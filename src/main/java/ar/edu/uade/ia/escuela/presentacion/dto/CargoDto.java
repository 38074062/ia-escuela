package ar.edu.uade.ia.escuela.presentacion.dto;

public class CargoDto
{
    private String nombre;

    private Integer codigo;

    public CargoDto()
    {
        super();
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre( String nombre )
    {
        this.nombre = nombre;
    }

    public Integer getCodigo()
    {
        return codigo;
    }

    public void setCodigo( Integer codigo )
    {
        this.codigo = codigo;
    }

}
