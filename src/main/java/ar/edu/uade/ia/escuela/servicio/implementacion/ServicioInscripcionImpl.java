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
import ar.edu.uade.ia.escuela.dominio.modelo.Alumno;
import ar.edu.uade.ia.escuela.dominio.modelo.Inscripcion;
import ar.edu.uade.ia.escuela.dominio.modelo.Servicio;
import ar.edu.uade.ia.escuela.dominio.modelo.Titular;
import ar.edu.uade.ia.escuela.presentacion.dto.InscripcionDto;
import ar.edu.uade.ia.escuela.servicio.ServicioInscripcion;
import ar.edu.uade.ia.escuela.servicio.error.DniExistenteException;
import ar.edu.uade.ia.escuela.servicio.error.EntidadNoEncontrada;


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
    public void inscribirAlumno( InscripcionDto inscripcionDto, Alumno alumno, Integer dni)
    {
        if ( repositorioAlumno.findByDni( alumno.getDni()) != null )
        {
            throw new DniExistenteException();
        }
        Alumno alumnoActual = new Alumno();
        alumnoActual.setNombre( alumno.getNombre() );
        alumnoActual.setApellido( alumno.getApellido() );
        alumnoActual.setDni( alumno.getDni() );
        repositorioAlumno.save(alumnoActual);
        
        Optional<Titular> titularOpt = repositorioTitular.findByDni(dni);
        Titular titular = new Titular();
        titular = titularOpt.get();
        
        
        Inscripcion inscripcionActual = new Inscripcion();
        inscripcionActual.setId(inscripcionDto.getId());
        inscripcionActual.setTitular(titular);
        inscripcionActual.setAlumno(alumnoActual);
        repositorioInscripcion.save(inscripcionActual);
    }

	public void agregarServicios(InscripcionDto inscripcionDto, List<Servicio> servicios)
	{
		Optional<Inscripcion> inscripcion = repositorioInscripcion.findById(inscripcionDto.getId());
		if ( !inscripcion.isPresent())
        {
            throw new EntidadNoEncontrada("La inscripcion no existe");
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
