package ar.edu.uade.ia.escuela.servicio;

import java.util.List;

import org.springframework.stereotype.Service;

import ar.edu.uade.ia.escuela.presentacion.dto.FacturaDto;

@Service
public interface ServicioFactura 
{
	public void generarFactura();
	public List<FacturaDto> listarFacturas();

}
