package ar.edu.uade.ia.escuela.presentacion.controlador;

import java.util.Arrays;
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
import ar.edu.uade.ia.escuela.presentacion.dto.RespuestaApiDto;
import ar.edu.uade.ia.escuela.presentacion.dto.ServicioDto;
import ar.edu.uade.ia.escuela.servicio.ServicioServicio;
import ar.edu.uade.ia.escuela.servicio.error.EntidadNoEncontradaException;
import ar.edu.uade.ia.escuela.servicio.error.NombreExistenteException;

@RestController
@CrossOrigin
public class ControladorServicio
{

    @Autowired
    private ServicioServicio servicioServicio;

    @PostMapping( "/servicios/escolaridad" )
    public RespuestaApiDto<Object> altaServicioEscolaridad( @RequestBody ServicioDto servicio )
    {
        RespuestaApiDto<Object> respuesta = new RespuestaApiDto<Object>();
        try
        {
            servicioServicio.altaEscolaridad( servicio );
            respuesta.setEstado( true );
            respuesta.setMensaje( MensajePresentacion.SERVICIO_CREADO.getDescripcion() );
        }
        catch ( NombreExistenteException e )
        {
            respuesta.setEstado( false );
            respuesta.setMensaje( e.getMessage() );
        }
        return respuesta;
    }

    @PostMapping( "/servicios/comedor" )
    public RespuestaApiDto<Object> altaServicioComedor( @RequestBody ServicioDto servicio )
    {
        RespuestaApiDto<Object> respuesta = new RespuestaApiDto<Object>();
        try
        {
            servicioServicio.altaComedor( servicio );
            respuesta.setEstado( true );
            respuesta.setMensaje( MensajePresentacion.SERVICIO_CREADO.getDescripcion() );
        }
        catch ( NombreExistenteException e )
        {
            respuesta.setEstado( false );
            respuesta.setMensaje( e.getMessage() );
        }
        return respuesta;
    }

    @PostMapping( "/servicios/adicional" )
    public RespuestaApiDto<Object> altaServicioAdicional( @RequestBody ServicioDto servicio )
    {
        RespuestaApiDto<Object> respuesta = new RespuestaApiDto<Object>();
        try
        {
            servicioServicio.altaEscolaridad( servicio );
            respuesta.setEstado( true );
            respuesta.setMensaje( MensajePresentacion.SERVICIO_CREADO.getDescripcion() );
        }
        catch ( NombreExistenteException e )
        {
            respuesta.setEstado( false );
            respuesta.setMensaje( e.getMessage() );
        }
        return respuesta;
    }

    @DeleteMapping( "/servicios/{id}" )
    public RespuestaApiDto<Object> bajaServicio( @PathVariable( name = "id" ) Long id )
    {
        RespuestaApiDto<Object> respuesta = new RespuestaApiDto<Object>();
        try
        {
            servicioServicio.bajaServicio( id );
            respuesta.setEstado( true );
            respuesta.setMensaje( MensajePresentacion.SERVICIO_BORRADO.getDescripcion() );

        }
        catch ( EntidadNoEncontradaException e )
        {
            respuesta.setEstado( false );
            respuesta.setMensaje( e.getMessage() );
        }
        return respuesta;
    }

    @PutMapping( "/servicios" )
    public RespuestaApiDto<Object> modificarServicio( @RequestBody ServicioDto servicio )
    {
        RespuestaApiDto<Object> respuesta = new RespuestaApiDto<Object>();
        try
        {
            servicioServicio.modificarServicio( servicio );
            respuesta.setEstado( true );
            respuesta.setMensaje( MensajePresentacion.SERVICIO_MODIFICADO.getDescripcion() );
        }
        catch ( EntidadNoEncontradaException e )
        {
            respuesta.setEstado( false );
            respuesta.setMensaje( e.getMessage() );
        }
        return respuesta;
    }

    @GetMapping( "/servicios" )
    public RespuestaApiDto<List<ServicioDto>> listarServicios()
    {
        RespuestaApiDto<List<ServicioDto>> respuesta = new RespuestaApiDto<List<ServicioDto>>();
        respuesta.setDatos( servicioServicio.listarServicios() );
        respuesta.setEstado( true );
        return respuesta;
    }

    @GetMapping("/servicios/categorias")
    public RespuestaApiDto<List<String>> listarCategorias()
    {
        RespuestaApiDto<List<String>> respuesta = new RespuestaApiDto<List<String>>();
        respuesta.setDatos( Arrays.asList("Comedor","Escolaridad","Adicional"));
        respuesta.setEstado( true );
        return respuesta;
    }
}