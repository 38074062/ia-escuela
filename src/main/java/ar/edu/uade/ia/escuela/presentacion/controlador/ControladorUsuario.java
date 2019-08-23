package ar.edu.uade.ia.escuela.presentacion.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.uade.ia.escuela.presentacion.MensajePresentacion;
import ar.edu.uade.ia.escuela.presentacion.dto.CargoDto;
import ar.edu.uade.ia.escuela.presentacion.dto.RegistroUsuarioDto;
import ar.edu.uade.ia.escuela.presentacion.dto.RespuestaApiDto;
import ar.edu.uade.ia.escuela.servicio.ServicioUsuario;
import ar.edu.uade.ia.escuela.servicio.error.CargoInexistenteException;
import ar.edu.uade.ia.escuela.servicio.error.DniExistenteException;
import ar.edu.uade.ia.escuela.servicio.error.NombreDeUsuarioExistenteException;

@RestController
public class ControladorUsuario
{

    @Autowired
    private ServicioUsuario servicioUsuario;

    @GetMapping("/")
    @ResponseBody
    public String home(){
        return "Hola!";
    }

    @GetMapping( "/cargos" )
    public RespuestaApiDto<List<CargoDto>> getCargos()
    {
        RespuestaApiDto<List<CargoDto>> respuesta = new RespuestaApiDto<List<CargoDto>>();
        respuesta.setDatos( servicioUsuario.getCargos() );
        respuesta.setEstado( true );
        return respuesta;
    }

    @PostMapping( "/registrarse" )
    public RespuestaApiDto<Object> registrarUsuario( @RequestBody RegistroUsuarioDto usuario )
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

}