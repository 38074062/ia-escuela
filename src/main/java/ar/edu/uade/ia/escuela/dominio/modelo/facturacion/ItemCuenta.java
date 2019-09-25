package ar.edu.uade.ia.escuela.dominio.modelo.facturacion;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import ar.edu.uade.ia.escuela.dominio.modelo.EntidadBase;

@Entity
public class ItemCuenta
    extends EntidadBase
{

    @ManyToOne
    @JoinColumn(name="cuenta_corriente_id", nullable=false)
    private CuentaCorriente cuentaCorriente;

    @OneToOne( cascade = CascadeType.ALL )
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

    public EstadoFactura getEstado()
    {
        if ( !factura.estaPagada( monto ) )
        {
            if ( factura.estaVencida( LocalDate.now() ) )
            {
                return EstadoFactura.VENCIDA;
            }
            return EstadoFactura.FACTURADA;
        }
        return EstadoFactura.PAGADA;
    }

    public String getDescripcion()
    {
        return factura.getDescripcion();
    }

    public boolean correspondeAFactura( Long facturaId )
    {
        return factura.esFactura( facturaId );
    }

    public void registrarPago( LocalDate fecha, float monto )
    {
        this.monto += monto;
        this.fechaPago = fecha;
    }

}
