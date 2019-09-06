package ar.edu.uade.ia.escuela.dominio.modelo;

import java.time.LocalDate;

public class itemCuentas {
	
	private Factura facturas;
	
	private LocalDate fechapago;
	
	private float monto;


	public Factura getFacturas() {
		return facturas;
	}

	public void setFacturas(Factura facturas) {
		this.facturas = facturas;
	}

	public LocalDate getFechapago() {
		return fechapago;
	}

	public void setFechapago(LocalDate fechapago) {
		this.fechapago = fechapago;
	}

	public float getMonto() {
		return monto;
	}

	public void setMonto(float monto) {
		this.monto = monto;
	}
	
	

}
