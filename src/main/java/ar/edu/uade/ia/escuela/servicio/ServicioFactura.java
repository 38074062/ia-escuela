package ar.edu.uade.ia.escuela.servicio;

import java.util.List;

import ar.edu.uade.ia.escuela.presentacion.dto.FacturaDto;

public interface ServicioFactura 
{
	public void generarFactura(FacturaDto facturaDto);
	public List<FacturaDto> listarFacturas();
}
