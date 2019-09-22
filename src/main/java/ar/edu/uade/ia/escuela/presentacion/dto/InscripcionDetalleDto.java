package ar.edu.uade.ia.escuela.presentacion.dto;

import java.util.List;

public class InscripcionDetalleDto
{
    private Long id;

    private AlumnoDto alumno;

    private List<ServicioDto> servicios;

    public InscripcionDetalleDto()
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

    public AlumnoDto getAlumno()
    {
        return alumno;
    }

    public void setAlumno( AlumnoDto alumno )
    {
        this.alumno = alumno;
    }

    public List<ServicioDto> getServicios()
    {
        return servicios;
    }

    public void setServicios( List<ServicioDto> servicios )
    {
        this.servicios = servicios;
    }

}
