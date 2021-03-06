package ar.edu.uade.ia.escuela.dominio.modelo.inscripcion;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import ar.edu.uade.ia.escuela.dominio.modelo.EntidadBase;

@Inheritance( strategy = InheritanceType.JOINED )
@Entity
public abstract class Servicio
    extends EntidadBase
{
    private String nombre;

    private String tipo;

    private float precio;

    public Servicio()
    {
        super();
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre( String nom )
    {
        nombre = nom;
    }

    public String getTipo()
    {
        return tipo;
    }

    public void setTipo( String tipo )
    {
        this.tipo = tipo;
    }

    public float getPrecio()
    {
        return precio;
    }

    public void setPrecio( float precio )
    {
        this.precio = precio;
    }

    public abstract String getCategoria();

    public Float getCuota(Integer cantidadCuotas)
    {
        return getPrecio() / cantidadCuotas;
    }
}
