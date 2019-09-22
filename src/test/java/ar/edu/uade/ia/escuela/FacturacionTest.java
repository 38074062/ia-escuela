package ar.edu.uade.ia.escuela;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
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
import ar.edu.uade.ia.escuela.presentacion.dto.PagoDto;
import ar.edu.uade.ia.escuela.presentacion.dto.ServicioDto;
import ar.edu.uade.ia.escuela.presentacion.dto.TitularDto;
import ar.edu.uade.ia.escuela.servicio.ServicioFactura;
import ar.edu.uade.ia.escuela.servicio.ServicioInscripcion;
import ar.edu.uade.ia.escuela.servicio.ServicioServicio;
import ar.edu.uade.ia.escuela.servicio.ServicioTitular;

@RunWith( SpringRunner.class )
@SpringBootTest
@DirtiesContext( classMode = ClassMode.AFTER_EACH_TEST_METHOD )
public class FacturacionTest
{
    @Autowired
    private ServicioFactura factura;

    @Autowired
    private ServicioInscripcion inscripcion;

    @Autowired
    private ServicioServicio servicio;

    @Autowired
    private ServicioTitular titular;

    @Test
    public void facturar()
    {
        // Dado
        ServicioDto escolaridadDto = new ServicioDto();
        escolaridadDto.setNombre( "Media jornada" );
        escolaridadDto.setPrecio( 5000L );
        servicio.altaEscolaridad( escolaridadDto );

        ServicioDto comedorDto = new ServicioDto();
        comedorDto.setNombre( "Almuerzo" );
        comedorDto.setPrecio( 400L );
        servicio.altaComedor( comedorDto );

        TitularDto titularDto = new TitularDto();
        titularDto.setId( 01L );
        titularDto.setNombre( "Juan" );
        titularDto.setApellido( "Perez" );
        titularDto.setDireccion( "Lima 775" );
        titularDto.setDni( 12345678 );
        titularDto.setEmail( "jperez@uade.edu.ar" );
        titularDto.setCuentaBancaria( "3120941931203s" );
        titular.altaTitular( titularDto );

        List<ServicioDto> servicios = servicio.listarServicios();
        List<TitularDto> titulares = titular.listarTitulares();

        Long idT = Long.valueOf( titulares.get( 0 ).getDni() );
        ArrayList<Long> idS = new ArrayList<Long>();
        for ( ServicioDto s : servicios )
        {
            idS.add( s.getId() );
        }

        AlumnoDto alumno = new AlumnoDto();
        alumno.setNombre( "Pepe" );
        alumno.setApellido( "Gomez" );
        alumno.setDni( 186512345 );

        InscripcionDto inscripcionDto = new InscripcionDto();
        inscripcionDto.setAlumno( alumno );
        inscripcionDto.setIdTitular( idT );
        inscripcionDto.setIdServicios( idS );
        inscripcion.inscribirAlumno( inscripcionDto );

        alumno = new AlumnoDto();
        alumno.setNombre( "John" );
        alumno.setApellido( "Doe" );
        alumno.setDni( 186512045 );

        inscripcionDto = new InscripcionDto();
        inscripcionDto.setAlumno( alumno );
        inscripcionDto.setIdTitular( idT );
        inscripcionDto.setIdServicios( Arrays.asList( servicio.listarServicios().get( 1 ).getId() ) );
        inscripcion.inscribirAlumno( inscripcionDto );

        // Cuando
        factura.generarFactura();

        // Entonces
        assertEquals( 1, factura.listarFacturas().size() );

        PagoDto pago = new PagoDto();
        pago.setTitularId( titulares.get( 0 ).getId() );
        pago.setFacturaId( factura.listarFacturas().get( 0 ).getId() );
        pago.setFecha( LocalDate.now() );
        pago.setMonto( 800L );
        titular.registrarPago( pago );

        assertEquals( "Facturada",
                      titular.getTitular( titulares.get( 0 ).getId() ).getFacturas().get( 0 ).getEstado() );
    }
}
