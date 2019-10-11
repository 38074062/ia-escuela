package ar.edu.uade.ia.escuela.servicio.cliente;

public class RespuestaBanco
{
    private String resultado;

    private String error;

    public RespuestaBanco()
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

    public Boolean huboError()
    {
        return this.error != null && !this.error.isEmpty();
    }

}
