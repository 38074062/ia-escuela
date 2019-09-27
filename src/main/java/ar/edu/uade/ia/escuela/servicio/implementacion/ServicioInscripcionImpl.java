package ar.edu.uade.ia.escuela.servicio.implementacion;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.uade.ia.escuela.datos.RepositorioAlumno;
import ar.edu.uade.ia.escuela.datos.RepositorioInscripcion;
import ar.edu.uade.ia.escuela.datos.RepositorioServicio;
import ar.edu.uade.ia.escuela.datos.RepositorioTitular;
import ar.edu.uade.ia.escuela.dominio.modelo.inscripcion.Alumno;
import ar.edu.uade.ia.escuela.dominio.modelo.inscripcion.Inscripcion;
import ar.edu.uade.ia.escuela.dominio.modelo.inscripcion.Servicio;
import ar.edu.uade.ia.escuela.dominio.modelo.inscripcion.Titular;
import ar.edu.uade.ia.escuela.presentacion.dto.AlumnoDto;
import ar.edu.uade.ia.escuela.presentacion.dto.InscripcionDetalleDto;
import ar.edu.uade.ia.escuela.presentacion.dto.InscripcionDto;
import ar.edu.uade.ia.escuela.presentacion.dto.InscripcionSimpleDto;
import ar.edu.uade.ia.escuela.presentacion.dto.ServicioDto;
import ar.edu.uade.ia.escuela.servicio.ServicioInscripcion;
import ar.edu.uade.ia.escuela.servicio.error.DniExistenteException;
import ar.edu.uade.ia.escuela.servicio.error.EntidadNoEncontradaException;

@Service
@Transactional
public class ServicioInscripcionImpl
    implements ServicioInscripcion
{
    @Autowired
    private RepositorioAlumno repositorioAlumno;

    @Autowired
    private RepositorioTitular repositorioTitular;

    @Autowired
    private RepositorioInscripcion repositorioInscripcion;

    @Autowired
    private RepositorioServicio repositorioServicio;

    @Override
    public void inscribirAlumno( InscripcionDto inscripcionDto )
    {
        Optional<Titular> optTitular = repositorioTitular.findByDni( inscripcionDto.getIdTitular().intValue() );
        if ( !optTitular.isPresent() )
        {
            throw new EntidadNoEncontradaException( "El titular ingresado no existe" );
        }
        Titular titular = optTitular.get();
        if ( repositorioAlumno.findByDni( inscripcionDto.getAlumno().getDni() ) != null )
        {
            throw new DniExistenteException();
        }
        Alumno alumnoActual = new Alumno();
        alumnoActual.setNombre( inscripcionDto.getAlumno().getNombre() );
        alumnoActual.setApellido( inscripcionDto.getAlumno().getApellido() );
        alumnoActual.setDni( inscripcionDto.getAlumno().getDni() );
        alumnoActual.setTitular( titular );
        Inscripcion inscripcionActual = new Inscripcion();
        inscripcionActual.setTitular( titular );
        inscripcionActual.setAlumno( alumnoActual );
        inscripcionActual.setServicios( buscarServiciosPorId( inscripcionDto.getIdServicios() ) );
        repositorioInscripcion.save( inscripcionActual );
    }

    private List<Servicio> buscarServiciosPorId( List<Long> idServicios )
    {
        List<Servicio> servicios = new LinkedList<>();
        for ( Long id : idServicios )
        {
            Optional<Servicio> servicioOpt = repositorioServicio.findById( id );
            if ( !servicioOpt.isPresent() )
            {
                throw new EntidadNoEncontradaException( "El servicio ingresado no existe" );
            }
            servicios.add( servicioOpt.get() );
        }
        return servicios;
    }

    @Override
    public List<InscripcionDetalleDto> listarInscripciones()
    {
        List<Inscripcion> inscripciones = repositorioInscripcion.findAll();
        List<InscripcionDetalleDto> inscripcionesDto = new LinkedList<>();
        inscripciones.forEach( inscripcion -> {
            InscripcionDetalleDto inscripionDto = new InscripcionDetalleDto();
            inscripionDto.setId( inscripcion.getId() );
            inscripionDto.setAlumno( convertirAlumnoAAlumnoDto( inscripcion.getAlumno() ) );
            inscripionDto.setDniTitular( inscripcion.getTitular().getDni() );
            inscripionDto.setServicios( convertirServiciosAServiciosDto( inscripcion.getServicios() ) );
            inscripcionesDto.add( inscripionDto );
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
    public void modificarInscripcion( InscripcionSimpleDto inscripcionDto )
    {
        Optional<Inscripcion> inscripcionOpt = repositorioInscripcion.findById( inscripcionDto.getId() );
        if ( !inscripcionOpt.isPresent() )
        {
            throw new EntidadNoEncontradaException( "La inscripcion ingresada no existe" );
        }
        Inscripcion inscripcion = inscripcionOpt.get();
        inscripcion.setServicios( buscarServiciosPorId( inscripcionDto.getIdServicios() ) );
        repositorioInscripcion.save( inscripcion );
    }

    @Override
    public void eliminarInscripcion( Long idInscripcion )
    {
        Optional<Inscripcion> optInscripcion = repositorioInscripcion.findById( idInscripcion );
        if ( !optInscripcion.isPresent() )
        {
            throw new EntidadNoEncontradaException( "La inscripcion ingresada no existe" );
        }
        Inscripcion inscripcion = optInscripcion.get();
        inscripcion.setActivo( false );
        repositorioInscripcion.save( inscripcion );
        repositorioInscripcion.deleteById( idInscripcion );
    }

}
