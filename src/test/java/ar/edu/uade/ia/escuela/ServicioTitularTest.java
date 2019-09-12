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
import ar.edu.uade.ia.escuela.presentacion.dto.TitularDto;
import ar.edu.uade.ia.escuela.servicio.ServicioServicio;
import ar.edu.uade.ia.escuela.servicio.ServicioTitular;
import ar.edu.uade.ia.escuela.servicio.error.NombreExistenteException;
import ar.edu.uade.ia.escuela.servicio.error.EntidadNoEncontradaException;

@RunWith( SpringRunner.class )
@SpringBootTest
@DirtiesContext( classMode = ClassMode.AFTER_EACH_TEST_METHOD )
public class ServicioTitularTest
{

    @Autowired
    private ServicioTitular servicio;

    @Test
    public void crearTitular()
    {
        // Dado
        TitularDto titularDto = new TitularDto();
        titularDto.setNombre("Juan");
        titularDto.setApellido("Perez");
        titularDto.setDireccion("Lima 775");
        titularDto.setDni(12345678);
        titularDto.setEmail("jperez@uade.edu.ar");
        titularDto.setCuentaBancaria("3120941931203");
        // Cuando
        servicio.altaTitular( titularDto );
        // Entonces
        assertEquals( 1, servicio.listarTitulares().size() );
    }

   
}
