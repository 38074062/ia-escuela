package ar.edu.uade.ia.escuela.servicio.implementacion;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import ar.edu.uade.ia.escuela.datos.RepositorioFactura;
import ar.edu.uade.ia.escuela.datos.RepositorioTitular;
import ar.edu.uade.ia.escuela.dominio.modelo.facturacion.Factura;
import ar.edu.uade.ia.escuela.dominio.modelo.facturacion.ItemCuenta;
import ar.edu.uade.ia.escuela.dominio.modelo.facturacion.MetodoPago;
import ar.edu.uade.ia.escuela.dominio.modelo.facturacion.TarjetaCredito;
import ar.edu.uade.ia.escuela.dominio.modelo.facturacion.TransferenciaBancaria;
import ar.edu.uade.ia.escuela.dominio.modelo.inscripcion.Inscripcion;
import ar.edu.uade.ia.escuela.dominio.modelo.inscripcion.Titular;
import ar.edu.uade.ia.escuela.presentacion.dto.FacturaDto;
import ar.edu.uade.ia.escuela.servicio.ServicioFactura;
import ar.edu.uade.ia.escuela.servicio.cliente.banco.PagoBanco;
import ar.edu.uade.ia.escuela.servicio.cliente.banco.PagoBancoException;
import ar.edu.uade.ia.escuela.servicio.cliente.banco.RequestBancoMovimiento;
import ar.edu.uade.ia.escuela.servicio.cliente.tarjeta.PagoTarjeta;
import ar.edu.uade.ia.escuela.servicio.cliente.tarjeta.PagoTarjetaException;
import ar.edu.uade.ia.escuela.servicio.cliente.tarjeta.RequestTarjeta;

