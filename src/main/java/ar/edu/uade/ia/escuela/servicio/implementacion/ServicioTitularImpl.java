package ar.edu.uade.ia.escuela.servicio.implementacion;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.uade.ia.escuela.datos.RepositorioTitular;
import ar.edu.uade.ia.escuela.dominio.modelo.facturacion.CuentaCorriente;
import ar.edu.uade.ia.escuela.dominio.modelo.inscripcion.Titular;
import ar.edu.uade.ia.escuela.presentacion.dto.TitularDto;
import ar.edu.uade.ia.escuela.servicio.ServicioTitular;
import ar.edu.uade.ia.escuela.servicio.error.DniExistenteException;
import ar.edu.uade.ia.escuela.servicio.error.EntidadNoEncontradaException;
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
    	Optional<Titular> optTitular = repositorioTitular.findByDni( titularDto.getDni() );
        if ( optTitular.isPresent() )
        {
            throw new DniExistenteException();
        }
        Titular titular = optTitular.orElse(new Titular());
        titular.setNombre( titularDto.getNombre() );
        titular.setApellido( titularDto.getApellido() );
        titular.setDni( titularDto.getDni() );
        titular.setDireccion( titularDto.getDireccion() );
        titular.setEmail( titularDto.getEmail() );
        CuentaCorriente cuenta = new CuentaCorriente();
        cuenta.setCuentaBancaria( titularDto.getCuentaBancaria() );
        titular.setCuentaCorriente( cuenta );
        repositorioTitular.save( titular );
    }

    @Override
    public void bajaTitular( Long id )
    {
        if ( !repositorioTitular.findById( id ).isPresent() )
        {
            throw new EntidadNoEncontradaException( "El titular no existe" );
        }
        repositorioTitular.deleteById( id );
    }

    @Override
    public void modificarTitular( TitularDto titularDto )
    {
        if ( repositorioTitular.findByDni( titularDto.getDni() ) != null )
        {
            throw new EntidadNoEncontradaException( "El titular no existe" );
        }
        Titular titularGuardado = new Titular();
        titularGuardado.setNombre( titularDto.getNombre() );
        titularGuardado.setApellido( titularDto.getApellido() );
        titularGuardado.setDni( titularDto.getDni() );
        titularGuardado.setDireccion( titularDto.getDireccion() );
        titularGuardado.setEmail( titularDto.getEmail() );
        if ( titularGuardado.getCuentaCorriente() != null )
        {
            titularGuardado.getCuentaCorriente().setCuentaBancaria( titularDto.getCuentaBancaria() );
        }
        repositorioTitular.save( titularGuardado );
    }

    @Override
    public List<TitularDto> listarTitulares()
    {
        List<Titular> titulares = repositorioTitular.findAll();
        List<TitularDto> titularDto = new ArrayList<TitularDto>();
        for ( Titular s : titulares )
        {
            TitularDto sg = new TitularDto();
            sg.setId(s.getId());
            sg.setDni( s.getDni() );
            sg.setNombre( s.getNombre() );
            sg.setApellido( s.getApellido() );
            sg.setDireccion( s.getDireccion() );
            sg.setEmail( s.getEmail() );
            sg.setCuentaBancaria( s.getCuentaCorriente().getCuentaBancaria() );
            titularDto.add( sg );
        }
        return titularDto;
    }
}
