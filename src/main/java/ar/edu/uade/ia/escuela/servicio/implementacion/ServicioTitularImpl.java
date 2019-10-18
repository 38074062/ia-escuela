package ar.edu.uade.ia.escuela.servicio.implementacion;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ar.edu.uade.ia.escuela.datos.RepositorioTitular;
import ar.edu.uade.ia.escuela.dominio.error.FacturaNoEncontradaException;
import ar.edu.uade.ia.escuela.dominio.modelo.facturacion.CuentaCorriente;
import ar.edu.uade.ia.escuela.dominio.modelo.inscripcion.Alumno;
import ar.edu.uade.ia.escuela.dominio.modelo.inscripcion.Inscripcion;
import ar.edu.uade.ia.escuela.dominio.modelo.inscripcion.Servicio;
import ar.edu.uade.ia.escuela.dominio.modelo.inscripcion.Titular;
import ar.edu.uade.ia.escuela.presentacion.dto.AlumnoDto;
import ar.edu.uade.ia.escuela.presentacion.dto.EstadoFacturasDto;
import ar.edu.uade.ia.escuela.presentacion.dto.InscripcionDetalleDto;
import ar.edu.uade.ia.escuela.presentacion.dto.PagoDto;
import ar.edu.uade.ia.escuela.presentacion.dto.ServicioDto;
import ar.edu.uade.ia.escuela.presentacion.dto.TitularDetalleDto;
import ar.edu.uade.ia.escuela.presentacion.dto.TitularDto;
import ar.edu.uade.ia.escuela.servicio.ServicioTitular;
import ar.edu.uade.ia.escuela.servicio.cliente.RequestBanco;
import ar.edu.uade.ia.escuela.servicio.cliente.RequestTarjeta;
import ar.edu.uade.ia.escuela.servicio.cliente.RespuestaBanco;
import ar.edu.uade.ia.escuela.servicio.cliente.RespuestaTarjetaCredito;
import ar.edu.uade.ia.escuela.servicio.error.DniExistenteException;
import ar.edu.uade.ia.escuela.servicio.error.EntidadNoEncontradaException;
import ar.edu.uade.ia.escuela.servicio.error.ErrorPagoException;

