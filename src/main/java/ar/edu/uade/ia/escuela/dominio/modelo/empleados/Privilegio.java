package ar.edu.uade.ia.escuela.dominio.modelo.empleados;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import ar.edu.uade.ia.escuela.dominio.modelo.EntidadBase;

@Entity
public class Privilegio extends EntidadBase
{
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private Long id;

    private String nombre;

    @ManyToMany( mappedBy = "privilegios" )
    private List<Rol> roles;

    public Privilegio()
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

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre( String nombre )
    {
        this.nombre = nombre;
    }

    public List<Rol> getRoles()
    {
        return roles;
    }

    public void setRoles( List<Rol> roles )
    {
        this.roles = roles;
    }

}
