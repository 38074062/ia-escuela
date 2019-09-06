package ar.edu.uade.ia.escuela.presentacion.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.uade.ia.escuela.dominio.modelo.Alumno;
import ar.edu.uade.ia.escuela.presentacion.MensajePresentacion;
import ar.edu.uade.ia.escuela.presentacion.dto.InscripcionDto;
import ar.edu.uade.ia.escuela.presentacion.dto.RespuestaApiDto;
import ar.edu.uade.ia.escuela.servicio.ServicioInscripcion;
import ar.edu.uade.ia.escuela.servicio.error.DniExistenteException;

@RestController
public class ControladorInscripcion
{
	 @Autowired
	 private ServicioInscripcion servicioInscripcion;

	 @PostMapping( "/inscripcion" )
	 public RespuestaApiDto<Object> inscribirAlumno( @RequestBody InscripcionDto inscripcion , Alumno alumno, Integer dni)
	 {
	 RespuestaApiDto<Object> respuesta = new RespuestaApiDto<Object>();
	 try
	 {
	     servicioInscripcion.inscribirAlumno(inscripcion, alumno, dni);
	     respuesta.setEstado( true );
	     respuesta.setMensaje( MensajePresentacion.ALUMNO_INSCRIPTO.getDescripcion() );
	 }
	 catch ( DniExistenteException e )
	 {
		 respuesta.setEstado( false );
		 respuesta.setMensaje( e.getMessage() );
	 }
	 return respuesta;
	 }
	 
	 @GetMapping("/inscripcion")
	 public RespuestaApiDto<List<InscripcionDto>> listarInscripciones()
	 {
		 RespuestaApiDto<List<InscripcionDto>> respuesta = new RespuestaApiDto<List<InscripcionDto>>();
		 respuesta.setDatos(servicioInscripcion.listarInscripciones());
		 respuesta.setEstado(true);
		 return respuesta;
	 }	
	    

}
