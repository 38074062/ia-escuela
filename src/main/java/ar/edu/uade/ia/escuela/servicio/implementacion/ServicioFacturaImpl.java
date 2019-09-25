package ar.edu.uade.ia.escuela.servicio.implementacion;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ar.edu.uade.ia.escuela.datos.RepositorioFactura;
import ar.edu.uade.ia.escuela.datos.RepositorioTitular;
import ar.edu.uade.ia.escuela.dominio.modelo.facturacion.Factura;
import ar.edu.uade.ia.escuela.dominio.modelo.inscripcion.Inscripcion;
import ar.edu.uade.ia.escuela.dominio.modelo.inscripcion.Titular;
import ar.edu.uade.ia.escuela.presentacion.dto.FacturaDto;
import ar.edu.uade.ia.escuela.servicio.ServicioFactura;

@Service
@Transactional
public class ServicioFacturaImpl
    implements ServicioFactura
{
    @Value( "${fechaVencimientoDias}" )
    private Long fechaVencimientoDias;

    @Value( "${cantidadCuotas}" )
    private Integer cantidadCuotas;

    @Autowired
    private RepositorioTitular repositorioTitulares;

    @Autowired
    private RepositorioFactura repositorioFactura;

    @Override
    public void generarFactura()
    {
        List<Factura> facturas = new LinkedList<>();
        List<Titular> titulares = repositorioTitulares.findAll();
        titulares.forEach( titular -> {
            Factura factura = new Factura();
            factura.setTitular( titular );
            factura.setTipo( titular.getPreferenciaTipoFactura() );
            factura.setFecha( LocalDate.now() );
            factura.setVencimiento( LocalDate.now().plusDays( fechaVencimientoDias ) );
            List<Inscripcion> inscripcionesActivas = titular.getInscripcionesActivas();
            inscripcionesActivas.forEach( inscripcion -> {
                factura.addInscripcion( inscripcion, cantidadCuotas );
            } );
            facturas.add( factura );
            titular.addFacturaACuentaCorriente( factura );
            repositorioTitulares.save( titular );
        } );
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
