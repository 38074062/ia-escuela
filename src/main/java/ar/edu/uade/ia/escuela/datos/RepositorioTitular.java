package ar.edu.uade.ia.escuela.datos;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;

import ar.edu.uade.ia.escuela.dominio.modelo.inscripcion.Titular;

public interface RepositorioTitular
	extends RepositorioBase<Titular, Long>
{
	@Query( "select e from #{#entityName} e where e.eliminado=false and e.dni=?1" )
	Optional<Titular> findByDni(Integer Dni);
}

