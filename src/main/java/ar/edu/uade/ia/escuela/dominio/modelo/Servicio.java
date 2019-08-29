package ar.edu.uade.ia.escuela.dominio.modelo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Where;

@MappedSuperclass
@Where( clause = "deleted=0" )
public abstract class Servicio
{
	 @Id
	 @GeneratedValue( strategy = GenerationType.AUTO )
	 protected Long id;
	 
	 protected Boolean eliminado;
	 
	 private String nombre;
	 
	 private String tipo;
	 
	 public Servicio()
	 {
		 super();
		 eliminado = false;
	 }
	 
	 public Long getId()
	 {
	    return id;
	 }

	 public void setId( Long id )
	 {
	    this.id = id;
	 }

	 public Boolean getEliminado()
	 {
	    return eliminado;
	 }

	 public void setEliminado( Boolean eliminado )
	 {
	    this.eliminado = eliminado;
	 }
	 
	 public String getNombre()
	 {
	   	return nombre;
	 }

	 public void setNombre(String nom)
	 {
	   	nombre = nom;
	 }
	 
	 public String getTipo()
	 {
	   	return tipo;
	 }

	 public void setTipo(String tipo)
	 {
	   	tipo = tipo;
	 }
}
