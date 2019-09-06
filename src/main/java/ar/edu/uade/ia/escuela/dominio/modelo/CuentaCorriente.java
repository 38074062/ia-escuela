package ar.edu.uade.ia.escuela.dominio.modelo;

import java.util.List;

public class CuentaCorriente 
{
	List<itemCuentas> itemsCuentas;
	
	public float estadoCuenta(){
		float aux = 0;
		for(itemCuentas i:itemsCuentas)
		{
			aux += i.getFacturas().getTotal() - i.getMonto(); 
		}
		return aux;
	}

}
