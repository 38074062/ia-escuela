package ar.edu.uade.ia.escuela.presentacion;

public enum MensajePresentacion
{
    USUARIO_CREADO( "Usuario creado exitosamente" );

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
