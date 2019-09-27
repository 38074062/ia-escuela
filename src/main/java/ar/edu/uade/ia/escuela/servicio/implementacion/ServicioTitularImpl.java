package ar.edu.uade.ia.escuela.servicio.implementacion;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.uade.ia.escuela.datos.RepositorioTitular;
import ar.edu.uade.ia.escuela.dominio.error.FacturaNoEncontradaException;
import ar.edu.uade.ia.escuela.dominio.modelo.facturacion.CuentaCorriente;
import ar.edu.uade.ia.escuela.dominio.modelo.inscripcion.Alumno;
import ar.edu.uade.ia.escuela.dominio.modelo.inscripcion.Inscripcion;
import ar.edu.uade.ia.escuela.dominio.modelo.inscripcion.Servicio;
import ar.edu.uade.ia.escuela.dominio.modelo.inscripcion.Titular;
import ar.edu.uade.ia.escuela.presentacion.dto.AlumnoDto;
import ar.edu.uade.ia.escuela.presentacion.dto.EstadoFacturasDto;
import ar.edu.uade.ia.escuela.presentacion.dto.InscripcionDetalleDto;
import ar.edu.uade.ia.escuela.presentacion.dto.PagoDto;
import ar.edu.uade.ia.escuela.presentacion.dto.ServicioDto;
import ar.edu.uade.ia.escuela.presentacion.dto.TitularDetalleDto;
import ar.edu.uade.ia.escuela.presentacion.dto.TitularDto;
import ar.edu.uade.ia.escuela.servicio.ServicioTitular;
import ar.edu.uade.ia.escuela.servicio.error.DniExistenteException;
import ar.edu.uade.ia.escuela.servicio.error.EntidadNoEncontradaException;

