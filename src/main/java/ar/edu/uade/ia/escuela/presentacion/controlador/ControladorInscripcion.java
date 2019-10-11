package ar.edu.uade.ia.escuela.presentacion.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.uade.ia.escuela.presentacion.MensajePresentacion;
import ar.edu.uade.ia.escuela.presentacion.dto.InscripcionDetalleDto;
import ar.edu.uade.ia.escuela.presentacion.dto.InscripcionDto;
import ar.edu.uade.ia.escuela.presentacion.dto.InscripcionSimpleDto;
import ar.edu.uade.ia.escuela.presentacion.dto.RespuestaApiDto;
import ar.edu.uade.ia.escuela.servicio.ServicioInscripcion;
import ar.edu.uade.ia.escuela.servicio.error.DniExistenteException;
import ar.edu.uade.ia.escuela.servicio.error.EntidadNoEncontradaException;

@RestController
@CrossOrigin
public class ControladorInscripcion
{
    @Autowired
    private ServicioInscripcion servicioInscripcion;

    @PostMapping( "/inscripcion" )
    public RespuestaApiDto<Object> inscribirAlumno( @RequestBody InscripcionDto inscripcion )
    {
        RespuestaApiDto<Object> respuesta = new RespuestaApiDto<Object>();
        try
        {
            servicioInscripcion.inscribirAlumno( inscripcion );
            respuesta.setEstado( true );
            respuesta.setMensaje( MensajePresentacion.ALUMNO_INSCRIPTO.getDescripcion() );
        }
        catch ( DniExistenteException | EntidadNoEncontradaException e )
        {
            respuesta.setEstado( false );
            respuesta.setMensaje( e.getMessage() );
        }
        return respuesta;
    }

    @PutMapping( "/inscripcion" )
    public RespuestaApiDto<Object> modificarInscripcion( @RequestBody InscripcionSimpleDto inscripcion )
    {
        RespuestaApiDto<Object> respuesta = new RespuestaApiDto<Object>();
        try
        {
            servicioInscripcion.modificarInscripcion( inscripcion );
            respuesta.setEstado( true );
            respuesta.setMensaje( MensajePresentacion.INSCRIPCION_MODIFICADA.getDescripcion() );
        }
        catch ( DniExistenteException | EntidadNoEncontradaException e )
        {
            respuesta.setEstado( false );
            respuesta.setMensaje( e.getMessage() );
        }
        return respuesta;
    }

    @GetMapping( "/inscripcion" )
    public RespuestaApiDto<List<InscripcionDetalleDto>> listarInscripciones()
    {
        RespuestaApiDto<List<InscripcionDetalleDto>> respuesta = new RespuestaApiDto<>();
        respuesta.setDatos( servicioInscripcion.listarInscripciones() );
        respuesta.setEstado( true );
        return respuesta;
    }

    @DeleteMapping("/inscripcion/{id}")
    public RespuestaApiDto<Object> bajaServicio( @PathVariable( name = "id" ) Long id )
    {
        RespuestaApiDto<Object> respuesta = new RespuestaApiDto<Object>();
        try
        {
            servicioInscripcion.eliminarInscripcion( id );
            respuesta.setEstado( true );
            respuesta.setMensaje( MensajePresentacion.INSCRIPCION_ELIMINADA.getDescripcion() );
        }
        catch ( EntidadNoEncontradaException e )
        {
            respuesta.setEstado( false );
            respuesta.setMensaje( e.getMessage() );
        }
        return respuesta;
    }
}
