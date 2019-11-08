package ar.edu.uade.ia.escuela.servicio;

import java.util.List;

import ar.edu.uade.ia.escuela.presentacion.dto.MetodoPagoDto;
import ar.edu.uade.ia.escuela.presentacion.dto.PagoDto;
import ar.edu.uade.ia.escuela.presentacion.dto.TitularDetalleDto;
import ar.edu.uade.ia.escuela.presentacion.dto.TitularDto;

public interface ServicioTitular
{
    public void altaTitular( TitularDto titularDto );

    public void bajaTitular( Long id );

    public void modificarTitular( TitularDto titularDto );

    public List<TitularDto> listarTitulares();

    public TitularDetalleDto getTitular( Long id );

    public void registrarPago( PagoDto pagoDto );

    public void modificarMetodoDePago( Long id, MetodoPagoDto metodoPago );
}
