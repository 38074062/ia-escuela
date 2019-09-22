package ar.edu.uade.ia.escuela.dominio.modelo.facturacion;

public enum EstadoFactura
{
    PAGADA( "Pagada" ), VENCIDA( "Vencida" ), FACTURADA( "Facturada" );
    private String descripcion;

    private EstadoFactura( String descripcion )
    {
        this.descripcion = descripcion;
    }

    public String getDescripcion()
    {
        return descripcion;
    }

}
