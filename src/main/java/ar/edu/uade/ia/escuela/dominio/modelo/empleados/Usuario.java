package ar.edu.uade.ia.escuela.dominio.modelo.empleados;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import ar.edu.uade.ia.escuela.dominio.modelo.EntidadBase;

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

    @OneToMany( mappedBy = "usuario", cascade = CascadeType.ALL )
    private List<Recibo> recibos;

    @ManyToMany
    @JoinTable( name = "roles_usuarios", joinColumns = @JoinColumn( name = "id_usuario", referencedColumnName = "id" ), inverseJoinColumns = @JoinColumn( name = "id_rol", referencedColumnName = "id" ) )
    private List<Rol> roles;

    @Enumerated( EnumType.STRING )
    private Cargo cargo;

    private String cbu;

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

    public void setRecibos( List<Recibo> recibos )
    {
        if ( recibos != null )
        {
            recibos.forEach( this::agregarRecibo );
        }
    }

    public void agregarRecibo( Recibo recibo )
    {
        if ( this.recibos == null )
        {
            this.recibos = new LinkedList<>();
        }
        recibo.setUsuario( this );
        this.recibos.add( recibo );
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

    public String getCbu()
    {
        return cbu;
    }

    public void setCbu( String cbu )
    {
        this.cbu = cbu;
    }

    public float calcularSueldo()
    {
        return (float) recibos.stream().mapToDouble( Recibo::calcularTotal ).sum();
    }
}
