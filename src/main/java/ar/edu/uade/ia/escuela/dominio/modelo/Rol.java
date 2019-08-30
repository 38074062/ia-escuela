package ar.edu.uade.ia.escuela.dominio.modelo;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Rol extends EntidadBase
{
    private String nombre;

    @ManyToMany( mappedBy = "roles" )
    private List<Usuario> usuarios;

    @ManyToMany
    @JoinTable( name = "privilegios_roles", joinColumns = @JoinColumn( name = "id_rol", referencedColumnName = "id" ), inverseJoinColumns = @JoinColumn( name = "id_privilegio", referencedColumnName = "id" ) )
    private List<Privilegio> privilegios;

    public Rol()
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

    public List<Usuario> getUsuarios()
    {
        return usuarios;
    }

    public void setUsuarios( List<Usuario> usuarios )
    {
        this.usuarios = usuarios;
    }

    public List<Privilegio> getPrivilegios()
    {
        return privilegios;
    }

    public void setPrivilegios( List<Privilegio> privilegios )
    {
        this.privilegios = privilegios;
    }

}
