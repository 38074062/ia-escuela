package ar.edu.uade.ia.escuela.dominio.modelo;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Inscripcion
    extends EntidadBase
{
    @OneToOne
    private Titular titular;

    @OneToOne
    private Alumno alumno;

    @OneToMany
    private List<Servicio> servicios;

    private Boolean activo;

    public Inscripcion()
    {
        super();
    }

    public float getCuota()
    {
        float cuota;
        cuota = calcularTotal() / 12;
        return cuota;
    }

    private float calcularTotal()
    {
        float total = 0;
        for ( Servicio s : servicios )
        {
            total = total + s.getPrecio();
        }
        return total;
    }

    public Titular getTitular()
    {
        return titular;
    }

    public void setTitular( Titular titular )
    {
        this.titular = titular;
    }

    public Alumno getAlumno()
    {
        return alumno;
    }

    public void setAlumno( Alumno alumno )
    {
        this.alumno = alumno;
    }

    public List<Servicio> getServicios()
    {
        return servicios;
    }

    public void setServicios( List<Servicio> servicios )
    {
        this.servicios = servicios;
    }

    public Boolean estaActiva()
    {
        return activo;
    }

    public void setActivo( Boolean activo )
    {
        this.activo = activo;
    }

    public Factura generarFacturaFecha( LocalDate now )
    {
        // TODO Auto-generated method stub
        return null;
    }
}
