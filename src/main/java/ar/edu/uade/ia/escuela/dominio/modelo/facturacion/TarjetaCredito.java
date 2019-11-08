package ar.edu.uade.ia.escuela.dominio.modelo.facturacion;

public class TarjetaCredito
    extends MetodoPago
{
    private String nroTarjeta;

    private String codSeg;

    public TarjetaCredito()
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

}
