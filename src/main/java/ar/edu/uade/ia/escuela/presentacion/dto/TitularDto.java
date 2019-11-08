package ar.edu.uade.ia.escuela.presentacion.dto;

public class TitularDto
{
    private Long id;

    private Integer dni;

    private String nombre;

    private String apellido;

    private String direccion;

    private String email;

    private MetodoPagoDto metodoPago;

    public Integer getDni()
    {
        return dni;
    }

    public void setDni( Integer dni )
    {
        this.dni = dni;
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

    public String getDireccion()
    {
        return direccion;
    }

    public void setDireccion( String direccion )
    {
        this.direccion = direccion;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail( String email )
    {
        this.email = email;
    }

    public Long getId()
    {
        return id;
    }

    public void setId( Long id )
    {
        this.id = id;
    }

    public MetodoPagoDto getMetodoPago()
    {
        return metodoPago;
    }

    public void setMetodoPago( MetodoPagoDto metodoPago )
    {
        this.metodoPago = metodoPago;
    }

}
