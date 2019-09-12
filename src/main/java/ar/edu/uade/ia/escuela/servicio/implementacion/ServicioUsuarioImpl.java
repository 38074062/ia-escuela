package ar.edu.uade.ia.escuela.servicio.implementacion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
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
import ar.edu.uade.ia.escuela.dominio.modelo.empleados.Rol;
import ar.edu.uade.ia.escuela.dominio.modelo.empleados.Usuario;
import ar.edu.uade.ia.escuela.presentacion.dto.CargoDto;
import ar.edu.uade.ia.escuela.presentacion.dto.RegistroUsuarioDto;
import ar.edu.uade.ia.escuela.servicio.ServicioUsuario;
import ar.edu.uade.ia.escuela.servicio.error.CargoInexistenteException;
import ar.edu.uade.ia.escuela.servicio.error.DniExistenteException;
import ar.edu.uade.ia.escuela.servicio.error.NombreDeUsuarioExistenteException;

@Service
@Transactional
public class ServicioUsuarioImpl
    implements ServicioUsuario
{
    private static final String DEFAULT_PASSWORD = "1234";

    @Autowired
    private RepositorioUsuario repositorioUsuario;

    @Autowired
    private RepositorioRol repositorioRol;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

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
}