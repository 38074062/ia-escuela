package ar.edu.uade.ia.escuela.presentacion.dto;

import java.time.LocalDate;

public class EstadoFacturasDto
{
    private Long idFactura;

    private String detalle;

    private LocalDate fechaVencimiento;

    private Float monto;

    private String estado;

    public EstadoFacturasDto()
    {
        super();
    }

    public Long getIdFactura()
    {
        return idFactura;
    }

    public void setIdFactura( Long idFactura )
    {
        this.idFactura = idFactura;
    }

    public String getDetalle()
    {
        return detalle;
    }

    public void setDetalle( String detalle )
    {
        this.detalle = detalle;
    }

    public LocalDate getFechaVencimiento()
    {
        return fechaVencimiento;
    }

    public void setFechaVencimiento( LocalDate fechaVencimiento )
    {
        this.fechaVencimiento = fechaVencimiento;
    }

    public Float getMonto()
    {
        return monto;
    }

    public void setMonto( Float monto )
    {
        this.monto = monto;
    }

    public String getEstado()
    {
        return estado;
    }

    public void setEstado( String estado )
    {
        this.estado = estado;
    }

}
