package ar.edu.uade.ia.escuela.dominio.modelo.facturacion;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import ar.edu.uade.ia.escuela.dominio.modelo.EntidadBase;
import ar.edu.uade.ia.escuela.dominio.modelo.inscripcion.Inscripcion;

@Entity
public class ItemFactura
    extends EntidadBase
{

    @ManyToOne
    private Factura factura;

    @OneToOne
    private Inscripcion inscripcion;

    @OneToMany( mappedBy = "itemFactura",cascade = CascadeType.ALL )
    private List<ServicioFacturado> serviciosFacturados;

    public Factura getFactura()
    {
        return factura;
    }

    public void setFactura( Factura factura )
    {
        this.factura = factura;
    }

    public Inscripcion getInscripcion()
    {
        return inscripcion;
    }

    public void setInscripcion( Inscripcion inscripcion )
    {
        this.inscripcion = inscripcion;
    }

    public List<ServicioFacturado> getServiciosFacturados()
    {
        return serviciosFacturados;
    }

    public void setServiciosFacturados( List<ServicioFacturado> serviciosFacturados )
    {
        if ( serviciosFacturados != null )
        {
            serviciosFacturados.forEach( this::agregarServicioFacturado );
        }
    }

    public void agregarServicioFacturado( ServicioFacturado servicioFacturado )
    {
        if ( this.serviciosFacturados == null )
        {
            this.serviciosFacturados = new LinkedList<>();
        }
        servicioFacturado.setItemFactura( this );
        this.serviciosFacturados.add( servicioFacturado );
    }

    public float getSubTotal()
    {
        return serviciosFacturados.stream().map( ServicioFacturado::getMontoFacturado ).reduce( Float::sum ).orElse( 0F );
    }

}