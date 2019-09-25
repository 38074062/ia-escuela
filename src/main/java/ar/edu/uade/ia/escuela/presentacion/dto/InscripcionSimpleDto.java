package ar.edu.uade.ia.escuela.presentacion.dto;

import java.util.List;

public class InscripcionSimpleDto
{
    private Long id;

    private List<Long> idServicios;

    public InscripcionSimpleDto()
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

    public List<Long> getIdServicios()
    {
        return idServicios;
    }

    public void setIdServicios( List<Long> idServicios )
    {
        this.idServicios = idServicios;
    }

}
