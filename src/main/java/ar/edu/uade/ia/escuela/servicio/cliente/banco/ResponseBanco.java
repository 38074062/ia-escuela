package ar.edu.uade.ia.escuela.servicio.cliente.banco;

public class ResponseBanco
{
    private String resultado;

    private String error;

    public ResponseBanco()
    {
        super();
    }

    public String getResultado()
    {
        return resultado;
    }

    public void setResultado( String resultado )
    {
        this.resultado = resultado;
    }

    public String getError()
    {
        return error;
    }

    public void setError( String error )
    {
        this.error = error;
    }

    public boolean huboError()
    {
        return !error.isEmpty();
    }

}
