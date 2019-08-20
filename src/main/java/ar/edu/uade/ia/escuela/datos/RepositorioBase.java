package ar.edu.uade.ia.escuela.datos;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import ar.edu.uade.ia.escuela.dominio.modelo.EntidadBase;

@NoRepositoryBean
public interface RepositorioBase<T extends EntidadBase, ID extends Serializable>
    extends JpaRepository<T, ID>
{

    @Override
    @Query( "update #{#entityName} e set e.eliminado=true where e.id=?1" )
    @Modifying
    public void deleteById( ID id );

    @Override
    @Query( "select e from #{#entityName} e where e.eliminado=false and e.id=?1" )
    public Optional<T> findById( ID arg0 );

    @Override
    @Query( "select e from #{#entityName} e where e.eliminado=false" )
    public List<T> findAll();

}
