package ar.edu.uade.ia.escuela.datos;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import ar.edu.uade.ia.escuela.dominio.modelo.empleados.Usuario;

public interface RepositorioUsuario
    extends RepositorioBase<Usuario, Long>
{
    @Query( "select e from #{#entityName} e where e.eliminado=false and e.nombreUsuario=?1" )
    Usuario findByNombreUsuario(String nombreUsuario);

    @Query( "select e from #{#entityName} e where e.eliminado=false and e.dni=?1" )
    Usuario findByDni(Integer dni);

    @Query( "select e from #{#entityName} e where e.eliminado=false and e.cargo != ar.edu.uade.ia.escuela.dominio.modelo.empleados.Cargo.ADMINISTRADOR" )
    List<Usuario> findEmpleados();
}
