package ar.edu.uade.ia.escuela.presentacion.dto;

public class RegistroUsuarioDto
{
    private Long id;

    private String nombre;

    private String apellido;

    private String nombreUsuario;

    private String cuit;

    private Integer dni;

    private Integer cargo;

    private String cbu;

    public RegistroUsuarioDto()
    {

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

    public Integer getCargo()
    {
        return cargo;
    }

    public void setCargo( Integer cargo )
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

}