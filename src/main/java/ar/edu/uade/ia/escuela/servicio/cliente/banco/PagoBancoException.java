package ar.edu.uade.ia.escuela.servicio.cliente.banco;

import java.util.LinkedList;
import java.util.List;

public class PagoBancoException
    extends Exception
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private List<String> erroresCbu = new LinkedList<>();

    public PagoBancoException( String arg0 )
    {
        super( arg0 );
    }

    public PagoBancoException( String error, List<String> erroresCbu )
    {
        this( error );
        this.erroresCbu = erroresCbu;
    }

    public List<String> getErroresCbu()
    {
        return erroresCbu;
    }

}
