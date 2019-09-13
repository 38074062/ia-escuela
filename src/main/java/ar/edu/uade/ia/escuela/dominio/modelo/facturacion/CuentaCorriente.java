package ar.edu.uade.ia.escuela.dominio.modelo.facturacion;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import ar.edu.uade.ia.escuela.dominio.modelo.EntidadBase;

@Entity
public class CuentaCorriente
    extends EntidadBase
{

    @OneToMany( mappedBy = "cuentaCorriente", cascade = CascadeType.ALL )
    private List<ItemCuenta> itemsCuentas = new LinkedList<ItemCuenta>();

    private String cuentaBancaria;

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

    public String getCuentaBancaria()
    {
        return cuentaBancaria;
    }

    public void setCuentaBancaria( String cuentaBancaria )
    {
        this.cuentaBancaria = cuentaBancaria;
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

    public void addFactura( Factura factura )
    {
        ItemCuenta itemCuenta = new ItemCuenta();
        itemCuenta.setFactura( factura );
        this.itemsCuentas.add( itemCuenta );
    }

}
