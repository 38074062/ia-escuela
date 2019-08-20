package ar.edu.uade.ia.escuela.presentacion.dto;

public class RegistroUsuarioDto
{

    private String nombre;

    private String apellido;

    private String nombreUsuario;

    private String contrasenia;

    private String cuit;

    private Integer dni;

    private Integer cargo;

    public RegistroUsuarioDto()
    {

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

    public Integer getCargo()
    {
        return cargo;
    }

    public void setCargo( Integer cargo )
    {
        this.cargo = cargo;
    }

}