package ar.edu.uade.ia.escuela.dominio.modelo.facturacion;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import ar.edu.uade.ia.escuela.dominio.modelo.EntidadBase;

@Entity
public class ItemCuenta
    extends EntidadBase
{

    @ManyToOne
    private CuentaCorriente cuentaCorriente;

    @OneToOne
    private Factura factura;

    private LocalDate fechaPago;

    private float monto;

    public ItemCuenta()
    {
        super();
    }

    public CuentaCorriente getCuentaCorriente()
    {
        return cuentaCorriente;
    }

    public void setCuentaCorriente( CuentaCorriente cuentaCorriente )
    {
        this.cuentaCorriente = cuentaCorriente;
    }

    public Factura getFactura()
    {
        return factura;
    }

    public void setFactura( Factura factura )
    {
        this.factura = factura;
    }

    public LocalDate getFechaPago()
    {
        return fechaPago;
    }

    public void setFechaPago( LocalDate fechapago )
    {
        this.fechaPago = fechapago;
    }

    public float getMonto()
    {
        return monto;
    }

    public void setMonto( float monto )
    {
        this.monto = monto;
    }

}
