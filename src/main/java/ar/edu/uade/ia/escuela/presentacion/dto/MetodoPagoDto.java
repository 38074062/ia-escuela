package ar.edu.uade.ia.escuela.presentacion.dto;

public abstract class MetodoPagoDto
{
    private Boolean debitoAutomatico = false;

    public MetodoPagoDto()
    {
        super();
    }

    public Boolean getDebitoAutomatico()
    {
        return debitoAutomatico;
    }

    public void setDebitoAutomatico( Boolean debitoAutomatico )
    {
        this.debitoAutomatico = debitoAutomatico;
    }

}
