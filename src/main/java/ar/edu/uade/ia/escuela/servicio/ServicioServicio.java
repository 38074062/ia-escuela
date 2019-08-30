package ar.edu.uade.ia.escuela.servicio;

import java.util.List;

import ar.edu.uade.ia.escuela.presentacion.dto.ServicioDto;

public interface ServicioServicio
{
    public void altaServicio( ServicioDto servicioDto );
    public void bajaServicio(Long id);
    public void modificarServicio(ServicioDto servicioDto);
    public List<ServicioDto> listarServicios();
}