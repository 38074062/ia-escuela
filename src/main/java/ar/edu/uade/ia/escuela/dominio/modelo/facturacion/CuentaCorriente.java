package ar.edu.uade.ia.escuela.dominio.modelo.facturacion;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import ar.edu.uade.ia.escuela.dominio.error.FacturaNoEncontradaException;
import ar.edu.uade.ia.escuela.dominio.modelo.EntidadBase;

@Entity
public class CuentaCorriente
    extends EntidadBase
{

    @OneToMany( mappedBy = "cuentaCorriente", cascade = CascadeType.ALL )
    private List<ItemCuenta> itemsCuentas = new LinkedList<ItemCuenta>();

    private MetodoPago metodoPago;

    public CuentaCorriente()
    {
        super();
    }

    public List<ItemCuenta> getItemsCuentas()
    {
        return itemsCuentas;
    }

    public void setItemsCuentas( List<ItemCuenta> itemsCuentas )
    {
        if ( itemsCuentas != null )
        {
            itemsCuentas.forEach( this::agregarItem );
        }
    }

    public void agregarItem( ItemCuenta item )
    {
        if ( this.itemsCuentas == null )
        {
            this.itemsCuentas = new LinkedList<>();
        }
        item.setCuentaCorriente( this );
        this.itemsCuentas.add( item );
    }

    public float estadoCuenta()
    {
        float aux = 0;
        for ( ItemCuenta i : itemsCuentas )
        {
            aux += i.getFactura().getTotal() - i.getMonto();
        }
        return aux;
    }

    public void agregarFactura( Factura factura )
    {
        ItemCuenta itemCuenta = new ItemCuenta();
        itemCuenta.setCuentaCorriente( this );
        itemCuenta.setFactura( factura );
        this.itemsCuentas.add( itemCuenta );
    }

    public void registrarPago( Long facturaId, LocalDate fecha, float monto )
    {
        ItemCuenta item = buscarFactura( facturaId );
        if ( item == null )
        {
            throw new FacturaNoEncontradaException( "La factura ingresada no existe" );
        }
        item.registrarPago( fecha, monto );

    }

    private ItemCuenta buscarFactura( Long facturaId )
    {
        for ( ItemCuenta item : itemsCuentas )
        {
            if ( item.correspondeAFactura( facturaId ) )
            {
                return item;
            }
        }
        return null;
    }

    public MetodoPago getMetodoPago()
    {
        return metodoPago;
    }

    public void setMetodoPago( MetodoPago metodoPago )
    {
        this.metodoPago = metodoPago;
    }

}
