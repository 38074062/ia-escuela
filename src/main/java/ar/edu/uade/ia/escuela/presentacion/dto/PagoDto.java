package ar.edu.uade.ia.escuela.presentacion.dto;

import java.time.LocalDate;

public class PagoDto
{
    private Long titularId;

    private Long facturaId;

    private float monto;

    private LocalDate fecha;

    private MetodoPagoDto metodoPago;

    public PagoDto()
    {
        super();
    }

    public Long getTitularId()
    {
        return titularId;
    }

    public void setTitularId( Long titularId )
    {
        this.titularId = titularId;
    }

    public Long getFacturaId()
    {
        return facturaId;
    }

    public void setFacturaId( Long facturaId )
    {
        this.facturaId = facturaId;
    }

    public float getMonto()
    {
        return monto;
    }

    public void setMonto( float monto )
    {
        this.monto = monto;
    }

    public LocalDate getFecha()
    {
        return fecha;
    }

    public void setFecha( LocalDate fecha )
    {
        this.fecha = fecha;
    }

    public MetodoPagoDto getMetodoPago()
    {
        return metodoPago;
    }

    public void setMetodoPago( MetodoPagoDto metodoPago )
    {
        this.metodoPago = metodoPago;
    }

}
