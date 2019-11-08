package ar.edu.uade.ia.escuela.servicio;

public enum MensajeServicio
{
    NOMBRE_USUARIO_EXISTENTE( "El nombre de usuario ya existe" ),
    DNI_EXISTENTE( "El dni ingresado ya está asociado" ),
    CARGO_INEXISTENTE( "El cargo ingresado es inválido" ),
    NOMBRE_EXISTENTE( "El nombre ya existe" ),
    TITULAR_NOEXISTE( "El titular no existe" ),
    ERROR_PAGO( "Se produjo un error al informar el pago" ),
    ERROR_PAGO_BANCO( "Se produjo un error al informar el pago a la entidad bancaria. " ),
    METODO_PAGO_INVALIDO( "El metodo de pago es invalido" );

    private String descripcion;

    private MensajeServicio( String descripcion )
    {
        this.descripcion = descripcion;
    }

    public String getDescripcion()
    {
        return this.descripcion;
    }
}