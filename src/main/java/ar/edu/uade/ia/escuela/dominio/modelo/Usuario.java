package ar.edu.uade.ia.escuela.dominio.modelo;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import ar.edu.uade.ia.escuela.presentacion.dto.ReciboDto;

import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;

@Entity
public class Usuario
    extends EntidadBase
{
    private String nombre;

    private String apellido;

    private String nombreUsuario;

    private String contrasenia;

    private String cuit;

    private Integer dni;

    @OneToMany( mappedBy = "usuario" )
    private List<Recibo> recibos;

    @ManyToMany
    @JoinTable( name = "roles_usuarios", joinColumns = @JoinColumn( name = "id_usuario", referencedColumnName = "id" ), inverseJoinColumns = @JoinColumn( name = "id_rol", referencedColumnName = "id" ) )
    private List<Rol> roles;

    @Enumerated( EnumType.STRING )
    private Cargo cargo;

    public Usuario()
    {
        super();
    }

    public String getNombre()
    {
        return nombre;
    }

    public List<Recibo> getRecibos()
    {
        return recibos;
    }

    public void setRecibos( List<ReciboDto> list )
    {
        for ( ReciboDto r : list )
        {
            Recibo reciboActual = new Recibo();
            reciboActual.setHaber( r.getHaber() );
            reciboActual.setHorario( r.getHorario() );
            reciboActual.setHoras( r.getHoras() );
            reciboActual.setPrecio( r.getPrecio() );
            reciboActual.setDescuento( r.getDescuento() );
            recibos.add( reciboActual );
        }
    }

    public void setNombre( String nombre )
    {
        this.nombre = nombre;
    }

    public String getApellido()
    {
        return apellido;
    }

    public void setApellido( String apellido )
    {
        this.apellido = apellido;
    }

    public String getNombreUsuario()
    {
        return nombreUsuario;
    }

    public void setNombreUsuario( String nombreUsuario )
    {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasenia()
    {
        return contrasenia;
    }

    public void setContrasenia( String contrasenia )
    {
        this.contrasenia = contrasenia;
    }

    public String getCuit()
    {
        return cuit;
    }

    public void setCuit( String cuit )
    {
        this.cuit = cuit;
    }

    public Integer getDni()
    {
        return dni;
    }

    public void setDni( Integer dni )
    {
        this.dni = dni;
    }

    public List<Rol> getRoles()
    {
        return roles;
    }

    public void setRoles( List<Rol> roles )
    {
        this.roles = roles;
    }

    public Cargo getCargo()
    {
        return cargo;
    }

    public void setCargo( Cargo cargo )
    {
        this.cargo = cargo;
    }

}
