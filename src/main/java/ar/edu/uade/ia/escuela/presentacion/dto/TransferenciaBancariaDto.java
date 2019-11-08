package ar.edu.uade.ia.escuela.presentacion.dto;

public class TransferenciaBancariaDto extends MetodoPagoDto
{

    private String cuentaBancaria;

    public TransferenciaBancariaDto()
    {
        super();
    }

    public String getCuentaBancaria()
    {
        return cuentaBancaria;
    }

    public void setCuentaBancaria( String cuentaBancaria )
    {
        this.cuentaBancaria = cuentaBancaria;
    }

}
