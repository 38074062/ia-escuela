package ar.edu.uade.ia.escuela.datos;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ar.edu.uade.ia.escuela.dominio.modelo.Privilegio;

@Repository
public interface RepositorioPrivilegio
    extends RepositorioBase<Privilegio, Long>
{
    @Query( "select e from #{#entityName} e where e.eliminado=false and e.nombre=?1" )
    Optional<Privilegio> findByPrivilegio( String privilegio );
}
