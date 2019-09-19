package ar.edu.uade.ia.escuela.servicio.implementacion;

import java.util.ArrayList;
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
import ar.edu.uade.ia.escuela.presentacion.dto.InscripcionDto;
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
        Inscripcion inscripcionActual = new Inscripcion();
        inscripcionActual.setId( inscripcionDto.getId() );
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
    public List<InscripcionDto> listarInscripciones()
    {
        List<Inscripcion> inscripciones = repositorioInscripcion.findAll();
        List<InscripcionDto> inscripcionDto = new ArrayList<InscripcionDto>();
        for ( Inscripcion s : inscripciones )
        {
            InscripcionDto sg = new InscripcionDto();
            sg.setId( s.getId() );
            inscripcionDto.add( sg );
        }
        return inscripcionDto;
    }

}
