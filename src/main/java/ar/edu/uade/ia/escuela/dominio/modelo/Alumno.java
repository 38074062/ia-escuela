package ar.edu.uade.ia.escuela.dominio.modelo;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;


@Entity
public class Alumno 
	extends EntidadBase
{
	private Integer legajo;
	
	private String nombre;
	
	private String apellido;
	
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
