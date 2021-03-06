package ar.edu.uade.ia.escuela.datos;

import org.springframework.data.jpa.repository.Query;

import ar.edu.uade.ia.escuela.dominio.modelo.inscripcion.Alumno;

public interface RepositorioAlumno
    extends RepositorioBase<Alumno, Long>
{
    @Query( "select e from #{#entityName} e where e.eliminado=false and e.legajo=?1" )
    Alumno findByLegajo( Integer legajo );

    @Query( "select e from #{#entityName} e where e.eliminado=false and e.dni=?1" )
    Alumno findByDni( Integer dni );
}
