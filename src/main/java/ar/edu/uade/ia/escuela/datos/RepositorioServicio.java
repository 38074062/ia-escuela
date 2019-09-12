package ar.edu.uade.ia.escuela.datos;

import org.springframework.data.jpa.repository.Query;

import ar.edu.uade.ia.escuela.dominio.modelo.inscripcion.Servicio;

public interface RepositorioServicio
    extends RepositorioBase<Servicio, Long>
{
    @Query( "select e from #{#entityName} e where e.eliminado=false and e.nombre=?1" )
    Servicio findByNombre(String nombre);
}


