package ar.edu.uade.ia.escuela.presentacion.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.uade.ia.escuela.presentacion.MensajePresentacion;
import ar.edu.uade.ia.escuela.presentacion.dto.FacturaDto;
import ar.edu.uade.ia.escuela.presentacion.dto.RespuestaApiDto;
import ar.edu.uade.ia.escuela.servicio.ServicioFactura;
import ar.edu.uade.ia.escuela.servicio.error.NombreExistenteException;

@RestController
public class ControladorFactura 
{
	@Autowired
    private ServicioFactura servicioFactura;
	
	@PostMapping( "/facturas" )
    public RespuestaApiDto<Object> generarFactura( @RequestBody FacturaDto factura )
    {
        RespuestaApiDto<Object> respuesta = new RespuestaApiDto<Object>();
        try
        {
        	servicioFactura.generarFactura( factura );
            respuesta.setEstado( true );
            respuesta.setMensaje( MensajePresentacion.FACTURA_CREADA.getDescripcion() );
        }
        catch ( NombreExistenteException e)
        {
            respuesta.setEstado( false );
            respuesta.setMensaje( e.getMessage() );
        }
        return respuesta;
    }

	 @GetMapping("/facturas")
	    public RespuestaApiDto<List<FacturaDto>> listarFacturas()
	    {
	    	RespuestaApiDto<List<FacturaDto>> respuesta = new RespuestaApiDto<List<FacturaDto>>();
	    	respuesta.setDatos(servicioFactura.listarFacturas());
	    	respuesta.setEstado(true);
	    	return respuesta;
	    }
}
