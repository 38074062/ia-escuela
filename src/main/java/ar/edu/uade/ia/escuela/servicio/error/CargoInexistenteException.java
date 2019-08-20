package ar.edu.uade.ia.escuela.servicio.error;

import ar.edu.uade.ia.escuela.servicio.MensajeServicio;

public class CargoInexistenteException
    extends RuntimeException
{

    private static final long serialVersionUID = 1L;

    @Override
    public String getMessage()
    {
        return MensajeServicio.CARGO_INEXISTENTE.getDescripcion();
    }
}
