package ar.edu.uade.ia.escuela.presentacion.dto;

import java.util.List;

import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import ar.edu.uade.ia.escuela.dominio.modelo.Alumno;
import ar.edu.uade.ia.escuela.dominio.modelo.Servicio;
import ar.edu.uade.ia.escuela.dominio.modelo.Titular;

public class InscripcionDto 
{
	private Long id; 
	
	@OneToOne
	private Titular titular;
	
	@OneToOne
	private Alumno alumno;
	
	@OneToMany
	private List<Servicio> servicios;
	
	public InscripcionDto()
	{
		super();
	}

	public float getCuota()
	{
		float cuota;
		cuota = calcularTotal()/12;
		return cuota;
	}
			
	private float calcularTotal()
	{
		float total=0;
		for(Servicio s:servicios)
		{
			total = total + s.getPrecio();
		}
		return total;
	}
	
	public Long getId(){
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}