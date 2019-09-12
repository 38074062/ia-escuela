package ar.edu.uade.ia.escuela.presentacion.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.uade.ia.escuela.presentacion.MensajePresentacion;
import ar.edu.uade.ia.escuela.presentacion.dto.RespuestaApiDto;
import ar.edu.uade.ia.escuela.presentacion.dto.ServicioDto;
import ar.edu.uade.ia.escuela.servicio.ServicioServicio;
import ar.edu.uade.ia.escuela.servicio.error.EntidadNoEncontrada;
import ar.edu.uade.ia.escuela.servicio.error.NombreExistenteException;

@RestController
public class ControladorServicio
{

    @Autowired
    private ServicioServicio servicioServicio;

    @PostMapping( "/servicios" )
    public RespuestaApiDto<Object> altaServicio( @RequestBody ServicioDto servicio )
    {
        RespuestaApiDto<Object> respuesta = new RespuestaApiDto<Object>();
        try
        {
            servicioServicio.altaEscolaridad( servicio );
            respuesta.setEstado( true );
            respuesta.setMensaje( MensajePresentacion.SERVICIO_CREADO.getDescripcion() );
        }
        catch ( NombreExistenteException e)
        {
            respuesta.setEstado( false );
            respuesta.setMensaje( e.getMessage() );
        }
        return respuesta;
    }
    
    @DeleteMapping("/servicios/{id}")
    public RespuestaApiDto<Object> bajaServicio(@PathVariable(name = "id")Long id)
    {
    	 RespuestaApiDto<Object> respuesta = new RespuestaApiDto<Object>();
         try
         {
        	 servicioServicio.bajaServicio(id);
        	 respuesta.setEstado( true );
             respuesta.setMensaje( MensajePresentacion.SERVICIO_BORRADO.getDescripcion() );
        	 
         }
         catch(EntidadNoEncontrada e)
         {
        	 respuesta.setEstado( false );
             respuesta.setMensaje( e.getMessage() );
         }
         return respuesta;
    }

    @PutMapping("/servicios")
    public RespuestaApiDto<Object> modificarServicio(@RequestBody ServicioDto servicio)
    {
    	RespuestaApiDto<Object> respuesta = new RespuestaApiDto<Object>();
        try
        {
            servicioServicio.modificarServicio( servicio );
            respuesta.setEstado( true );
            respuesta.setMensaje( MensajePresentacion.SERVICIO_MODIFICADO.getDescripcion() );
        }
        catch (EntidadNoEncontrada e)
        {
            respuesta.setEstado( false );
            respuesta.setMensaje( e.getMessage() );
        }
        return respuesta;
    }
    
    @GetMapping("/servicios")
    public RespuestaApiDto<List<ServicioDto>> listarServicios()
    {
    	RespuestaApiDto<List<ServicioDto>> respuesta = new RespuestaApiDto<List<ServicioDto>>();
    	respuesta.setDatos(servicioServicio.listarServicios());
    	respuesta.setEstado(true);
    	return respuesta;
    }
    
}