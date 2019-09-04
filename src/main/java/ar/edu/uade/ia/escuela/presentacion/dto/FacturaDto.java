package ar.edu.uade.ia.escuela.presentacion.dto;

import java.sql.Date;

public class FacturaDto 
{
	private Long id;
	
	private float total;
	
	private Date fecha;
	
	private String tipo;
	
	private Date vencimiento;
	
	public FacturaDto(){
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Date getVencimiento() {
		return vencimiento;
	}

	public void setVencimiento(Date vencimiento) {
		this.vencimiento = vencimiento;
	}

	
}
