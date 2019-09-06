package ar.edu.uade.ia.escuela.dominio.modelo;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


@Entity
public class Titular 
	extends EntidadBase
	{
		private Integer dni;
	
		private String nombre;
	
		private String apellido;

	    private String direccion;

	    private String email;

	    private Integer cuentaBancaria;
	    
	    @OneToMany
	    private List<Inscripcion> inscripciones; 
	    
	    @OneToMany
	    private List<Factura> facturas;
	    
	    @OneToOne
	    private CuentaCorriente cuentaCorriente;
	   
	        
	    public Titular()
	    {
	    	super();
	    }
	    
	    public Integer getDni()
	    {
	    	return dni;
	    }
	    
	    public void setDni(Integer dni)
	    {
	    	this.dni = dni;
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
	    
	    public String getDireccion()
	    {
	    	return direccion;
	    }

	    public void setDireccion(String dir)
	    {
	    	direccion = dir;
	    }
	    
	    public String getEmail()
	    {
	    	return email;
	    }

	    public void setEmail(String mail)
	    {
	    	email = mail;
	    }
	    
	    public Integer getCuentaBancaria()
	    {
	    	return cuentaBancaria;
	    }

	    public void setCuentaBancaria(Integer cuen)
	    {
	    	cuentaBancaria = cuen;
	    }
	    
		
		public List<Inscripcion> getInscripciones() {
			return inscripciones;
		}

		public void setInscripciones(List<Inscripcion> inscripciones) {
			this.inscripciones = inscripciones;
		}

		public List<Factura> getFacturas() {
			return facturas;
		}

		public void setFacturas(List<Factura> facturas) {
			this.facturas = facturas;
		}
}
