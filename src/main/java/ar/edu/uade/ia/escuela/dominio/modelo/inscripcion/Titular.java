package ar.edu.uade.ia.escuela.dominio.modelo.inscripcion;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import ar.edu.uade.ia.escuela.dominio.modelo.EntidadBase;
import ar.edu.uade.ia.escuela.dominio.modelo.facturacion.CuentaCorriente;
import ar.edu.uade.ia.escuela.dominio.modelo.facturacion.Factura;

@Entity
public class Titular
    extends EntidadBase
{
    private Integer dni;

    private String nombre;

    private String apellido;

    private String direccion;

    private String email;

    @OneToMany( mappedBy = "titular" )
    private List<Inscripcion> inscripciones = new LinkedList<Inscripcion>();

    @OneToOne( cascade = CascadeType.ALL )
    private CuentaCorriente cuentaCorriente;

    private String preferenciaTipoFactura;

    public Titular()
    {
        super();
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

    public void setNombre( String nom )
    {
        nombre = nom;
    }

    public String getApellido()
    {
        return apellido;
    }

    public void setApellido( String ape )
    {
        apellido = ape;
    }

    public String getDireccion()
    {
        return direccion;
    }

    public void setDireccion( String dir )
    {
        direccion = dir;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail( String mail )
    {
        email = mail;
    }

    public List<Inscripcion> getInscripciones()
    {
        return inscripciones;
    }

    public void setInscripciones( List<Inscripcion> inscripciones )
    {
        this.inscripciones = inscripciones;
    }

    public CuentaCorriente getCuentaCorriente()
    {
        return cuentaCorriente;
    }

    public void setCuentaCorriente( CuentaCorriente cuentaCorriente )
    {
        this.cuentaCorriente = cuentaCorriente;
    }

    public String getPreferenciaTipoFactura()
    {
        return preferenciaTipoFactura;
    }

    public void setPreferenciaTipoFactura( String preferenciaTipoFactura )
    {
        this.preferenciaTipoFactura = preferenciaTipoFactura;
    }

    public List<Inscripcion> getInscripcionesActivas()
    {
        return this.inscripciones.stream().filter( inscripcion -> inscripcion.estaActiva() ).collect( Collectors.toList() );
    }

    public void addFacturaACuentaCorriente( Factura factura )
    {
        this.cuentaCorriente.agregarFactura( factura );
    }

    public void registrarPago( Long facturaId, LocalDate fecha, float monto )
    {
        cuentaCorriente.registrarPago( facturaId, fecha, monto );

    }

}
