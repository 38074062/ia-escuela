package ar.edu.uade.ia.escuela.dominio.modelo;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class ItemFactura
    extends EntidadBase
{

    @ManyToOne
    private Factura factura;

    @OneToOne
    private Inscripcion inscripcion;

    @OneToMany( mappedBy = "itemFactura" )
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
        this.serviciosFacturados = serviciosFacturados;
    }

    public float getSubTotal()
    {
        return serviciosFacturados.stream().map( ServicioFacturado::getMontoFacturado ).reduce( Float::sum ).orElse( 0F );
    }

}
