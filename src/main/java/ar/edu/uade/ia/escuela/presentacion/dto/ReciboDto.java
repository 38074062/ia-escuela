package ar.edu.uade.ia.escuela.presentacion.dto;

public class ReciboDto
{
    private Long idEmpleado;

    private String haber;

    private float precio;

    private float descuento;

    private String horario;

    private Integer horas;

    public Long getIdEmpleado()
    {
        return idEmpleado;
    }

    public void setIdEmpleado( Long idEmpleado )
    {
        this.idEmpleado = idEmpleado;
    }

    public String getHaber()
    {
        return haber;
    }

    public void setHaber( String haber )
    {
        this.haber = haber;
    }

    public float getPrecio()
    {
        return precio;
    }

    public void setPrecio( float precio )
    {
        this.precio = precio;
    }

    public float getDescuento()
    {
        return descuento;
    }

    public void setDescuento( float descuento )
    {
        this.descuento = descuento;
    }

    public String getHorario()
    {
        return horario;
    }

    public void setHorario( String horario )
    {
        this.horario = horario;
    }

    public Integer getHoras()
    {
        return horas;
    }

    public void setHoras( Integer horas )
    {
        this.horas = horas;
    }

}
