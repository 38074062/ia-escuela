package ar.edu.uade.ia.escuela.servicio.implementacion;

import java.time.LocalDate;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.uade.ia.escuela.datos.RepositorioFactura;
import ar.edu.uade.ia.escuela.datos.RepositorioInscripcion;
import ar.edu.uade.ia.escuela.datos.RepositorioTitular;
import ar.edu.uade.ia.escuela.dominio.modelo.Factura;
import ar.edu.uade.ia.escuela.dominio.modelo.Titular;
import ar.edu.uade.ia.escuela.dominio.modelo.Inscripcion;
import ar.edu.uade.ia.escuela.presentacion.dto.FacturaDto;
import ar.edu.uade.ia.escuela.servicio.ServicioFactura;

@Service
@Transactional
public class ServicioFacturaImpl
    implements ServicioFactura
{
    private static final Long FECHA_VENCIMIENTO_DIAS = 7L;

    /*
     * @Autowired private RepositorioTitular repositorioTitulares;
     */

    @Autowired
    private RepositorioFactura repositorioFacturas;

    @Override
    public void generarFactura()
    {
        List<Factura> facturas = new LinkedList<>();
        List<Titular> titulares = new LinkedList<>();
        titulares.forEach( titular -> {
            Factura factura = new Factura();
            factura.setTitular( titular );
            factura.setTipo( titular.getTipoFacturacion() );
            factura.setFecha( LocalDate.now() );
            factura.setVencimiento( LocalDate.now().plusDays( FECHA_VENCIMIENTO_DIAS ) );
            List<Inscripcion> inscripcionesActivas = titular.getInscripcionesActivas();
            inscripcionesActivas.forEach( inscripcion -> {
                factura.addInscripcion( inscripcion );
            } );
            facturas.add( factura );
        } );
        repositorioFacturas.saveAll( facturas );
    }

    @Override
    public List<FacturaDto> listarFacturas()
    {
        List<Factura> facturas = RepositorioFactura.findAll();
        List<FacturaDto> facturaDto = new ArrayList<FacturaDto>();
        for ( Factura s : facturas )
        {
            FacturaDto sg = new FacturaDto();
            sg.setId( s.getId() );
            sg.setTotal( s.getTotal() );
            sg.setTipo( s.getTipo() );
            sg.setFecha( s.getFecha() );
            sg.setVencimiento( s.getVencimiento() );
            facturaDto.add( sg );
        }
        return facturaDto;
    }

}
