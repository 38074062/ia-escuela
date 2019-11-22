package ar.edu.uade.ia.escuela;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;

import ar.edu.uade.ia.escuela.presentacion.dto.AlumnoDto;
import ar.edu.uade.ia.escuela.presentacion.dto.InscripcionDto;
import ar.edu.uade.ia.escuela.presentacion.dto.ServicioDto;
import ar.edu.uade.ia.escuela.presentacion.dto.TitularDto;
import ar.edu.uade.ia.escuela.servicio.ServicioInscripcion;
import ar.edu.uade.ia.escuela.servicio.ServicioServicio;
import ar.edu.uade.ia.escuela.servicio.ServicioTitular;

@RunWith( SpringRunner.class )
@SpringBootTest
@DirtiesContext( classMode = ClassMode.AFTER_EACH_TEST_METHOD )
public class ServicioInscripcionTest 
{
	@Autowired
	private ServicioInscripcion inscripcion;
	
	@Autowired
	private ServicioServicio servicio;
	
	@Autowired
	private ServicioTitular titular;
	
	@Test
	public void inscribirAlumno()
	{
		//Dado
		ServicioDto escolaridadDto = new ServicioDto();
		escolaridadDto.setId(01L);
        escolaridadDto.setNombre( "Media jornada" );
        escolaridadDto.setPrecio( 5000L );
        servicio.altaEscolaridad( escolaridadDto );
        
        TitularDto titularDto = new TitularDto();
        titularDto.setId(01L);
        titularDto.setNombre("Juan");
        titularDto.setApellido("Perez");
        titularDto.setDireccion("Lima 775");
        titularDto.setDni(12345678);
        titularDto.setEmail("jperez@uade.edu.ar");
        titular.altaTitular( titularDto );
        
        List<ServicioDto> servicios = servicio.listarServicios();
        List<TitularDto> titulares = titular.listarTitulares();
        
        Long idT = titulares.get(0).getId();
        ArrayList<Long> idS = new ArrayList<Long>();
        for(ServicioDto s:servicios)
        {
        	idS.add(s.getId());
        }
               
        
        AlumnoDto alumno = new AlumnoDto();
        alumno.setNombre("Pepe");
        alumno.setApellido("Gomez");
        alumno.setDni(186512345);
        
        InscripcionDto inscripcionDto = new InscripcionDto();
        inscripcionDto.setAlumno(alumno);
        inscripcionDto.setIdTitular(idT);
        inscripcionDto.setIdServicios(idS);
        
        //Cuando
		inscripcion.inscribirAlumno(inscripcionDto);
		
		//Entonces
		assertEquals(1, inscripcion.listarInscripciones().size());
	}

}
