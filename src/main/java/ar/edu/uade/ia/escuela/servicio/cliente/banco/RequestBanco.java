package ar.edu.uade.ia.escuela.servicio.cliente.banco;

public class RequestBanco
{
    private String cbuOrigen;

    private String cbuDestino;

    private float monto;

    private String descripcion;

    public RequestBanco()
    {
        super();
    }

    public String getCbuOrigen()
    {
        return cbuOrigen;
    }

    public void setCbuOrigen( String cbuOrigen )
    {
        this.cbuOrigen = cbuOrigen;
    }

    public String getCbuDestino()
    {
        return cbuDestino;
    }

    public void setCbuDestino( String cbuDestino )
    {
        this.cbuDestino = cbuDestino;
    }

    public float getMonto()
    {
        return monto;
    }

    public void setMonto( float monto )
    {
        this.monto = monto;
    }

    public String getDescripcion()
    {
        return descripcion;
    }

    public void setDescripcion( String descripcion )
    {
        this.descripcion = descripcion;
    }

}
