package ar.edu.uade.ia.escuela.servicio.implementacion;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.uade.ia.escuela.datos.RepositorioTitular;
import ar.edu.uade.ia.escuela.dominio.modelo.Titular;
import ar.edu.uade.ia.escuela.presentacion.dto.TitularDto;
import ar.edu.uade.ia.escuela.servicio.ServicioTitular;
import ar.edu.uade.ia.escuela.servicio.error.EntidadNoEncontrada;
import ar.edu.uade.ia.escuela.servicio.error.NombreExistenteException;

@Service
@Transactional
public class ServicioTitularImpl
    implements ServicioTitular
{

    @Autowired
    private RepositorioTitular repositorioTitular;

    @Override
    public void altaTitular( TitularDto titularDto )
    {
        if ( repositorioTitular.findByDni( titularDto.getDni() ) != null )
        {
            throw new NombreExistenteException();
        }
        Titular titular = new Titular();
        titular.setNombre( titularDto.getNombre() );
        titular.setApellido(titularDto.getApellido());
        titular.setDni(titularDto.getDni());
        titular.setDireccion(titularDto.getDireccion());
        titular.setEmail(titularDto.getEmail());
        titular.setCuentaBancaria(titularDto.getCuentaBancaria());
        repositorioTitular.save( titular );
    }

	@Override
	public void bajaTitular(Long id) {
		if (!repositorioTitular.findById(id).isPresent())
		{
			throw new EntidadNoEncontrada("El titular no existe");
		}
		repositorioTitular.deleteById(id);
	}

	@Override
	public void modificarTitular(TitularDto titularDto) {
	    Optional<Titular> titular = repositorioTitular.findByDni( titularDto.getDni());
		if ( !titular.isPresent())
        {
            throw new EntidadNoEncontrada("El titular no existe");
        }
		Titular titularGuardado = titular.get();
		titularGuardado.setNombre( titularDto.getNombre() );
        titularGuardado.setApellido(titularDto.getApellido());
        titularGuardado.setDni(titularDto.getDni());
        titularGuardado.setDireccion(titularDto.getDireccion());
        titularGuardado.setEmail(titularDto.getEmail());
        titularGuardado.setCuentaBancaria(titularDto.getCuentaBancaria());
        repositorioTitular.save(titularGuardado);
	}

	@Override
	public List<TitularDto> listarTitulares() {
		List<Titular> titulares = 	repositorioTitular.findAll();
		List<TitularDto> titularDto = new ArrayList<TitularDto>();
		for(Titular s:titulares)
		{
			TitularDto sg= new TitularDto();
			sg.setDni(s.getDni());
		    sg.setNombre( s.getNombre() );
		    sg.setApellido( s.getApellido() );
		    sg.setDireccion(s.getDireccion());
		    sg.setEmail(s.getEmail());
		    sg.setCuentaBancaria(s.getCuentaBancaria());
		    titularDto.add(sg);
		}
		return titularDto;
	}
}
