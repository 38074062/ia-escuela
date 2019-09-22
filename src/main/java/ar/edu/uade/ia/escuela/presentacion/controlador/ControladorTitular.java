package ar.edu.uade.ia.escuela.presentacion.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.uade.ia.escuela.presentacion.MensajePresentacion;
import ar.edu.uade.ia.escuela.presentacion.dto.PagoDto;
import ar.edu.uade.ia.escuela.presentacion.dto.RespuestaApiDto;
import ar.edu.uade.ia.escuela.presentacion.dto.TitularDetalleDto;
import ar.edu.uade.ia.escuela.presentacion.dto.TitularDto;
import ar.edu.uade.ia.escuela.servicio.ServicioTitular;
import ar.edu.uade.ia.escuela.servicio.error.DniExistenteException;
import ar.edu.uade.ia.escuela.servicio.error.EntidadNoEncontradaException;

@RestController
public class ControladorTitular
{
    @Autowired
    private ServicioTitular servicioTitular;

    @PostMapping( "/titulares" )
    public RespuestaApiDto<Object> altaTitular( @RequestBody TitularDto titular )
    {
        RespuestaApiDto<Object> respuesta = new RespuestaApiDto<Object>();
        try
        {
            servicioTitular.altaTitular( titular );
            respuesta.setEstado( true );
            respuesta.setMensaje( MensajePresentacion.TITULAR_CREADO.getDescripcion() );
        }
        catch ( DniExistenteException e )
        {
            respuesta.setEstado( false );
            respuesta.setMensaje( e.getMessage() );
        }
        return respuesta;
    }

    @GetMapping( "/titulares" )
    public RespuestaApiDto<List<TitularDto>> listarTitulares()
    {
        RespuestaApiDto<List<TitularDto>> respuesta = new RespuestaApiDto<List<TitularDto>>();
        respuesta.setDatos( servicioTitular.listarTitulares() );
        respuesta.setEstado( true );
        return respuesta;
    }

    @GetMapping( "/titulares/{id}" )
    public RespuestaApiDto<TitularDetalleDto> getTitular( @PathVariable( name = "id" ) Long id )
    {
        RespuestaApiDto<TitularDetalleDto> respuesta = new RespuestaApiDto<>();
        try
        {
            respuesta.setDatos( servicioTitular.getTitular( id ) );
            respuesta.setEstado( true );
        }
        catch ( EntidadNoEncontradaException ene )
        {
            respuesta.setEstado( false );
            respuesta.setMensaje( ene.getMessage() );
        }
        return respuesta;
    }

    @PostMapping( "/titulares/cobro" )
    public RespuestaApiDto<Object> registrarPagoFactura( @RequestBody PagoDto pagoDto )
    {
        RespuestaApiDto<Object> respuesta = new RespuestaApiDto<>();
        try
        {
            servicioTitular.registrarPago(pagoDto);
            respuesta.setEstado( true );
            respuesta.setMensaje( MensajePresentacion.PAGO_REGISTRADO.getDescripcion() );
        }
        catch ( EntidadNoEncontradaException ene )
        {
            respuesta.setEstado( false );
            respuesta.setMensaje( ene.getMessage() );
        }
        return respuesta;
    }
}
