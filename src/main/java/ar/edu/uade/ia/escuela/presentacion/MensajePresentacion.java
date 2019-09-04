package ar.edu.uade.ia.escuela.presentacion;

public enum MensajePresentacion
{
    USUARIO_CREADO( "Usuario creado exitosamente" ), SERVICIO_MODIFICADO("Servicio modificado exitosamente"), SERVICIO_CREADO( "Servicio creado exitosamente" ), SERVICIO_BORRADO( "Servicio borrado exitosamente" ), ALUMNO_INSCRIPTO("Alumno inscripto correctamente"), FACTURA_CREADA("Factura creada correctamente");

    private String descripcion;

    private MensajePresentacion( String descripcion )
    {
        this.descripcion = descripcion;
    }

    public String getDescripcion()
    {
        return descripcion;
    }
}
