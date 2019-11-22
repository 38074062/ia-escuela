package ar.edu.uade.ia.escuela.presentacion.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.*;

@JsonTypeInfo(use = Id.NAME, include = As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = TransferenciaBancariaDto.class, name = "transferenciaBancaria"),
    @JsonSubTypes.Type(value = TarjetaCreditoDto.class, name = "tarjetaCredito")
})
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
