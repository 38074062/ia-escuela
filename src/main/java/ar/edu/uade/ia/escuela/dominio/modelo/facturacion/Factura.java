package ar.edu.uade.ia.escuela.dominio.modelo.facturacion;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import ar.edu.uade.ia.escuela.dominio.modelo.EntidadBase;
import ar.edu.uade.ia.escuela.dominio.modelo.inscripcion.Inscripcion;
import ar.edu.uade.ia.escuela.dominio.modelo.inscripcion.Servicio;
import ar.edu.uade.ia.escuela.dominio.modelo.inscripcion.Titular;

@Entity
public class Factura
    extends EntidadBase
{
    private LocalDate fecha;

    private String tipo;

    private LocalDate vencimiento;

    @OneToOne
    private Titular titular;

    @OneToMany( mappedBy = "factura", cascade = CascadeType.ALL )
    private List<ItemFactura> items = new LinkedList<>();

    public Factura()
    {
        super();
    }

    public float getTotal()
    {
        return items.stream().map( ItemFactura::getSubTotal ).reduce( Float::sum ).orElse( 0F );
    }

    public LocalDate getFecha()
    {
        return fecha;
    }

    public void setFecha( LocalDate fecha )
    {
        this.fecha = fecha;
    }

    public Titular getTitular()
    {
        return titular;
    }

    public void setTitular( Titular titular )
    {
        this.titular = titular;
    }

    public LocalDate getVencimiento()
    {
        return vencimiento;
    }

    public String getTipo()
    {
        return tipo;
    }

    public void setTipo( String tipo )
    {
        this.tipo = tipo;
    }

    public void setVencimiento( LocalDate ven )
    {
        vencimiento = ven;
    }

    public void addInscripcion( Inscripcion inscripcion, Integer cantidadCuotas )
    {
        ItemFactura itemFactura = new ItemFactura();
        itemFactura.setInscripcion( inscripcion );
        List<ServicioFacturado> serviciosFacturados = new LinkedList<>();
        for(Servicio servicio : inscripcion.getServicios()) {
            ServicioFacturado servicioFacturado = new ServicioFacturado();
            servicioFacturado.setNombre( servicio.getNombre() );
            servicioFacturado.setMontoFacturado( servicio.getCuota( cantidadCuotas ) );
            serviciosFacturados.add( servicioFacturado );    
        }
        
        itemFactura.setServiciosFacturados( serviciosFacturados );
        itemFactura.setFactura( this );
        items.add( itemFactura );
    }

    public Boolean estaPagada( float monto )
    {
        return this.getTotal() <= monto;
    }

    public Boolean estaVencida( LocalDate fecha )
    {
        return vencimiento.isBefore( fecha );
    }

    public String getDescripcion()
    {
        return items.stream().map( ItemFactura::getDescripcion).collect(Collectors.joining("/n"));
    }

    public boolean esFactura( Long facturaId )
    {
        return this.id.equals( facturaId );
    }

    public String getNombreCompletoTitular()
    {
        return titular.getNombreCompleto();
    }

}
