package ar.edu.uade.ia.escuela.servicio;

import java.util.List;

import ar.edu.uade.ia.escuela.presentacion.dto.InscripcionDetalleDto;
import ar.edu.uade.ia.escuela.presentacion.dto.InscripcionDto;

public interface ServicioInscripcion
{
    public void inscribirAlumno( InscripcionDto inscripcion );

    public List<InscripcionDetalleDto> listarInscripciones();

}
