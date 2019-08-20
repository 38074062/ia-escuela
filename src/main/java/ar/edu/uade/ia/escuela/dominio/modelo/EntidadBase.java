package ar.edu.uade.ia.escuela.dominio.modelo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Where;

@MappedSuperclass
@Where( clause = "deleted=0" )
public abstract class EntidadBase
{
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    protected Long id;

    protected Boolean eliminado;

    public EntidadBase()
    {
        super();
        this.eliminado = false;
    }

    public Long getId()
    {
        return id;
    }

    public void setId( Long id )
    {
        this.id = id;
    }

    public Boolean getEliminado()
    {
        return eliminado;
    }

    public void setEliminado( Boolean eliminado )
    {
        this.eliminado = eliminado;
    }
    
    

}