@Service
@Transactional
public class ServicioTitularImpl
    implements ServicioTitular
{

    @Autowired
    private RepositorioTitular repositorioTitular;

    @Override
    public void altaTitular( TitularDto titularDto )
    {
        Optional<Titular> optTitular = repositorioTitular.findByDni( titularDto.getDni() );
        if ( optTitular.isPresent() )
        {
            throw new DniExistenteException();
        }
        Titular titular = optTitular.orElse( new Titular() );
        titular.setNombre( titularDto.getNombre() );
        titular.setApellido( titularDto.getApellido() );
        titular.setDni( titularDto.getDni() );
        titular.setDireccion( titularDto.getDireccion() );
        titular.setEmail( titularDto.getEmail() );
        CuentaCorriente cuenta = new CuentaCorriente();
        cuenta.setCuentaBancaria( titularDto.getCuentaBancaria() );
        titular.setCuentaCorriente( cuenta );
        repositorioTitular.save( titular );
    }

    @Override
    public void bajaTitular( Long id )
    {
        if ( !repositorioTitular.findById( id ).isPresent() )
        {
            throw new EntidadNoEncontradaException( "El titular no existe" );
        }
        repositorioTitular.deleteById( id );
    }

    @Override
    public void modificarTitular( TitularDto titularDto )
    {
        Optional<Titular> titularOpt = repositorioTitular.findById( titularDto.getId() );
        if ( !titularOpt.isPresent() )
        {
            throw new EntidadNoEncontradaException( "El titular no existe" );
        }
        Titular titularGuardado = titularOpt.get();
        titularGuardado.setNombre( titularDto.getNombre() );
        titularGuardado.setApellido( titularDto.getApellido() );
        if ( !titularDto.getDni().equals( titularGuardado.getDni() ) )
        {
            if ( repositorioTitular.findByDni( titularDto.getDni() ).isPresent() )
            {
                throw new DniExistenteException();
            }
        }
        titularGuardado.setDni( titularDto.getDni() );
        titularGuardado.setDireccion( titularDto.getDireccion() );
        titularGuardado.setEmail( titularDto.getEmail() );
        if ( titularGuardado.getCuentaCorriente() != null )
        {
            titularGuardado.getCuentaCorriente().setCuentaBancaria( titularDto.getCuentaBancaria() );
        }
        repositorioTitular.save( titularGuardado );
    }

    @Override
    public List<TitularDto> listarTitulares()
    {
        List<Titular> titulares = repositorioTitular.findAll();
        List<TitularDto> titularDto = new ArrayList<TitularDto>();
        for ( Titular s : titulares )
        {
            TitularDto sg = new TitularDto();
            sg.setId( s.getId() );
            sg.setDni( s.getDni() );
            sg.setNombre( s.getNombre() );
            sg.setApellido( s.getApellido() );
            sg.setDireccion( s.getDireccion() );
            sg.setEmail( s.getEmail() );
            sg.setCuentaBancaria( s.getCuentaCorriente().getCuentaBancaria() );
            titularDto.add( sg );
        }
        return titularDto;
    }

    @Override
    public TitularDetalleDto getTitular( Long id )
    {
        Optional<Titular> titularOpt = repositorioTitular.findById( id );
        if ( !titularOpt.isPresent() )
        {
            throw new EntidadNoEncontradaException( "El titular no existe" );
        }
        return convertirTitularATitularDetalleDto( titularOpt.get() );
    }

    private TitularDetalleDto convertirTitularATitularDetalleDto( Titular titular )
    {
        TitularDetalleDto titularDetalleDto = new TitularDetalleDto();
        titularDetalleDto.setId( titular.getId() );
        titularDetalleDto.setDni( titular.getDni() );
        titularDetalleDto.setNombre( titular.getNombre() );
        titularDetalleDto.setApellido( titular.getApellido() );
        titularDetalleDto.setDireccion( titular.getDireccion() );
        titularDetalleDto.setEmail( titular.getEmail() );
        titularDetalleDto.setInscripciones( convertirInscripcionesAInscripcionesDetalleDto( titular.getInscripcionesActivas() ) );
        titularDetalleDto.setFacturas( convertirFacturasAEstadoFacturasDto( titular.getCuentaCorriente() ) );
        return titularDetalleDto;
    }

    private List<EstadoFacturasDto> convertirFacturasAEstadoFacturasDto( CuentaCorriente cuentaCorriente )
    {
        List<EstadoFacturasDto> facturas = new LinkedList<>();
        cuentaCorriente.getItemsCuentas().forEach( item -> {
            EstadoFacturasDto estado = new EstadoFacturasDto();
            estado.setIdFactura( item.getFactura().getId() );
            estado.setDetalle( item.getDescripcion() );
            estado.setFechaVencimiento( item.getFactura().getVencimiento() );
            estado.setMonto( item.getFactura().getTotal() );
            estado.setEstado( item.getEstado().getDescripcion() );
            estado.setMontoPagado( item.getMonto() );
            facturas.add( estado );
        } );
        return facturas;
    }

    private List<InscripcionDetalleDto> convertirInscripcionesAInscripcionesDetalleDto( List<Inscripcion> inscripciones )
    {
        List<InscripcionDetalleDto> inscripcionesDto = new LinkedList<>();
        inscripciones.forEach( inscripcion -> {
            InscripcionDetalleDto inscricpionDto = new InscripcionDetalleDto();
            inscricpionDto.setId( inscripcion.getId() );
            inscricpionDto.setAlumno( convertirAlumnoAAlumnoDto( inscripcion.getAlumno() ) );
            inscricpionDto.setServicios( convertirServiciosAServiciosDto( inscripcion.getServicios() ) );
            inscripcionesDto.add( inscricpionDto );
        } );
        return inscripcionesDto;
    }

    private List<ServicioDto> convertirServiciosAServiciosDto( List<Servicio> servicios )
    {
        List<ServicioDto> serviciosDto = new LinkedList<>();
        servicios.forEach( servicio -> {
            ServicioDto servicioDto = new ServicioDto();
            servicioDto.setId( servicio.getId() );
            servicioDto.setNombre( servicio.getNombre() );
            servicioDto.setTipo( servicio.getTipo() );
            servicioDto.setPrecio( servicio.getPrecio() );
            servicioDto.setCategoria( servicio.getCategoria() );
            serviciosDto.add( servicioDto );
        } );
        return serviciosDto;
    }

    private AlumnoDto convertirAlumnoAAlumnoDto( Alumno alumno )
    {
        AlumnoDto alumnoDto = new AlumnoDto();
        alumnoDto.setId( alumno.getId() );
        alumnoDto.setDni( alumno.getDni() );
        alumnoDto.setNombre( alumno.getNombre() );
        alumnoDto.setApellido( alumno.getApellido() );
        return alumnoDto;
    }

    @Override
    public void registrarPago( PagoDto pagoDto )
    {
        Optional<Titular> titularOpt = repositorioTitular.findById( pagoDto.getTitularId() );
        if ( !titularOpt.isPresent() )
        {
            throw new EntidadNoEncontradaException( "El titular no existe" );
        }
        Titular titular = titularOpt.get();
        try
        {
            titular.registrarPago( pagoDto.getFacturaId(), pagoDto.getFecha(), pagoDto.getMonto() );
            repositorioTitular.save( titular );
        }
        catch ( FacturaNoEncontradaException fne )
        {
            throw new EntidadNoEncontradaException( fne.getMessage() );
        }
    }
}