@Service
@Transactional
public class ServicioFacturaImpl
    implements ServicioFactura
{
    private static final Logger LOG = Logger.getGlobal();

    @Value( "${fechaVencimientoDias}" )
    private Long fechaVencimientoDias;

    @Value( "${cantidadCuotas}" )
    private Integer cantidadCuotas;

    @Autowired
    private RepositorioTitular repositorioTitulares;

    @Autowired
    private RepositorioFactura repositorioFactura;

    @Value( "${idEntidadEscuela}" )
    private String idEntidad;

    @Value( "${cbu}" )
    private String cbuEscuela;

    @Autowired
    private PagoBanco pagoBanco;

    @Autowired
    private PagoTarjeta pagoTarjeta;

    @Override
    public void generarFactura()
    {
        List<Factura> facturas = new LinkedList<>();
        List<Titular> titulares = repositorioTitulares.findAll();
        Map<Factura, RequestBancoMovimiento> movimientosBanco = new HashMap<>();
        Map<Factura, RequestTarjeta> movimientosTarjeta = new HashMap<>();
        titulares.forEach( titular -> {
            Factura factura = generarYGuardarFactura( titular );
            facturas.add( factura );
            if ( tieneTarjetaConDebitoAutomatico( titular ) )
            {
                factura.getTitular();
                movimientosTarjeta.put( factura, crearMovimientoTarjeta( titular.getCuentaCorriente().getMetodoPago(),
                                                                         factura.getTotal() ) );
            }
            if ( tieneCuentaBancariaConDebitoAutomatico( titular ) )
            {
                movimientosBanco.put( factura, crearMovimientoBanco( titular.getCuentaCorriente().getMetodoPago(),
                                                                     factura.getTotal() ) );
            }
            List<ItemCuenta> itemsCuenta = titular.getCuentaCorriente().getItemsCuentas();
            factura.setId( itemsCuenta.get( itemsCuenta.size() - 1 ).getFactura().getId() );
        } );
        debitarFacturas( movimientosTarjeta, movimientosBanco );
    }

    private void debitarFacturas( Map<Factura, RequestTarjeta> movimientosTarjeta,
                                  Map<Factura, RequestBancoMovimiento> movimientosBanco )
    {
        Set<Factura> facturasCobradas = new HashSet<>();
        facturasCobradas.addAll( facturasCobradasConTarjeta( movimientosTarjeta ) );
        facturasCobradas.addAll( facturasCobradasConTransferencia( movimientosBanco ) );
        registrarPagoFactura( facturasCobradas );
    }

    private Set<Factura> facturasCobradasConTransferencia( Map<Factura, RequestBancoMovimiento> movimientosBanco )
    {
        if ( !movimientosBanco.isEmpty() )
        {
            try
            {
                pagoBanco.registrarPagos( movimientosBanco.values().stream().collect( Collectors.toList() ) );
            }
            catch ( PagoBancoException e )
            {
                eliminarCbuConErrores( e.getErroresCbu(), movimientosBanco );
            }
        }
        return movimientosBanco.keySet();
    }

    private Set<Factura> facturasCobradasConTarjeta( Map<Factura, RequestTarjeta> movimientosTarjeta )
    {
        if ( !movimientosTarjeta.isEmpty() )
        {
            try
            {
                pagoTarjeta.facturarTarjeta( movimientosTarjeta.values().stream().collect( Collectors.toList() ) );
            }
            catch ( PagoTarjetaException e )
            {

                eliminarTarjetasConErrores( e.getErroresTarjeta(), movimientosTarjeta );
            }
            catch ( JsonProcessingException e )
            {
                LOG.warning( e.getMessage() );
            }
        }
        return movimientosTarjeta.keySet();
    }

    private void eliminarTarjetasConErrores( List<Integer> erroresTarjeta,
                                             Map<Factura, RequestTarjeta> movimientosTarjeta )
    {
        List<RequestTarjeta> movimientos = movimientosTarjeta.values().stream().collect( Collectors.toList() );
        erroresTarjeta.forEach( error -> movimientosTarjeta.values().remove( movimientos.get( error ) ) );
    }

    private void registrarPagoFactura( Set<Factura> facturasCobradas )
    {
        facturasCobradas.forEach( factura -> {
            Titular titular = factura.getTitular();
            titular.registrarPago( factura.getId(), LocalDate.now(), factura.getTotal() );
            repositorioTitulares.save( titular );
        } );
    }

    private void eliminarCbuConErrores( List<String> erroresCbu, Map<Factura, RequestBancoMovimiento> movimientosBanco )
    {
        erroresCbu.forEach( error -> {
            Optional<Entry<Factura, RequestBancoMovimiento>> movimientoConError =
                movimientosBanco.entrySet().stream().filter( movimiento -> movimiento.getValue().getCbuOrigen().equals( error ) ).findFirst();
            if ( movimientoConError.isPresent() )
            {
                movimientosBanco.remove( movimientoConError.get().getKey() );
            }
        } );
    }

    private Factura generarYGuardarFactura( Titular titular )
    {
        Factura factura = new Factura();
        factura.setTitular( titular );
        factura.setTipo( titular.getPreferenciaTipoFactura() );
        factura.setFecha( LocalDate.now() );
        factura.setVencimiento( LocalDate.now().plusDays( fechaVencimientoDias ) );
        List<Inscripcion> inscripcionesActivas = titular.getInscripcionesActivas();
        inscripcionesActivas.forEach( inscripcion -> factura.addInscripcion( inscripcion, cantidadCuotas ) );
        titular.addFacturaACuentaCorriente( factura );
        repositorioTitulares.save( titular );
        return factura;
    }

    private RequestBancoMovimiento crearMovimientoBanco( MetodoPago metodoPago, float total )
    {
        RequestBancoMovimiento request = new RequestBancoMovimiento();
        request.setCbuOrigen( ( (TransferenciaBancaria) metodoPago ).getCuentaBancaria() );
        request.setCbuDestino( cbuEscuela );
        request.setMonto( total );
        request.setDescripcion( "Pago cuota" );
        return request;
    }

    private RequestTarjeta crearMovimientoTarjeta( MetodoPago metodoPago, float total )
    {
        RequestTarjeta request = new RequestTarjeta();
        request.setNroTarjeta( ( (TarjetaCredito) metodoPago ).getNroTarjeta() );
        request.setCodSeg( ( (TarjetaCredito) metodoPago ).getCodSeg() );
        request.setMonto( String.valueOf( total ) );
        request.setIdEntidad( idEntidad );
        request.setCuotas( "1" );
        return request;
    }

    private boolean tieneCuentaBancariaConDebitoAutomatico( Titular titular )
    {
        return titular.getCuentaCorriente().getMetodoPago() instanceof TransferenciaBancaria
            && titular.getCuentaCorriente().getMetodoPago().getDebitoAutomatico();
    }

    private boolean tieneTarjetaConDebitoAutomatico( Titular titular )
    {
        return titular.getCuentaCorriente().getMetodoPago() instanceof TarjetaCredito
            && titular.getCuentaCorriente().getMetodoPago().getDebitoAutomatico();
    }

    @Override
    public List<FacturaDto> listarFacturas()
    {
        List<Factura> facturas = repositorioFactura.findAll();
        List<FacturaDto> facturaDto = new ArrayList<FacturaDto>();
        for ( Factura s : facturas )
        {
            FacturaDto sg = new FacturaDto();
            sg.setId( s.getId() );
            sg.setTotal( s.getTotal() );
            sg.setTitular( s.getNombreCompletoTitular() );
            sg.setFecha( s.getFecha() );
            sg.setVencimiento( s.getVencimiento() );
            facturaDto.add( sg );
        }
        return facturaDto;
    }

}
