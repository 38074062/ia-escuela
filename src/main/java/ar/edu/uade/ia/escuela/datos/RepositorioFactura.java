package ar.edu.uade.ia.escuela.datos;

import org.springframework.data.jpa.repository.Query;

import ar.edu.uade.ia.escuela.dominio.modelo.Factura;
import ar.edu.uade.ia.escuela.dominio.modelo.Titular;

public interface RepositorioFactura 
	extends RepositorioBase<Factura, Long>
{
	@Query( "select e from #{#entityName} e where e.eliminado=false and e.nombre=?1" )
	Factura findById(Integer id);
}
