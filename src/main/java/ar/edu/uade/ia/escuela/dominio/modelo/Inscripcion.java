package ar.edu.uade.ia.escuela.dominio.modelo;

import java.util.List;
import javax.persistence.Entity;

@Entity
public class Inscripcion 
	extends EntidadBase
{
	private float cuota;
	
	private float total;
	
	private List<Alumno> alumnos;
	
	private List<Servicio> servicios;
	
	public Inscripcion()
	{
		super();
	}

	public float getCuota()
	{
		return cuota;
	}
	
	public float getTotal()
	{
		return total;
	}
	
	public void calcularCuota()
	{
		
	}
	
	public void calcularTotal()
	{
		
	}
}
