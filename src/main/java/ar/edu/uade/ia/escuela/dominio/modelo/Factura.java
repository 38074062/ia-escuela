package ar.edu.uade.ia.escuela.dominio.modelo;

import java.sql.Date;
import java.util.List;
import javax.persistence.Entity;

@Entity
public class Factura 
	extends EntidadBase
{
	private float total;
	
	private Date fecha;
	
	private String tipo;
	
	private Date vencimiento;
	
	private Titular titular;
	
	private List<Inscripcion> inscripciones;
	
	public Factura()
	{
		super();
	}

	public float getTotal()
	{
		return total;
	}
	
	public void calcularTotal()
	{
		
	}
	
	public Date getFecha()
	{
		return fecha;
	}
	
	public void setFecha(Date fecha)
	{
		fecha = fecha;
	}
	
	public Date getVencimiento()
	{
		return vencimiento;
	}
	
	public String getTipo()
	{
		return tipo;
	}
	
	public void setTipo(String tipo)
	{
		tipo = tipo;
	}
	public void setVencimiento(Date ven)
	{
		vencimiento = ven;
	}
}

