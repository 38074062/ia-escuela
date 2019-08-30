package ar.edu.uade.ia.escuela.dominio.modelo;

import java.sql.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Factura 
	extends EntidadBase
{
	private float total;
	
	private Date fecha;
	
	private String tipo;
	
	private Date vencimiento;
	
	@ManyToOne
	private Titular titular;
	
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
		titula
	}
	
	public Date getFecha()
	{
		return fecha;
	}
	
	public void setFecha(Date fecha)
	{
		this.fecha = fecha;
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
		this.tipo = tipo;
	}
	public void setVencimiento(Date ven)
	{
		vencimiento = ven;
	}
}

