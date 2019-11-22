package ar.edu.uade.ia.escuela.servicio.cliente.banco;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

import ar.edu.uade.ia.escuela.dominio.modelo.facturacion.MetodoPago;
import ar.edu.uade.ia.escuela.dominio.modelo.facturacion.TransferenciaBancaria;
import ar.edu.uade.ia.escuela.presentacion.dto.PagoDto;

@Component
public class PagoBanco
{
    @Value( "${endpointBanco}" )
    private String urlBanco;

    @Value( "${cbu}" )
    private String cbuEscuela;

    @Autowired
    private RestTemplate restTemplate;

    public void registrarPagoBanco( MetodoPago metodoPago, PagoDto pagoDto )
        throws PagoBancoException
    {
        List<RequestBancoMovimiento> requestBody = new LinkedList<>();
        RequestBanco requestBanco = new RequestBanco();
        requestBanco.setOrigenMovimiento( "debito" );
        requestBanco.setUser( "instituto" );
        requestBody.add( crearObjetoMovimiento( metodoPago, pagoDto ) );
        requestBanco.setMovimientos( convertirMovimientosAFormato( requestBody ) );
        enviarRequest( requestBanco );
    }

    private String convertirMovimientosAFormato( List<RequestBancoMovimiento> requestBody )
    {
        StringBuilder builder = new StringBuilder().append( "[" );
        requestBody.forEach( movimiento -> {
            builder.append( "{\"cbuOrigen\":\"" );
            builder.append( movimiento.getCbuOrigen() );
            builder.append( "\",\"cbuDestino\":\"" ).append( movimiento.getCbuDestino() );
            builder.append( "\",\"monto\":" ).append( movimiento.getMonto() ).append( ",\"descripcion\":\"" );
            builder.append( movimiento.getDescripcion() );
            builder.append( "\"}," );
        } );
        String preString = builder.toString();
        StringBuilder finalBuilder = new StringBuilder();
        finalBuilder.append( preString.substring( 0, preString.length() - 1 ) ).append( "]" );
        return finalBuilder.toString();
    }

    private void enviarRequest( RequestBanco requestBody )
        throws PagoBancoException
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType( MediaType.APPLICATION_JSON );
        Gson gson = new Gson();
        String body = gson.toJson( requestBody );
        HttpEntity<String> request = new HttpEntity<>( body, headers );
        ResponseEntity<RespuestaBanco> respuesta =
            restTemplate.exchange( urlBanco, HttpMethod.POST, request, RespuestaBanco.class );
        if ( respuesta.getBody().huboError() )
        {
            throw new PagoBancoException( respuesta.getBody().getError(),
                                          procesarErrores( respuesta.getBody().getError() ) );
        }
    }

    private List<String> procesarErrores( String error )
    {
        List<String> erroresCbu = new LinkedList<>();
        String[] errores = error.split( "\\r?\\n" );
        for ( String mensajeError : errores )
        {
            String cbuError = mensajeError.replaceAll( "\\D+", "" );
            erroresCbu.add( cbuError );
        }
        return erroresCbu;
    }

    private RequestBancoMovimiento crearObjetoMovimiento( MetodoPago metodoPago, PagoDto pagoDto )
    {
        RequestBancoMovimiento request = new RequestBancoMovimiento();
        request.setCbuOrigen( ( (TransferenciaBancaria) metodoPago ).getCuentaBancaria() );
        request.setCbuDestino( cbuEscuela );
        request.setMonto( pagoDto.getMonto() );
        request.setDescripcion( "Pago cuota" );
        return request;
    }

    public void registrarPagos( List<RequestBancoMovimiento> movimientos )
        throws PagoBancoException
    {
        RequestBanco requestBanco = new RequestBanco();
        requestBanco.setOrigenMovimiento( "debito" );
        requestBanco.setUser( "instituto" );
        requestBanco.setMovimientos( convertirMovimientosAFormato( movimientos ) );
        enviarRequest( requestBanco );
    }

}
