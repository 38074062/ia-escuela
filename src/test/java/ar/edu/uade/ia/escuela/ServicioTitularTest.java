package ar.edu.uade.ia.escuela;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;

import ar.edu.uade.ia.escuela.presentacion.dto.TitularDto;
import ar.edu.uade.ia.escuela.servicio.ServicioTitular;

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
        TitularDto titularDto2 = new TitularDto();
        titularDto2.setNombre("Bruno");
        titularDto2.setApellido("Diaz");
        titularDto2.setDireccion("Mitre 1043");
        titularDto2.setDni(165854688);
        titularDto2.setEmail("batman@uade.edu.ar");
        
        // Cuando
        servicio.altaTitular( titularDto );
        servicio.altaTitular( titularDto2 );
        // Entonces
        assertEquals( 2, servicio.listarTitulares().size() );
    }

   
}
