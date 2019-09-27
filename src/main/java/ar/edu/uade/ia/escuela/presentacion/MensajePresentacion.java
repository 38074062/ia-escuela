package ar.edu.uade.ia.escuela.presentacion;

public enum MensajePresentacion
{
    USUARIO_CREADO( "Usuario creado exitosamente" ),
    SERVICIO_MODIFICADO( "Servicio modificado exitosamente" ),
    SERVICIO_CREADO( "Servicio creado exitosamente" ),
    SERVICIO_BORRADO( "Servicio borrado exitosamente" ),
    ALUMNO_INSCRIPTO( "Alumno inscripto correctamente" ),
    FACTURA_CREADA( "Factura creada correctamente" ),
    CARGA_HORARIA_AGREGADA( "Carga horaria registrada correctamente" ),
    TITULAR_CREADO( "Titular creado exitosamente" ),
    PAGO_REGISTRADO( "Pago registrado exitosamente" ),
    INSCRIPCION_MODIFICADA( "Inscripción modificada exitosamente" ),
    INSCRIPCION_ELIMINADA( "La inscripción se dió de baja exitosamente" ),
    TITULAR_BORRADO( "Titular eliminado exitosamente" ),
    TITULAR_MODIFICADO( "Titular modificado exitosamente" ),
    USUARIO_BORRADO( "Empleado eliminado exitosamente" ),
    USUARIO_MODIFICADO( "Empleado modificado exitosamente" );

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
