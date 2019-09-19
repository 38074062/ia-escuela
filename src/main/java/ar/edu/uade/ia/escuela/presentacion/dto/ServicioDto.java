package ar.edu.uade.ia.escuela.presentacion.dto;

public abstract class ServicioDto
{
    private Long id;

    private String nombre;

    private String tipo;

    private float precio;

    private String categoria;

    public ServicioDto()
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

    public void setNombre( String nom )
    {
        nombre = nom;
    }

    public String getTipo()
    {
        return tipo;
    }

    public void setTipo( String tipo )
    {
        this.tipo = tipo;
    }

    public float getPrecio()
    {
        return precio;
    }

    public void setPrecio( float precio )
    {
        this.precio = precio;
    }

    public String getCategoria()
    {
        return categoria;
    }

    public void setCategoria( String categoria )
    {
        this.categoria = categoria;
    }

}
