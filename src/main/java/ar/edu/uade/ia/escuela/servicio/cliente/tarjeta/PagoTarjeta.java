package ar.edu.uade.ia.escuela.servicio.cliente.tarjeta;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ar.edu.uade.ia.escuela.dominio.modelo.facturacion.MetodoPago;
import ar.edu.uade.ia.escuela.dominio.modelo.facturacion.TarjetaCredito;
import ar.edu.uade.ia.escuela.presentacion.dto.PagoDto;

@Component
public class PagoTarjeta
{

    @Autowired
    private RestTemplate restTemplate;

    @Value( "${endpointTarjeta}" )
    private String urlTarjeta;

    @Value( "${idEntidadEscuela}" )
    private String idEntidad;

    @Value( "${erroresTransaccionTarjeta}" )
    private String[] erroresTarjeta;

    private Logger logger = LoggerFactory.getLogger( PagoTarjeta.class );

    public void registrarPagoTarjeta( MetodoPago metodoPago, PagoDto pagoDto )
        throws JsonProcessingException, PagoTarjetaException
    {
        enviarRequest( Arrays.asList( crearObjetoTarjeta( metodoPago, pagoDto ) ) );
    }

    private void enviarRequest( List<RequestTarjeta> movimientos )
        throws JsonProcessingException, PagoTarjetaException
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType( MediaType.APPLICATION_JSON );
        HttpEntity<String> request;
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put( "movimientos", movimientos );
        request = new HttpEntity<String>( new ObjectMapper().writeValueAsString( requestBody ), headers );
        ResponseEntity<String> response = restTemplate.exchange( urlTarjeta, HttpMethod.POST, request, String.class );
        if ( !response.getStatusCode().equals( HttpStatus.OK ) )
        {
            logger.error( response.getBody() );
            throw new PagoTarjetaException( response.getBody() );
        }
        if ( response.getStatusCode().equals( HttpStatus.OK )
            && esRespuestaDeError( procesarErrores( response.getBody() ) ) )
        {
            logger.error( response.getBody() );
            throw new PagoTarjetaException( response.getBody(), procesarErrores( response.getBody() ) );
        }
    }

    private boolean esRespuestaDeError( List<Integer> errores )
    {
        return !errores.isEmpty();
    }

    private List<Integer> procesarErrores( String error )
    {
        error = error.replaceAll( "[\\[\\]'\"\"]+", "" );
        List<Integer> erroresTarjeta = new LinkedList<>();
        String[] errores = error.split( "," );
        for ( Integer index = 0; index < errores.length; index++ )
        {
            if ( esError( errores[index] ) )
            {
                erroresTarjeta.add( index );
            }
        }
        return erroresTarjeta;
    }

    private boolean esError( String error )
    {
        for ( String errorPosible : erroresTarjeta )
        {
            if ( error.equals( errorPosible ) )
            {
                return true;
            }
        }
        return false;
    }

    private RequestTarjeta crearObjetoTarjeta( MetodoPago metodoPago, PagoDto pagoDto )
    {
        RequestTarjeta request = new RequestTarjeta();
        request.setNroTarjeta( ( (TarjetaCredito) metodoPago ).getNroTarjeta() );
        request.setCodSeg( ( (TarjetaCredito) metodoPago ).getCodSeg() );
        request.setMonto( String.valueOf( pagoDto.getMonto() ) );
        request.setIdEntidad( idEntidad );
        request.setCuotas( "1" );
        return request;
    }

    public void facturarTarjeta( List<RequestTarjeta> transacciones )
        throws JsonProcessingException, PagoTarjetaException
    {
        enviarRequest( transacciones );
    }
}
