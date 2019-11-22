package ar.edu.uade.ia.escuela.servicio.cliente.banco;

public class RequestBanco
{
    private String user;

    private String origenMovimiento;
    
    private String movimientos;

    public String getUser()
    {
        return user;
    }

    public void setUser( String user )
    {
        this.user = user;
    }

    public String getOrigenMovimiento()
    {
        return origenMovimiento;
    }

    public void setOrigenMovimiento( String origenMovimiento )
    {
        this.origenMovimiento = origenMovimiento;
    }

    public String getMovimientos()
    {
        return movimientos;
    }

    public void setMovimientos( String movimientos )
    {
        this.movimientos = movimientos;
    }
    
    
}
