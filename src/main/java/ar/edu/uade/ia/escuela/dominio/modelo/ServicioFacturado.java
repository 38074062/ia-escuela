package ar.edu.uade.ia.escuela.dominio.modelo;

public class ServicioFacturado
{
    private Servicio servicio;
    
    private String nombre;
       
    private Float montoFacturado;

    public Servicio getServicio()
    {
        return servicio;
    }

    public void setServicio( Servicio servicio )
    {
        this.servicio = servicio;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre( String nombre )
    {
        this.nombre = nombre;
    }

    public Float getMontoFacturado()
    {
        return montoFacturado;
    }

    public void setMontoFacturado( Float montoFacturado )
    {
        this.montoFacturado = montoFacturado;
    }
    
    
    
}
