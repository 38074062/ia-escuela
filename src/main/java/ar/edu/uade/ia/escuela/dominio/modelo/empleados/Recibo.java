package ar.edu.uade.ia.escuela.dominio.modelo.empleados;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ar.edu.uade.ia.escuela.dominio.modelo.EntidadBase;

@Entity
public class Recibo
    extends EntidadBase
{
    @ManyToOne
    @JoinColumn(name="usuario_id", nullable=false)
    private Usuario usuario;

    private String haber;

    private float precio;

    private float descuento;

    private String horario;

    private Integer horas;

    public Recibo()
    {
        super();
    }

    public Usuario getUsuario()
    {
        return usuario;
    }

    public void setUsuario( Usuario usuario )
    {
        this.usuario = usuario;
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

    public float calcularTotal()
    {
        float total = 0;
        total = precio * horas;
        total = total - total / descuento;
        return total;
    }

}
