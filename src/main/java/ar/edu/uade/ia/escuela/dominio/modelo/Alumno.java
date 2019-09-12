package ar.edu.uade.ia.escuela.dominio.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;


@Entity
public class Alumno 
	extends EntidadBase
{
	@GeneratedValue(strategy= GenerationType.IDENTITY) 
	private  Integer legajo;
	
	private String nombre;
	
	private String apellido;
	
	private Integer dni;
	
	@ManyToOne
	private Titular titular;
	
	public Alumno()
	{
		super();
	}
	
	 public Integer getLegajo()
	 {
	   	return legajo;
	 }
	 
	 public Integer getDni() {
		return dni;
	}

	public void setDni(Integer dni) {
		this.dni = dni;
	}

	public void setLegajo(Integer leg)
	 {
		 legajo = leg;
	 }
	   
	 public String getNombre()
	 {
	   	return nombre;
	 }

	 public void setNombre(String nom)
	 {
	   	nombre = nom;
	 }
	    
	 public String getApellido()
	 {
	   	return apellido;
	 }

	 public void setApellido(String ape)
	 {
	   	apellido = ape;
	 }	
}
