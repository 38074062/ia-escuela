package ar.edu.uade.ia.escuela.datos;

import org.springframework.data.jpa.repository.Query;

import ar.edu.uade.ia.escuela.dominio.modelo.Alumno;

public interface RepositorioAlumno
	extends RepositorioBase<Alumno, Long>
{
	@Query( "select e from #{#entityName} e where e.eliminado=false and e.legajo=?1" )
	Alumno findByLegajo(Integer legajo);
}

