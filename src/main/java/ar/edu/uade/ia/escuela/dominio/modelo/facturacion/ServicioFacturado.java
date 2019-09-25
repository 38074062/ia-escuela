package ar.edu.uade.ia.escuela.dominio.modelo.facturacion;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import ar.edu.uade.ia.escuela.dominio.modelo.EntidadBase;
import ar.edu.uade.ia.escuela.dominio.modelo.inscripcion.Servicio;

@Entity
public class ServicioFacturado
    extends EntidadBase
{
    @ManyToOne
    @JoinColumn( name = "item_factura_id", nullable = false )
    private ItemFactura itemFactura;

    @OneToOne
    private Servicio servicio;

    private String nombre;

    private Float montoFacturado;

    public ServicioFacturado()
    {
        super();
    }

    public Servicio getServicio()
    {
        return servicio;
    }

    public ItemFactura getItemFactura()
    {
        return itemFactura;
    }

    public void setItemFactura( ItemFactura itemFactura )
    {
        this.itemFactura = itemFactura;
    }

    public void setServicio( Servicio servicio )
    {
        this.servicio = servicio;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre( String nombre )
    {
        this.nombre = nombre;
    }

    public Float getMontoFacturado()
    {
        return montoFacturado;
    }

    public void setMontoFacturado( Float montoFacturado )
    {
        this.montoFacturado = montoFacturado;
    }

    public String getDescripcion()
    {
        return nombre + " " + montoFacturado;
    }
}
