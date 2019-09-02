package ar.edu.uade.ia.escuela.servicio.implementacion;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.edu.uade.ia.escuela.datos.RepositorioAlumno;
import ar.edu.uade.ia.escuela.datos.RepositorioInscripcion;
import ar.edu.uade.ia.escuela.dominio.modelo.Alumno;
import ar.edu.uade.ia.escuela.dominio.modelo.Inscripcion;
import ar.edu.uade.ia.escuela.presentacion.dto.InscripcionDto;
import ar.edu.uade.ia.escuela.servicio.ServicioInscripcion;
import ar.edu.uade.ia.escuela.servicio.error.DniExistenteException;


@Service
@Transactional

public class ServicioInscripcionImpl 
	implements ServicioInscripcion
{
	@Override
    public void inscribirAlumno( InscripcionDto inscripcionDto, Alumno alumno )
    {
        if ( RepositorioInscripcion.findByDni( alumno.getDni()) != null )
        {
            throw new DniExistenteException();
        }
        Alumno alumnoActual = new Alumno();
        alumnoActual.setNombre( alumno.getNombre() );
        alumnoActual.setApellido( alumno.getApellido() );
        alumnoActual.setDni( alumno.getDni() );
        //alumnoActual.setLegajo(); autogenerar 
        RepositorioAlumno.save( alumnoActual , legajo);
    }

}
