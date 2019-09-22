package ar.edu.uade.ia.escuela.servicio.implementacion;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.uade.ia.escuela.datos.RepositorioServicio;
import ar.edu.uade.ia.escuela.dominio.modelo.inscripcion.Adicional;
import ar.edu.uade.ia.escuela.dominio.modelo.inscripcion.Comedor;
import ar.edu.uade.ia.escuela.dominio.modelo.inscripcion.Escolaridad;
import ar.edu.uade.ia.escuela.dominio.modelo.inscripcion.Servicio;
import ar.edu.uade.ia.escuela.presentacion.dto.ServicioDto;
import ar.edu.uade.ia.escuela.servicio.ServicioServicio;
import ar.edu.uade.ia.escuela.servicio.error.EntidadNoEncontradaException;
import ar.edu.uade.ia.escuela.servicio.error.NombreExistenteException;

@Service
@Transactional
public class ServicioServicioImpl
    implements ServicioServicio
{

    @Autowired
    private RepositorioServicio repositorioServicio;

    @Override
    public void bajaServicio( Long id )
    {
        if ( !repositorioServicio.findById( id ).isPresent() )
        {
            throw new EntidadNoEncontradaException( "El servicio no existe" );
        }
        repositorioServicio.deleteById( id );
    }

    @Override
    public void modificarServicio( ServicioDto servicioDto )
    {
        Optional<Servicio> servicio = repositorioServicio.findById( servicioDto.getId() );
        if ( !servicio.isPresent() )
        {
            throw new EntidadNoEncontradaException( "El servicio no existe" );
        }
        Servicio servicioGuardado = servicio.get();
        servicioGuardado.setNombre( servicioDto.getNombre() );
        servicioGuardado.setTipo( servicioDto.getTipo() );
        servicioGuardado.setPrecio( servicioDto.getPrecio() );
        repositorioServicio.save( servicioGuardado );

    }

    @Override
    public List<ServicioDto> listarServicios()
    {
        List<Servicio> servicios = repositorioServicio.findAll();
        List<ServicioDto> servicioDto = new ArrayList<ServicioDto>();
        for ( Servicio s : servicios )
        {
            ServicioDto sg = new ServicioDto();
            sg.setId( s.getId() );
            sg.setNombre( s.getNombre() );
            sg.setTipo( s.getTipo() );
            sg.setPrecio( s.getPrecio() );
            sg.setCategoria( getCategoria( s.getClass() ) );
            servicioDto.add( sg );
        }
        return servicioDto;
    }

    private String getCategoria( Class<? extends Servicio> adicional )
    {
        if ( adicional.equals( Escolaridad.class ) )
        {
            return "Escolaridad";
        }
        if ( adicional.equals( Comedor.class ) )
        {
            return "Comedor";
        }
        if ( adicional.equals( Adicional.class ) )
        {
            return "Adicional";
        }
        return "";
    }

    @Override
    public void altaEscolaridad( ServicioDto servicioDto )
    {
        Servicio escolaridad = new Escolaridad();
        this.altaServicio( escolaridad, servicioDto );
    }

    @Override
    public void altaComedor( ServicioDto servicioDto )
    {
        Comedor comedor = new Comedor();
        this.altaServicio( comedor, servicioDto );
    }

    @Override
    public void altaAdicional( ServicioDto servicioDto )
    {
        Servicio adicional = new Adicional();
        this.altaServicio( adicional, servicioDto );
    }

    private void altaServicio( Servicio servicio, ServicioDto servicioDto )
    {
        if ( repositorioServicio.findByNombre( servicioDto.getNombre() ) != null )
        {
            throw new NombreExistenteException();
        }
        servicio.setNombre( servicioDto.getNombre() );
        servicio.setTipo( servicioDto.getTipo() );
        servicio.setPrecio( servicioDto.getPrecio() );
        repositorioServicio.save( servicio );
    }
}
