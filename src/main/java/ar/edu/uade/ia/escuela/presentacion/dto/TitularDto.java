package ar.edu.uade.ia.escuela.presentacion.dto;

public class TitularDto
{
	private Long id;
	
    private Integer dni;

    private String nombre;

    private String apellido;

    private String direccion;

    private String email;

    private String cuentaBancaria;
    
    private String nroTarjeta;
    
    private String codSeg;
    
    private String preferenciaTipoFactura;
    
    private String preferenciaPago;

    public Integer getDni()
    {
        return dni;
    }

    public void setDni( Integer dni )
    {
        this.dni = dni;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre( String nombre )
    {
        this.nombre = nombre;
    }

    public String getApellido()
    {
        return apellido;
    }

    public void setApellido( String apellido )
    {
        this.apellido = apellido;
    }

    public String getDireccion()
    {
        return direccion;
    }

    public void setDireccion( String direccion )
    {
        this.direccion = direccion;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail( String email )
    {
        this.email = email;
    }

    public String getCuentaBancaria()
    {
        return cuentaBancaria;
    }

    public void setCuentaBancaria( String cuentaBancaria )
    {
        this.cuentaBancaria = cuentaBancaria;
    }

	public Long getId() {
		// TODO Auto-generated method stub
		return id;
	}

	public void setId(Long id) {
		this.id = id;		
	}
	
	public String getPreferenciaTipoFactura() {
		return preferenciaTipoFactura;
	}

	public void setPreferenciaTipoFactura(String preferenciaTipoFactura) {
		this.preferenciaTipoFactura = preferenciaTipoFactura;
	}

	public String getPreferenciaPago() {
		return preferenciaPago;
	}

	public void setPreferenciaPago(String preferenciaPago) {
		this.preferenciaPago = preferenciaPago;
	}

	public String getNroTarjeta() {
		return nroTarjeta;
	}

	public void setNroTarjeta(String nroTarjeta) {
		this.nroTarjeta = nroTarjeta;
	}

	public String getCodSeg() {
		return codSeg;
	}

	public void setCodSeg(String codSeg) {
		this.codSeg = codSeg;
	}

}
