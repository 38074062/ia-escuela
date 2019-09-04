package ar.edu.uade.ia.escuela.servicio.implementacion;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.edu.uade.ia.escuela.datos.RepositorioFactura;
import ar.edu.uade.ia.escuela.dominio.modelo.Factura;
import ar.edu.uade.ia.escuela.presentacion.dto.FacturaDto;
import ar.edu.uade.ia.escuela.servicio.ServicioFactura;

@Service
@Transactional
public class ServicioFacturaImpl 
	implements ServicioFactura
{

	@Override
	public void generarFactura(FacturaDto facturaDto) {
		
		
	}

	@Override
	public List<FacturaDto> listarFacturas() {
		List<Factura> facturas = 	RepositorioFactura.findAll();
		List<FacturaDto> facturaDto = new ArrayList<FacturaDto>();
		for(Factura s:facturas)
		{
			FacturaDto sg= new FacturaDto();
			sg.setId(s.getId());
		    sg.setTotal( s.getTotal() );
		    sg.setTipo( s.getTipo() );
		    sg.setFecha( s.getFecha() );
		    sg.setVencimiento( s.getVencimiento() );
		    facturaDto.add(sg);
		}
		return facturaDto;
	}

}