@Service
@Transactional
public class ServicioTitularImpl
    implements ServicioTitular
{

    @Autowired
    private RepositorioTitular repositorioTitular;

    @Autowired
    private RestTemplate restTemplate;

    @Value( "${cbu}" )
    private String cbuEscuela;

    @Value( "${endpointTarjeta}" )
    private String urlTarjeta;

    @Value( "${endpointBanco}" )
    private String urlBanco;

    @Override
    public void altaTitular( TitularDto titularDto )
    {
        Optional<Titular> optTitular = repositorioTitular.findByDni( titularDto.getDni() );
        if ( optTitular.isPresent() )
        {
            throw new DniExistenteException();
        }
        Titular titular = optTitular.orElse( new Titular() );
        titular.setNombre( titularDto.getNombre() );
        titular.setApellido( titularDto.getApellido() );
        titular.setDni( titularDto.getDni() );
        titular.setDireccion( titularDto.getDireccion() );
        titular.setEmail( titularDto.getEmail() );
        CuentaCorriente cuenta = new CuentaCorriente();
        cuenta.setCuentaBancaria( titularDto.getCuentaBancaria() );
        cuenta.setNroTarjeta(titularDto.getNroTarjeta());
        cuenta.setCodSeg(titularDto.getCodSeg());
        titular.setCuentaCorriente( cuenta );
        titular.setPreferenciaPago(titularDto.getPreferenciaPago());
        titular.setPreferenciaTipoFactura(titularDto.getPreferenciaTipoFactura());
        repositorioTitular.save( titular );
    }

    @Override
    public void bajaTitular( Long id )
    {
        if ( !repositorioTitular.findById( id ).isPresent() )
        {
            throw new EntidadNoEncontradaException( "El titular no existe" );
        }
        repositorioTitular.deleteById( id );
    }

    @Override
    public void modificarTitular( TitularDto titularDto )
    {
        Optional<Titular> titularOpt = repositorioTitular.findById( titularDto.getId() );
        if ( !titularOpt.isPresent() )
        {
            throw new EntidadNoEncontradaException( "El titular no existe" );
        }
        Titular titularGuardado = titularOpt.get();
        titularGuardado.setNombre( titularDto.getNombre() );
        titularGuardado.setApellido( titularDto.getApellido() );
        if ( !titularDto.getDni().equals( titularGuardado.getDni() ) )
        {
            if ( repositorioTitular.findByDni( titularDto.getDni() ).isPresent() )
            {
                throw new DniExistenteException();
            }
        }
        titularGuardado.setDni( titularDto.getDni() );
        titularGuardado.setDireccion( titularDto.getDireccion() );
        titularGuardado.setEmail( titularDto.getEmail() );
        if ( titularGuardado.getCuentaCorriente() != null )
        {
            titularGuardado.getCuentaCorriente().setCuentaBancaria( titularDto.getCuentaBancaria());
            titularGuardado.getCuentaCorriente().setNroTarjeta(titularDto.getNroTarjeta());
            titularGuardado.getCuentaCorriente().setCodSeg(titularDto.getCodSeg());
        }
        titularGuardado.setPreferenciaPago(titularDto.getPreferenciaPago());
        titularGuardado.setPreferenciaTipoFactura(titularDto.getPreferenciaTipoFactura());
        repositorioTitular.save( titularGuardado );
    }

    @Override
    public List<TitularDto> listarTitulares()
    {
        List<Titular> titulares = repositorioTitular.findAll();
        List<TitularDto> titularDto = new ArrayList<TitularDto>();
        for ( Titular s : titulares )
        {
            TitularDto sg = new TitularDto();
            sg.setId( s.getId() );
            sg.setDni( s.getDni() );
            sg.setNombre( s.getNombre() );
            sg.setApellido( s.getApellido() );
            sg.setDireccion( s.getDireccion() );
            sg.setEmail( s.getEmail() );
            sg.setCuentaBancaria( s.getCuentaCorriente().getCuentaBancaria() );
            sg.setPreferenciaPago(s.getPreferenciaPago());
            sg.setPreferenciaTipoFactura(s.getPreferenciaTipoFactura());
            titularDto.add( sg );
        }
        return titularDto;
    }

    @Override
    public TitularDetalleDto getTitular( Long id )
    {
        Optional<Titular> titularOpt = repositorioTitular.findById( id );
        if ( !titularOpt.isPresent() )
        {
            throw new EntidadNoEncontradaException( "El titular no existe" );
        }
        return convertirTitularATitularDetalleDto( titularOpt.get() );
    }

    private TitularDetalleDto convertirTitularATitularDetalleDto( Titular titular )
    {
        TitularDetalleDto titularDetalleDto = new TitularDetalleDto();
        titularDetalleDto.setId( titular.getId() );
        titularDetalleDto.setDni( titular.getDni() );
        titularDetalleDto.setNombre( titular.getNombre() );
        titularDetalleDto.setApellido( titular.getApellido() );
        titularDetalleDto.setDireccion( titular.getDireccion() );
        titularDetalleDto.setEmail( titular.getEmail() );
        titularDetalleDto.setInscripciones( convertirInscripcionesAInscripcionesDetalleDto( titular.getInscripcionesActivas() ) );
        titularDetalleDto.setFacturas( convertirFacturasAEstadoFacturasDto( titular.getCuentaCorriente() ) );
        titularDetalleDto.setPreferenciaPago(titular.getPreferenciaPago());
        titularDetalleDto.setPreferenciaTipoFactura(titular.getPreferenciaTipoFactura());
        return titularDetalleDto;
    }

    private List<EstadoFacturasDto> convertirFacturasAEstadoFacturasDto( CuentaCorriente cuentaCorriente )
    {
        List<EstadoFacturasDto> facturas = new LinkedList<>();
        cuentaCorriente.getItemsCuentas().forEach( item -> {
            EstadoFacturasDto estado = new EstadoFacturasDto();
            estado.setIdFactura( item.getFactura().getId() );
            estado.setDetalle( item.getDescripcion() );
            estado.setFechaVencimiento( item.getFactura().getVencimiento() );
            estado.setMonto( item.getFactura().getTotal() );
            estado.setEstado( item.getEstado().getDescripcion() );
            estado.setMontoPagado( item.getMonto() );
            facturas.add( estado );
        } );
        return facturas;
    }

    private List<InscripcionDetalleDto> convertirInscripcionesAInscripcionesDetalleDto( List<Inscripcion> inscripciones )
    {
        List<InscripcionDetalleDto> inscripcionesDto = new LinkedList<>();
        inscripciones.forEach( inscripcion -> {
            InscripcionDetalleDto inscricpionDto = new InscripcionDetalleDto();
            inscricpionDto.setId( inscripcion.getId() );
            inscricpionDto.setAlumno( convertirAlumnoAAlumnoDto( inscripcion.getAlumno() ) );
            inscricpionDto.setServicios( convertirServiciosAServiciosDto( inscripcion.getServicios() ) );
            inscripcionesDto.add( inscricpionDto );
        } );
        return inscripcionesDto;
    }

    private List<ServicioDto> convertirServiciosAServiciosDto( List<Servicio> servicios )
    {
        List<ServicioDto> serviciosDto = new LinkedList<>();
        servicios.forEach( servicio -> {
            ServicioDto servicioDto = new ServicioDto();
            servicioDto.setId( servicio.getId() );
            servicioDto.setNombre( servicio.getNombre() );
            servicioDto.setTipo( servicio.getTipo() );
            servicioDto.setPrecio( servicio.getPrecio() );
            servicioDto.setCategoria( servicio.getCategoria() );
            serviciosDto.add( servicioDto );
        } );
        return serviciosDto;
    }

    private AlumnoDto convertirAlumnoAAlumnoDto( Alumno alumno )
    {
        AlumnoDto alumnoDto = new AlumnoDto();
        alumnoDto.setId( alumno.getId() );
        alumnoDto.setDni( alumno.getDni() );
        alumnoDto.setNombre( alumno.getNombre() );
        alumnoDto.setApellido( alumno.getApellido() );
        return alumnoDto;
    }

    @Override
    public void registrarPago( PagoDto pagoDto )
    {
        Optional<Titular> titularOpt = repositorioTitular.findById( pagoDto.getTitularId() );
        if ( !titularOpt.isPresent() )
        {
            throw new EntidadNoEncontradaException( "El titular no existe" );
        }
        Titular titular = titularOpt.get();
        if(titular.getPreferenciaPago()=="banco"){
        	try
        	{
        		informarEntidadBanco( titular, pagoDto );
        	}
        	catch ( JsonProcessingException e )
        	{
        		// TODO Auto-generated catch block
        		e.printStackTrace();
        	}
        }else{
        	try
        	{
        		informarEntidadTarjeta( titular, pagoDto);
        	}
        	catch ( JsonProcessingException e )
        	{
        		// TODO Auto-generated catch block
        		e.printStackTrace();
        	}
        }
        try
        {
            titular.registrarPago( pagoDto.getFacturaId(), pagoDto.getFecha(), pagoDto.getMonto() );
            repositorioTitular.save( titular );
        }
        catch ( FacturaNoEncontradaException fne )
        {
            throw new EntidadNoEncontradaException( fne.getMessage() );
        }
    }

    private void informarEntidadTarjeta( Titular titular, PagoDto pagoDto ) throws JsonProcessingException
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType( MediaType.APPLICATION_JSON );
        HttpEntity<String> request;
        request = new HttpEntity<String>(new ObjectMapper().writeValueAsString(crearObjetoTarjeta(titular,pagoDto)), headers);
        ResponseEntity<String> response = restTemplate.exchange(
                                                                urlTarjeta, HttpMethod.POST, request, String.class);
        
            

    }

    private RequestTarjeta crearObjetoTarjeta( Titular titular, PagoDto pagoDto )
    {
        RequestTarjeta request = new RequestTarjeta();
        request.setNroTarjeta( "9345572142215307" );
        request.setCodSeg("342");
        request.setMonto(String.valueOf(pagoDto.getMonto()));
        request.setIdEntidad("1");
        request.setCuotas( "1" );
        return request;
    }

    private void informarEntidadBanco( Titular titular, PagoDto pagoDto )
        throws JsonProcessingException
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType( MediaType.APPLICATION_JSON );
        String movimientos =
            "[" + new ObjectMapper().writeValueAsString( crearObjetoMovimiento( titular.getCuentaCorriente().getCuentaBancaria(),
                                                                                pagoDto ) )
                            + "]";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.set("movimientos",movimientos);
        params.set("user","instituto");
        params.set("origenMovimiento","debito");

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(urlBanco).queryParams(params);
        String url;
        url = builder.build().toUri().toString();
        try
        {
            url = URLDecoder.decode(url, "UTF-8");
        }
        catch ( UnsupportedEncodingException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String response = restTemplate.postForObject( url,"",  String.class );
        
        if ( !response.equals( "Ok" ) )
        {
            throw new ErrorPagoException();
        }
    }

    private RequestBanco crearObjetoMovimiento( String cuentaBancaria, PagoDto pagoDto )
    {
        RequestBanco request = new RequestBanco();
        request.setCbuOrigen( "1230022000119" );
        request.setCbuDestino( cbuEscuela );
        request.setMonto( pagoDto.getMonto() );
        request.setDescripcion( "Pago cuota" );
        return request;
    }
}
