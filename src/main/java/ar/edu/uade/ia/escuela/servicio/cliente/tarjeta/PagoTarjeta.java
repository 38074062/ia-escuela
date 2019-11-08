package ar.edu.uade.ia.escuela.servicio.cliente.tarjeta;

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

    public void registrarPagoTarjeta( MetodoPago metodoPago, PagoDto pagoDto )
        throws JsonProcessingException, PagoTarjetaException
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType( MediaType.APPLICATION_JSON );
        HttpEntity<String> request;
        request =
            new HttpEntity<String>( new ObjectMapper().writeValueAsString( crearObjetoTarjeta( metodoPago, pagoDto ) ),
                                    headers );
        ResponseEntity<String> response = restTemplate.exchange( urlTarjeta, HttpMethod.POST, request, String.class );
        if ( !response.getStatusCode().equals( HttpStatus.OK ) )
        {
            throw new PagoTarjetaException( response.getBody() );
        }
    }

    private RequestTarjeta crearObjetoTarjeta( MetodoPago metodoPago, PagoDto pagoDto )
    {
        RequestTarjeta request = new RequestTarjeta();
        request.setNroTarjeta( "9345572142215307"/* ( (TarjetaCredito) metodoPago ).getNroTarjeta() */ );
        request.setCodSeg( "342" /* ( (TarjetaCredito) metodoPago ).getCodSeg() */ );
        request.setMonto( String.valueOf( pagoDto.getMonto() ) );
        request.setIdEntidad( idEntidad );
        request.setCuotas( "1" );
        return request;
    }

}
