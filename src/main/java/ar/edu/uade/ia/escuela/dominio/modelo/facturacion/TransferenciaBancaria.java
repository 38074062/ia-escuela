package ar.edu.uade.ia.escuela.dominio.modelo.facturacion;

import javax.persistence.Entity;

@Entity
public class TransferenciaBancaria extends MetodoPago
{

    private String cuentaBancaria;

    public TransferenciaBancaria()
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
