package ar.edu.uade.ia.escuela.servicio.cliente.tarjeta;

import java.util.LinkedList;
import java.util.List;

public class PagoTarjetaException
    extends Exception
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private List<Integer> erroresTarjeta = new LinkedList<>();

    public PagoTarjetaException( String message )
    {
        super( message );
    }

    public PagoTarjetaException( String message, List<Integer> erroresTarjeta )
    {
        this( message );
        this.erroresTarjeta = erroresTarjeta;
    }

    public List<Integer> getErroresTarjeta()
    {
        return erroresTarjeta;
    }

}
