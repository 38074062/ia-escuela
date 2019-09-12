package ar.edu.uade.ia.escuela.dominio.modelo;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Factura
    extends EntidadBase
{
    private LocalDate fecha;

    private String tipo;

    private LocalDate vencimiento;

    @OneToOne
    private Titular titular;

    @OneToMany( mappedBy = "factura" )
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

    public void addInscripcion( Inscripcion inscripcion )
    {
        ItemFactura itemFactura = new ItemFactura();
        itemFactura.setInscripcion( inscripcion );
        List<ServicioFacturado> serviciosFacturados = new LinkedList<>();
        inscripcion.getServicios().forEach( servicio -> {
            ServicioFacturado servicioFacturado = new ServicioFacturado();
            servicioFacturado.setNombre( servicio.getNombre() );
            servicioFacturado.setMontoFacturado( servicio.getPrecio() );
            serviciosFacturados.add( servicioFacturado );
        } );
        itemFactura.setServiciosFacturados( serviciosFacturados );
        items.add( itemFactura );
    }
}
