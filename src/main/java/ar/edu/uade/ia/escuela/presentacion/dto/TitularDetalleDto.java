package ar.edu.uade.ia.escuela.presentacion.dto;

import java.util.List;

public class TitularDetalleDto
{
    private Long id;

    private Integer dni;

    private String nombre;

    private String apellido;

    private String direccion;

    private String email;
    
    private String preferenciaTipoFactura;
    
    private String preferenciaPago;

    private List<InscripcionDetalleDto> inscripciones;

    private List<EstadoFacturasDto> facturas;

    public TitularDetalleDto()
    {
        super();
    }

    public Long getId()
    {
        return id;
    }

    public void setId( Long id )
    {
        this.id = id;
    }

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

    public List<InscripcionDetalleDto> getInscripciones()
    {
        return inscripciones;
    }

    public void setInscripciones( List<InscripcionDetalleDto> inscripciones )
    {
        this.inscripciones = inscripciones;
    }

    public List<EstadoFacturasDto> getFacturas()
    {
        return facturas;
    }

    public void setFacturas( List<EstadoFacturasDto> facturas )
    {
        this.facturas = facturas;
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
    
}
