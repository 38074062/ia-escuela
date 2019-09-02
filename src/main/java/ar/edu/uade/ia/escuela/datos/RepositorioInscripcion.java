package ar.edu.uade.ia.escuela.datos;

import org.springframework.data.jpa.repository.Query;

import ar.edu.uade.ia.escuela.dominio.modelo.Inscripcion;


public interface RepositorioInscripcion
	extends RepositorioBase<Inscripcion, Long>
{
	@Query( "select e from #{#entityName} e where e.eliminado=false and e.nombre=?1" )
	Inscripcion findById(Integer id);
}
