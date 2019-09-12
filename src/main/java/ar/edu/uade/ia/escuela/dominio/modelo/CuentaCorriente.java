package ar.edu.uade.ia.escuela.dominio.modelo;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class CuentaCorriente
    extends EntidadBase
{

    @OneToMany( mappedBy = "cuentaCorriente" )
    private List<ItemCuenta> itemsCuentas;

    private Integer cuentaBancaria;

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
        this.itemsCuentas = itemsCuentas;
    }

    public Integer getCuentaBancaria()
    {
        return cuentaBancaria;
    }

    public void setCuentaBancaria( Integer cuentaBancaria )
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
