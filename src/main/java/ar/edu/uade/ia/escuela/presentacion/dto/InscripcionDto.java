package ar.edu.uade.ia.escuela.presentacion.dto;

import java.util.List;

public class InscripcionDto
{
    private Long id;

    private Long idTitular;

    private AlumnoDto alumno;

    private List<Long> idServicios;

    public InscripcionDto()
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

    public Long getIdTitular()
    {
        return idTitular;
    }

    public void setIdTitular( Long idTitular )
    {
        this.idTitular = idTitular;
    }

    public AlumnoDto getAlumno()
    {
        return alumno;
    }

    public void setAlumno( AlumnoDto alumno )
    {
        this.alumno = alumno;
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
