package ar.edu.uade.ia.escuela.servicio;

import java.util.List;

import ar.edu.uade.ia.escuela.dominio.modelo.Alumno;
import ar.edu.uade.ia.escuela.dominio.modelo.Servicio;
import ar.edu.uade.ia.escuela.presentacion.dto.InscripcionDto;

public interface ServicioInscripcion 
{
	public void inscribirAlumno(InscripcionDto inscripcion, Alumno alumno, Integer dni);
	public void agregarServicios(InscripcionDto inscripcionDto, List<Servicio> servicios);
	public List<InscripcionDto> listarInscripciones(); 

}
