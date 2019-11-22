package ar.edu.uade.ia.escuela;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;

import ar.edu.uade.ia.escuela.presentacion.dto.ReciboDto;
import ar.edu.uade.ia.escuela.presentacion.dto.RegistroUsuarioDto;
import ar.edu.uade.ia.escuela.servicio.ServicioUsuario;

@RunWith( SpringRunner.class )
@SpringBootTest
@DirtiesContext( classMode = ClassMode.AFTER_EACH_TEST_METHOD )
public class ServicioUsuarioTest
{

    @Autowired
    private ServicioUsuario servicio;

    @Test
    public void crearUsuario()
    {
        // Dado
        RegistroUsuarioDto usuarioDto = new RegistroUsuarioDto();
        usuarioDto.setNombre( "Juan" );
        usuarioDto.setApellido( "Perez" );
        usuarioDto.setCuit( "r989243" );
        usuarioDto.setDni( 12345678 );
        usuarioDto.setNombreUsuario( "jperez@uade.edu.ar" );
        usuarioDto.setCargo( 0 );
        // Cuando
        servicio.registrarUsuario( usuarioDto );
        // Entonces
        assertEquals( 1, servicio.getEmpleados().size() );
    }

    @Test
    public void crearUsuarioYAsociarCargaHoraria()
    {

        // Dado
        RegistroUsuarioDto usuarioDto = new RegistroUsuarioDto();
        usuarioDto.setNombre( "Juan" );
        usuarioDto.setApellido( "Perez" );
        usuarioDto.setCuit( "r989243" );
        usuarioDto.setDni( 12345678 );
        usuarioDto.setNombreUsuario( "jperez@uade.edu.ar" );
        usuarioDto.setCargo( 0 );
        usuarioDto.setCbu("1230000000124");
        servicio.registrarUsuario( usuarioDto );
        assertEquals( 1, servicio.getEmpleados().size() );
        ReciboDto reciboDto = new ReciboDto();
        reciboDto.setIdEmpleado( servicio.getEmpleados().get( 0 ).getId() );
        reciboDto.setHaber( "Basico" );
        reciboDto.setPrecio( 40000L );
        reciboDto.setDescuento( 0.20F );
        reciboDto.setHorario( "Lunes 16:00-18:00hs" );
        reciboDto.setHoras( 2 );
        // Cuando
        servicio.agregarCargaHoraria( reciboDto );
        // Entonces
        servicio.liquidarSueldos();
    }

}
