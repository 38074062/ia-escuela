package ar.edu.uade.ia.escuela;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;

import ar.edu.uade.ia.escuela.presentacion.dto.EscolaridadDto;
import ar.edu.uade.ia.escuela.servicio.ServicioServicio;
import ar.edu.uade.ia.escuela.servicio.error.NombreExistenteException;
import ar.edu.uade.ia.escuela.servicio.error.EntidadNoEncontradaException;

@RunWith( SpringRunner.class )
@SpringBootTest
@DirtiesContext( classMode = ClassMode.AFTER_EACH_TEST_METHOD )
public class ServicioServicioTest
{

    @Autowired
    private ServicioServicio servicio;

    @Test
    public void crearServicioEscolaridad()
    {
        // Dado
        EscolaridadDto escolaridadDto = new EscolaridadDto();
        escolaridadDto.setNombre( "Media jornada" );
        escolaridadDto.setPrecio( 5000L );
        // Cuando
        servicio.altaEscolaridad( escolaridadDto );
        // Entonces
        assertEquals( 1, servicio.listarServicios().size() );
    }

    @Test( expected = NombreExistenteException.class )
    public void crearServicioEscolaridadNombreDuplicado()
    {
        // Dado
        EscolaridadDto escolaridadDto = new EscolaridadDto();
        escolaridadDto.setNombre( "Media jornada" );
        escolaridadDto.setPrecio( 5000L );
        // Cuando
        servicio.altaEscolaridad( escolaridadDto );
        servicio.altaEscolaridad( escolaridadDto );
        // Entonces falla
    }

    @Test
    public void borrarEscolaridad()
    {
        // Dado
        EscolaridadDto escolaridadDto = new EscolaridadDto();
        escolaridadDto.setNombre( "Media jornada" );
        escolaridadDto.setPrecio( 5000L );
        servicio.altaEscolaridad( escolaridadDto );
        assertEquals( 1, servicio.listarServicios().size() );
        Long id = servicio.listarServicios().get( 0 ).getId();
        // Cuando
        servicio.bajaServicio( id );
        // Entonces
        assertTrue( servicio.listarServicios().isEmpty() );
    }

    @Test( expected = EntidadNoEncontradaException.class )
    public void borrarEscolaridadInexistente()
    {
        // Dado
        EscolaridadDto escolaridadDto = new EscolaridadDto();
        escolaridadDto.setNombre( "Media jornada" );
        escolaridadDto.setPrecio( 5000L );
        servicio.altaEscolaridad( escolaridadDto );
        assertEquals( 1, servicio.listarServicios().size() );
        Long id = 99999L;
        // Cuando
        servicio.bajaServicio( id );
        // Entonces falla
    }

    @Test
    public void testFindAllStations()
    {
        // Dado
        EscolaridadDto escolaridadDto1 = new EscolaridadDto();
        escolaridadDto1.setNombre( "Media jornada" );
        escolaridadDto1.setPrecio( 5000L );
        EscolaridadDto escolaridadDto2 = new EscolaridadDto();
        escolaridadDto2.setNombre( "Jornada completa" );
        escolaridadDto2.setPrecio( 5000L );
        // Cuando
        servicio.altaEscolaridad( escolaridadDto1 );
        servicio.altaEscolaridad( escolaridadDto2 );
        // Entonces
        assertEquals( 2, servicio.listarServicios().size() );
    }

}
