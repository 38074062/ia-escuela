package ar.edu.uade.ia.escuela.servicio.implementacion;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ar.edu.uade.ia.escuela.datos.RepositorioRol;
import ar.edu.uade.ia.escuela.datos.RepositorioUsuario;
import ar.edu.uade.ia.escuela.dominio.modelo.empleados.Cargo;
import ar.edu.uade.ia.escuela.dominio.modelo.empleados.Privilegio;
import ar.edu.uade.ia.escuela.dominio.modelo.empleados.Recibo;
import ar.edu.uade.ia.escuela.dominio.modelo.empleados.Rol;
import ar.edu.uade.ia.escuela.dominio.modelo.empleados.Usuario;
import ar.edu.uade.ia.escuela.presentacion.dto.CargoDto;
import ar.edu.uade.ia.escuela.presentacion.dto.EmpleadoDto;
import ar.edu.uade.ia.escuela.presentacion.dto.ReciboDto;
import ar.edu.uade.ia.escuela.presentacion.dto.RegistroUsuarioDto;
import ar.edu.uade.ia.escuela.servicio.ServicioUsuario;
import ar.edu.uade.ia.escuela.servicio.cliente.banco.PagoBanco;
import ar.edu.uade.ia.escuela.servicio.cliente.banco.PagoBancoException;
import ar.edu.uade.ia.escuela.servicio.cliente.banco.RequestBancoMovimiento;
import ar.edu.uade.ia.escuela.servicio.error.CargoInexistenteException;
import ar.edu.uade.ia.escuela.servicio.error.DniExistenteException;
import ar.edu.uade.ia.escuela.servicio.error.EntidadNoEncontradaException;
import ar.edu.uade.ia.escuela.servicio.error.LiquidacionSueldosException;
import ar.edu.uade.ia.escuela.servicio.error.NombreDeUsuarioExistenteException;

