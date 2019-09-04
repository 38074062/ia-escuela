package ar.edu.uade.ia.escuela.servicio.implementacion;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.edu.uade.ia.escuela.datos.RepositorioAlumno;
import ar.edu.uade.ia.escuela.datos.RepositorioFactura;
import ar.edu.uade.ia.escuela.datos.RepositorioInscripcion;
import ar.edu.uade.ia.escuela.dominio.modelo.Alumno;
import ar.edu.uade.ia.escuela.dominio.modelo.Factura;
import ar.edu.uade.ia.escuela.dominio.modelo.Inscripcion;
import ar.edu.uade.ia.escuela.presentacion.dto.FacturaDto;
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
        if ( RepositorioAlumno.findByDni( alumno.getDni()) != null )
        {
            throw new DniExistenteException();
        }
        Alumno alumnoActual = new Alumno();
        alumnoActual.setNombre( alumno.getNombre() );
        alumnoActual.setApellido( alumno.getApellido() );
        alumnoActual.setDni( alumno.getDni() );
        //alumnoActual.setLegajo(); autogenerar 
        RepositorioAlumno.save( alumnoActual , legajo);
        
        Inscripcion inscripcionActual = new Inscripcion();
        inscripcionActual.setId(inscripcionDto.getId());
        RepositorioInscripcion.save(inscripcionActual);
    }

	@Override
	public List<InscripcionDto> listarInscripciones() {
		List<Inscripcion> inscripciones = 	RepositorioInscripcion.findAll();
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
