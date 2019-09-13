package ar.edu.uade.ia.escuela.servicio.implementacion;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.uade.ia.escuela.datos.RepositorioAlumno;
import ar.edu.uade.ia.escuela.datos.RepositorioInscripcion;
import ar.edu.uade.ia.escuela.datos.RepositorioTitular;
import ar.edu.uade.ia.escuela.dominio.modelo.inscripcion.Alumno;
import ar.edu.uade.ia.escuela.dominio.modelo.inscripcion.Inscripcion;
import ar.edu.uade.ia.escuela.dominio.modelo.inscripcion.Servicio;
import ar.edu.uade.ia.escuela.dominio.modelo.inscripcion.Titular;
import ar.edu.uade.ia.escuela.presentacion.dto.AlumnoDto;
import ar.edu.uade.ia.escuela.presentacion.dto.InscripcionDto;
import ar.edu.uade.ia.escuela.servicio.ServicioInscripcion;
import ar.edu.uade.ia.escuela.servicio.error.DniExistenteException;
import ar.edu.uade.ia.escuela.servicio.error.EntidadNoEncontradaException;
import ar.edu.uade.ia.escuela.servicio.error.TitularNoExisteException;


@Service
@Transactional

public class ServicioInscripcionImpl 
	implements ServicioInscripcion
{
	@Autowired
	RepositorioAlumno repositorioAlumno;
	@Autowired
	RepositorioTitular repositorioTitular;
	@Autowired
	RepositorioInscripcion repositorioInscripcion;
	
	@Override
    public void inscribirAlumno( InscripcionDto inscripcionDto, AlumnoDto alumno, Integer dni)
    {
		Optional<Titular> optTitular = repositorioTitular.findByDni( dni );
			if ( !optTitular.isPresent() )
			{
	            throw new TitularNoExisteException();
	        }
	    Titular titular = optTitular.get();
	        
		if ( repositorioAlumno.findByDni( alumno.getDni()) != null )
        {
            throw new DniExistenteException();
        }
        Alumno alumnoActual = new Alumno();
        alumnoActual.setNombre( alumno.getNombre() );
        alumnoActual.setApellido( alumno.getApellido() );
        alumnoActual.setDni( alumno.getDni() );
                
        Inscripcion inscripcionActual = new Inscripcion();
        inscripcionActual.setId(inscripcionDto.getId());
        inscripcionActual.setTitular(titular);
        inscripcionActual.setAlumno(alumnoActual);
        repositorioInscripcion.save(inscripcionActual);
    }

	@Override
	public void agregarServicios(InscripcionDto inscripcionDto, List<Servicio> servicios)
	{
		Optional<Inscripcion> inscripcion = repositorioInscripcion.findById(inscripcionDto.getId());
		if ( !inscripcion.isPresent())
        {
            throw new EntidadNoEncontradaException("La inscripcion no existe");
        }
		Inscripcion inscripcionActual = inscripcion.get();
		inscripcionActual.setServicios(servicios);	
		repositorioInscripcion.save(inscripcionActual);
	}
	
	@Override
	public List<InscripcionDto> listarInscripciones() {
		List<Inscripcion> inscripciones = 	repositorioInscripcion.findAll();
		List<InscripcionDto> inscripcionDto = new ArrayList<InscripcionDto>();
		for(Inscripcion s:inscripciones)
		{
			InscripcionDto sg= new InscripcionDto();
			sg.setId(s.getId());
			inscripcionDto.add(sg);
		}
		return inscripcionDto;
	}

}