@Service
@Transactional
public class ServicioUsuarioImpl
    implements ServicioUsuario
{
    private static final String DEFAULT_PASSWORD = "1234";

    private static final Logger LOG = Logger.getGlobal();

    @Autowired
    private RepositorioUsuario repositorioUsuario;

    @Autowired
    private RepositorioRol repositorioRol;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private PagoBanco pagoBanco;

    @Value( "${cbu}" )
    private String cbuEscuela;

    @Override
    public UserDetails loadUserByUsername( String nombreUsuario )
        throws UsernameNotFoundException
    {
        Usuario usuario = repositorioUsuario.findByNombreUsuario( nombreUsuario );
        if ( usuario == null )
        {
            throw new UsernameNotFoundException( "Nombre de usuario o contraseña incorrectos" );
        }
        return new org.springframework.security.core.userdetails.User( usuario.getNombreUsuario(),
                                                                       usuario.getContrasenia(), true, true, true, true,
                                                                       getAuthorities( usuario.getRoles() ) );
    }

    private Collection<? extends GrantedAuthority> getAuthorities( Collection<Rol> roles )
    {
        return getGrantedAuthorities( getPrivileges( roles ) );
    }

    private List<String> getPrivileges( Collection<Rol> roles )
    {
        List<String> privilegios = new ArrayList<>();
        List<Privilegio> listaPrivilegios = new ArrayList<>();
        for ( Rol rol : roles )
        {
            listaPrivilegios.addAll( rol.getPrivilegios() );
        }
        for ( Privilegio privilegio : listaPrivilegios )
        {
            privilegios.add( privilegio.getNombre() );
        }
        return privilegios;
    }

    private List<GrantedAuthority> getGrantedAuthorities( List<String> privilegios )
    {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for ( String privilegio : privilegios )
        {
            authorities.add( new SimpleGrantedAuthority( privilegio ) );
        }
        return authorities;
    }

    @Override
    public void registrarUsuario( RegistroUsuarioDto usuarioDto )
    {
        if ( repositorioUsuario.findByNombreUsuario( usuarioDto.getNombreUsuario() ) != null )
        {
            throw new NombreDeUsuarioExistenteException();
        }
        if ( repositorioUsuario.findByDni( usuarioDto.getDni() ) != null )
        {
            throw new DniExistenteException();
        }
        Usuario usuario = new Usuario();
        try
        {
            usuario.setCargo( Cargo.getCargoPorCodigo( usuarioDto.getCargo() ) );
        }
        catch ( IllegalArgumentException iae )
        {
            throw new CargoInexistenteException();
        }
        usuario.setNombre( usuarioDto.getNombre() );
        usuario.setApellido( usuarioDto.getApellido() );
        usuario.setNombreUsuario( usuarioDto.getNombreUsuario() );
        usuario.setContrasenia( passwordEncoder.encode( DEFAULT_PASSWORD ) );
        usuario.setDni( usuarioDto.getDni() );
        usuario.setCuit( usuarioDto.getCuit() );
        usuario.setRoles( establecerRolSegunCargo( usuario.getCargo() ) );
        usuario.setCbu( usuarioDto.getCbu() );
        repositorioUsuario.save( usuario );
    }

    private List<Rol> establecerRolSegunCargo( Cargo cargo )
    {
        return Arrays.asList( repositorioRol.findByRol( cargo.getRol() ).get() );
    }

    @Override
    public List<CargoDto> getCargos()
    {
        Cargo[] cargos = Cargo.values();
        List<CargoDto> cargosDto = new LinkedList<>();
        for ( Cargo cargo : cargos )
        {
            if ( cargo != Cargo.ADMINISTRADOR )
            {
                CargoDto cargoDto = new CargoDto();
                cargoDto.setNombre( cargo.getNombre() );
                cargoDto.setCodigo( cargo.getCodigo() );
                cargosDto.add( cargoDto );
            }
        }
        return cargosDto;
    }

    @Override
    public List<EmpleadoDto> getEmpleados()
    {
        return convertirUsuariosAEmpleadosDto( repositorioUsuario.findEmpleados() );
    }

    private List<EmpleadoDto> convertirUsuariosAEmpleadosDto( List<Usuario> usuarios )
    {
        List<EmpleadoDto> empleados = new LinkedList<>();
        usuarios.forEach( usuario -> empleados.add( convertirUsuarioAEmpleadoDto( usuario ) ) );
        return empleados;
    }

    private EmpleadoDto convertirUsuarioAEmpleadoDto( Usuario usuario )
    {
        EmpleadoDto empleado = new EmpleadoDto();
        empleado.setId( usuario.getId() );
        empleado.setNombre( usuario.getNombre() );
        empleado.setApellido( usuario.getApellido() );
        empleado.setDni( usuario.getDni() );
        empleado.setCuit( usuario.getCuit() );
        empleado.setNombreUsuario( usuario.getNombreUsuario() );
        empleado.setCargo( usuario.getCargo().getNombre() );
        empleado.setCbu( usuario.getCbu() );
        return empleado;
    }

    @Override
    public void agregarCargaHoraria( ReciboDto recibo )
    {
        Optional<Usuario> usuarioOpt = repositorioUsuario.findById( recibo.getIdEmpleado() );
        if ( !usuarioOpt.isPresent() )
        {
            throw new EntidadNoEncontradaException( "El empleado no existe" );
        }
        Usuario usuario = usuarioOpt.get();
        usuario.agregarRecibo( convertirReciboDtoARecibo( recibo ) );
        repositorioUsuario.save( usuario );
    }

    private Recibo convertirReciboDtoARecibo( ReciboDto reciboDto )
    {
        Recibo recibo = new Recibo();
        recibo.setHaber( reciboDto.getHaber() );
        recibo.setPrecio( reciboDto.getPrecio() );
        recibo.setDescuento( reciboDto.getDescuento() );
        recibo.setHorario( reciboDto.getHorario() );
        recibo.setHoras( reciboDto.getHoras() );
        return recibo;
    }

    @Override
    public void modificarUsuario( RegistroUsuarioDto usuarioDto )
    {
        Optional<Usuario> usuarioOpt = repositorioUsuario.findById( usuarioDto.getId() );
        if ( !usuarioOpt.isPresent() )
        {
            throw new EntidadNoEncontradaException( "El usuario ingresado no existe" );
        }
        Usuario usuario = usuarioOpt.get();
        if ( !usuario.getNombreUsuario().equals( usuarioDto.getNombreUsuario() ) )
        {
            if ( repositorioUsuario.findByNombreUsuario( usuarioDto.getNombreUsuario() ) != null )
            {
                throw new NombreDeUsuarioExistenteException();
            }
        }
        if ( !usuario.getDni().equals( usuarioDto.getDni() ) )
        {
            if ( repositorioUsuario.findByDni( usuarioDto.getDni() ) != null )
            {
                throw new DniExistenteException();
            }
        }
        try
        {
            usuario.setCargo( Cargo.getCargoPorCodigo( usuarioDto.getCargo() ) );
        }
        catch ( IllegalArgumentException iae )
        {
            throw new CargoInexistenteException();
        }
        usuario.setNombre( usuarioDto.getNombre() );
        usuario.setApellido( usuarioDto.getApellido() );
        usuario.setNombreUsuario( usuarioDto.getNombreUsuario() );
        usuario.setDni( usuarioDto.getDni() );
        usuario.setCuit( usuarioDto.getCuit() );
        repositorioUsuario.save( usuario );
    }

    @Override
    public void eliminarUsuario( Long id )
    {
        if ( !repositorioUsuario.findById( id ).isPresent() )
        {
            throw new EntidadNoEncontradaException( "El usuario ingresado no existe" );
        }
        repositorioUsuario.deleteById( id );

    }

    @Override
    public void liquidarSueldos()
    {
        List<RequestBancoMovimiento> liquidaciones = new LinkedList<>();
        List<Usuario> usuarios = repositorioUsuario.findEmpleados();
        usuarios.stream().forEach( empleado -> {
            RequestBancoMovimiento requestBanco = new RequestBancoMovimiento();
            requestBanco.setCbuOrigen( cbuEscuela );
            requestBanco.setCbuDestino( empleado.getCbu() );
            requestBanco.setDescripcion( "Pago mes " + LocalDate.now().getMonth().getValue() );
            requestBanco.setMonto( empleado.calcularSueldo() );
            liquidaciones.add( requestBanco );
        } );
        try
        {
            pagoBanco.registrarPagos( liquidaciones );
        }
        catch ( PagoBancoException e )
        {
            LOG.severe( e.getMessage() );
            throw new LiquidacionSueldosException( e.getMessage() );
        }
    }
}