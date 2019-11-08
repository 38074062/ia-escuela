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

import com.fasterxml.jackson.core.JsonProcessingException;

import ar.edu.uade.ia.escuela.dominio.modelo.facturacion.MetodoPago;
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
        throws JsonProcessingException, PagoBancoException
    {
        List<RequestBanco> requestBody = new LinkedList<>();
        requestBody.add( crearObjetoMovimiento( metodoPago, pagoDto ) );
        enviarRequest( requestBody );
    }

    private void enviarRequest( List<RequestBanco> requestBody )
        throws JsonProcessingException, PagoBancoException
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType( MediaType.APPLICATION_JSON );
        HttpEntity<Object> request = new HttpEntity<>( requestBody, headers );
        ResponseEntity<ResponseBanco> respuesta =
            restTemplate.exchange( urlBanco, HttpMethod.POST, request, ResponseBanco.class );
        if ( respuesta.getBody().huboError() )
        {
            throw new PagoBancoException( respuesta.getBody().getError() );
        }
    }

    private RequestBanco crearObjetoMovimiento( MetodoPago metodoPago, PagoDto pagoDto )
    {
        RequestBanco request = new RequestBanco();
        request.setCbuOrigen( "1230022000119" );
        request.setCbuDestino( cbuEscuela );
        request.setMonto( pagoDto.getMonto() );
        request.setDescripcion( "Pago cuota" );
        return request;
    }

    public void registrarPagoSueldos( List<RequestBanco> liquidaciones )
        throws JsonProcessingException, PagoBancoException
    {
        enviarRequest( liquidaciones );
    }

}
