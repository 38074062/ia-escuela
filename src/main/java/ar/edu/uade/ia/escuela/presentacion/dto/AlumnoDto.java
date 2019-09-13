package ar.edu.uade.ia.escuela.presentacion.dto;

public class AlumnoDto
{
    private Long id;

    private String nombre;

    private String apellido;

    private Integer dni;

    public AlumnoDto()
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

    public String getApellido()
    {
        return apellido;
    }

    public void setApellido( String apellido )
    {
        this.apellido = apellido;
    }

    public Integer getDni()
    {
        return dni;
    }

    public void setDni( Integer dni )
    {
        this.dni = dni;
    }

}
