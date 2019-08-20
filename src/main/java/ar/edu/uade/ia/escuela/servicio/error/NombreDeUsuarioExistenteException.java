package ar.edu.uade.ia.escuela.servicio.error;

import ar.edu.uade.ia.escuela.servicio.MensajeServicio;

public class NombreDeUsuarioExistenteException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    @Override
    public String getMessage() {
        return MensajeServicio.NOMBRE_USUARIO_EXISTENTE.getDescripcion();
    }



}