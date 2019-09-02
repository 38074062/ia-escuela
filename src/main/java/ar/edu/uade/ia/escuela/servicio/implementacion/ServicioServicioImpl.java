package ar.edu.uade.ia.escuela.servicio.implementacion;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.uade.ia.escuela.datos.RepositorioServicio;
import ar.edu.uade.ia.escuela.dominio.modelo.Escolaridad;
import ar.edu.uade.ia.escuela.dominio.modelo.Servicio;
import ar.edu.uade.ia.escuela.presentacion.dto.ServicioDto;
import ar.edu.uade.ia.escuela.servicio.ServicioServicio;
import ar.edu.uade.ia.escuela.servicio.error.EntidadNoEncontrada;
import ar.edu.uade.ia.escuela.servicio.error.NombreExistenteException;

@Service
@Transactional
public class ServicioServicioImpl
    implements ServicioServicio
{

    @Autowired
    private RepositorioServicio repositorioServicio;

    @Override
    public void altaServicio( ServicioDto servicioDto )
    {
        if ( repositorioServicio.findByNombre( servicioDto.getNombre() ) != null )
        {
            throw new NombreExistenteException();
        }
        Servicio servicio = new Escolaridad(); //aca falta poder elegir cual servicio primero
        servicio.setNombre( servicioDto.getNombre() );
        servicio.setTipo( servicioDto.getTipo() );
        servicio.setPrecio( servicioDto.getPrecio() );
        repositorioServicio.save( servicio );
    }

	@Override
	public void bajaServicio(Long id) {
		if (!repositorioServicio.findById(id).isPresent())
		{
			throw new EntidadNoEncontrada("El servicio no existe");
		}
		repositorioServicio.deleteById(id);
	}

	@Override
	public void modificarServicio(ServicioDto servicioDto) {
	    Optional<Servicio> servicio = repositorioServicio.findById( servicioDto.getId());
		if ( !servicio.isPresent())
        {
            throw new EntidadNoEncontrada("El servicio no existe");
        }
        Servicio servicioGuardado = servicio.get();
        servicioGuardado.setNombre( servicioDto.getNombre() );
        servicioGuardado.setTipo( servicioDto.getTipo() );
        servicioGuardado.setPrecio( servicioDto.getPrecio() );
        repositorioServicio.save( servicioGuardado );
		
	}

	@Override
	public List<ServicioDto> listarServicios() {
		List<Servicio> servicios = 	repositorioServicio.findAll();
		List<ServicioDto> servicioDto = new ArrayList<ServicioDto>();
		for(Servicio s:servicios)
		{
			ServicioDto sg= new ServicioDto();
			sg.setId(s.getId());
		    sg.setNombre( s.getNombre() );
		    sg.setTipo( s.getTipo() );
		    sg.setPrecio( s.getPrecio() );
		    servicioDto.add(sg);
		}
		return servicioDto;
	}
    
    
}