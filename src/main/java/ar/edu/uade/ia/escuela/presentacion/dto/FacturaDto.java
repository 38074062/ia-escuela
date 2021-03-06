package ar.edu.uade.ia.escuela.presentacion.dto;

import java.time.LocalDate;

public class FacturaDto
{
    private Long id;

    private float total;

    private LocalDate fecha;

    private String titular;

    private LocalDate vencimiento;

    public FacturaDto()
    {
        super();
    }

    public Long getId()
    {
        return id;
    }

    public void setId( Long id )
    {
        this.id = id;
    }

    public float getTotal()
    {
        return total;
    }

    public void setTotal( float total )
    {
        this.total = total;
    }

    public LocalDate getFecha()
    {
        return fecha;
    }

    public void setFecha( LocalDate fecha )
    {
        this.fecha = fecha;
    }

    public String getTitular()
    {
        return titular;
    }

    public void setTitular( String titular )
    {
        this.titular = titular;
    }

    public LocalDate getVencimiento()
    {
        return vencimiento;
    }

    public void setVencimiento( LocalDate vencimiento )
    {
        this.vencimiento = vencimiento;
    }

}
