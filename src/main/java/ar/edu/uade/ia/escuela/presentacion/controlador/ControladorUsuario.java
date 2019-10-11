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
import ar.edu.uade.ia.escuela.presentacion.dto.CargoDto;
import ar.edu.uade.ia.escuela.presentacion.dto.EmpleadoDto;
import ar.edu.uade.ia.escuela.presentacion.dto.ReciboDto;
import ar.edu.uade.ia.escuela.presentacion.dto.RegistroUsuarioDto;
import ar.edu.uade.ia.escuela.presentacion.dto.RespuestaApiDto;
import ar.edu.uade.ia.escuela.servicio.ServicioUsuario;
import ar.edu.uade.ia.escuela.servicio.error.CargoInexistenteException;
import ar.edu.uade.ia.escuela.servicio.error.DniExistenteException;
import ar.edu.uade.ia.escuela.servicio.error.EntidadNoEncontradaException;
import ar.edu.uade.ia.escuela.servicio.error.NombreDeUsuarioExistenteException;

@RestController
@CrossOrigin
public class ControladorUsuario
{

    @Autowired
    private ServicioUsuario servicioUsuario;

    @GetMapping( "/cargos" )
    public RespuestaApiDto<List<CargoDto>> getCargos()
    {
        RespuestaApiDto<List<CargoDto>> respuesta = new RespuestaApiDto<List<CargoDto>>();
        respuesta.setDatos( servicioUsuario.getCargos() );
        respuesta.setEstado( true );
        return respuesta;
    }

    @PostMapping( "/empleados" )
    public RespuestaApiDto<Object> altaEmpleado( @RequestBody RegistroUsuarioDto usuario )
    {
        RespuestaApiDto<Object> respuesta = new RespuestaApiDto<Object>();
        try
        {
            servicioUsuario.registrarUsuario( usuario );
            respuesta.setEstado( true );
            respuesta.setMensaje( MensajePresentacion.USUARIO_CREADO.getDescripcion() );
        }
        catch ( NombreDeUsuarioExistenteException | DniExistenteException | CargoInexistenteException e )
        {
            respuesta.setEstado( false );
            respuesta.setMensaje( e.getMessage() );
        }
        return respuesta;
    }

    @GetMapping( "/empleados" )
    public RespuestaApiDto<List<EmpleadoDto>> getEmpleados()
    {
        RespuestaApiDto<List<EmpleadoDto>> respuesta = new RespuestaApiDto<List<EmpleadoDto>>();
        respuesta.setDatos( servicioUsuario.getEmpleados() );
        respuesta.setEstado( true );
        return respuesta;
    }

    @PostMapping( "/empleados/{id}/cargaHoraria" )
    public RespuestaApiDto<Object> agregarCargaHoraria( @RequestBody ReciboDto reciboDto )
    {
        RespuestaApiDto<Object> respuesta = new RespuestaApiDto<>();
        try
        {
            servicioUsuario.agregarCargaHoraria( reciboDto );
            respuesta.setEstado( true );
            respuesta.setMensaje( MensajePresentacion.CARGA_HORARIA_AGREGADA.getDescripcion() );
        }
        catch ( EntidadNoEncontradaException ene )
        {
            respuesta.setMensaje( ene.getMessage() );
            respuesta.setEstado( false );
        }
        return respuesta;
    }

    @DeleteMapping( "/empleados/{id}" )
    public RespuestaApiDto<Object> bajaEmpleado( @PathVariable( name = "id" ) Long id )
    {
        RespuestaApiDto<Object> respuesta = new RespuestaApiDto<Object>();
        try
        {
            servicioUsuario.eliminarUsuario( id );
            respuesta.setEstado( true );
            respuesta.setMensaje( MensajePresentacion.USUARIO_BORRADO.getDescripcion() );

        }
        catch ( EntidadNoEncontradaException e )
        {
            respuesta.setEstado( false );
            respuesta.setMensaje( e.getMessage() );
        }
        return respuesta;
    }

    @PutMapping( "/empleados" )
    public RespuestaApiDto<Object> modificarUsuario( @RequestBody RegistroUsuarioDto usuario )
    {
        RespuestaApiDto<Object> respuesta = new RespuestaApiDto<Object>();
        try
        {
            servicioUsuario.modificarUsuario( usuario );
            respuesta.setEstado( true );
            respuesta.setMensaje( MensajePresentacion.USUARIO_MODIFICADO.getDescripcion() );
        }
        catch ( EntidadNoEncontradaException e )
        {
            respuesta.setEstado( false );
            respuesta.setMensaje( e.getMessage() );
        }
        return respuesta;
    }

}