package ar.edu.uade.ia.escuela.servicio.cliente.tarjeta;

public class RequestTarjeta
{
    private String nroTarjeta;

    private String codSeg;

    private String monto;

    private String idEntidad;

    private String cuotas;

    public RequestTarjeta()
    {
        super();
    }

    public String getNroTarjeta()
    {
        return nroTarjeta;
    }

    public void setNroTarjeta( String nroTarjeta )
    {
        this.nroTarjeta = nroTarjeta;
    }

    public String getCodSeg()
    {
        return codSeg;
    }

    public void setCodSeg( String codSeg )
    {
        this.codSeg = codSeg;
    }

    public String getMonto()
    {
        return monto;
    }

    public void setMonto( String monto )
    {
        this.monto = monto;
    }

    public String getIdEntidad()
    {
        return idEntidad;
    }

    public void setIdEntidad( String idEntidad )
    {
        this.idEntidad = idEntidad;
    }

    public String getCuotas()
    {
        return cuotas;
    }

    public void setCuotas( String cuotas )
    {
        this.cuotas = cuotas;
    }

}
