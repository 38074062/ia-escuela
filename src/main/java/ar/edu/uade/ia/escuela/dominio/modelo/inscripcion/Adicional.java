package ar.edu.uade.ia.escuela.dominio.modelo.inscripcion;

import javax.persistence.Entity;

@Entity
public class Adicional 
	extends Servicio
{
	public Adicional()
	{
		super();
	}

    @Override
    public String getCategoria()
    {
        return "Adicional";
    }
}
