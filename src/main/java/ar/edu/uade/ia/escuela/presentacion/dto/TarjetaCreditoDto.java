package ar.edu.uade.ia.escuela.presentacion.dto;

public class TarjetaCreditoDto
    extends MetodoPagoDto
{
    private String nroTarjeta;

    private String codSeg;

    public TarjetaCreditoDto()
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
