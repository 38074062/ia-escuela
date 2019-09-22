package ar.edu.uade.ia.escuela.dominio.modelo.inscripcion;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import ar.edu.uade.ia.escuela.dominio.modelo.EntidadBase;

@Entity
public class Inscripcion
    extends EntidadBase
{
    @ManyToOne
    @JoinColumn(name="titular_id", nullable=false)
    private Titular titular;

    @OneToOne(cascade = CascadeType.ALL)
    private Alumno alumno;

    @ManyToMany
    @JoinTable( name = "servicios_inscripcion", joinColumns = @JoinColumn( name = "id_inscripcion", referencedColumnName = "id" ), inverseJoinColumns = @JoinColumn( name = "id_servicio", referencedColumnName = "id" ) )
    private List<Servicio> servicios;

    private Boolean activo;

    public Inscripcion()
    {
        super();
        this.activo = true;
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

}
