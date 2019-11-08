package ar.edu.uade.ia.escuela.dominio.modelo.facturacion;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import ar.edu.uade.ia.escuela.dominio.modelo.EntidadBase;

@Inheritance( strategy = InheritanceType.JOINED )
@Entity
public abstract class MetodoPago extends EntidadBase
{
    private Boolean debitoAutomatico;

    public MetodoPago()
    {
        super();
        this.debitoAutomatico = false;
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
