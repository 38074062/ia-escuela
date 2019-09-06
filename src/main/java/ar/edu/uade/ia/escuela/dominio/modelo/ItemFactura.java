package ar.edu.uade.ia.escuela.dominio.modelo;

import java.util.List;

import javax.persistence.Entity;

@Entity
public class ItemFactura
    extends EntidadBase
{
    private Inscripcion inscripcion;
    
    private List<ServicioFacturado> serviciosFacturados;

    public Inscripcion getInscripcion()
    {
        return inscripcion;
    }

    public void setInscripcion( Inscripcion inscripcion )
    {
        this.inscripcion = inscripcion;
    }
    
    

    public List<ServicioFacturado> getServiciosFacturados()
    {
        return serviciosFacturados;
    }

    public void setServiciosFacturados( List<ServicioFacturado> serviciosFacturados )
    {
        this.serviciosFacturados = serviciosFacturados;
    }

    public float getSubTotal()
    {
        return serviciosFacturados.stream().map(ServicioFacturado::getMontoFacturado).reduce(Float::sum).orElse(0F);
    }    
    
}
