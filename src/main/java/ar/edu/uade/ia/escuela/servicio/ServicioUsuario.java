package ar.edu.uade.ia.escuela.servicio;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import ar.edu.uade.ia.escuela.presentacion.dto.CargoDto;
import ar.edu.uade.ia.escuela.presentacion.dto.EmpleadoDto;
import ar.edu.uade.ia.escuela.presentacion.dto.ReciboDto;
import ar.edu.uade.ia.escuela.presentacion.dto.RegistroUsuarioDto;

public interface ServicioUsuario
    extends UserDetailsService
{
    public void registrarUsuario( RegistroUsuarioDto usuarioDto );

    public List<CargoDto> getCargos();

    public List<EmpleadoDto> getEmpleados();

    public void agregarCargaHoraria( ReciboDto recibo );

    public void modificarUsuario( RegistroUsuarioDto usuarioDto );

    public void eliminarUsuario( Long id );
    
    public void calcularSueldos();
}