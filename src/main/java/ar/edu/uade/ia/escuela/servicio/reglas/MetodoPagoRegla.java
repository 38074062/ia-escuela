package ar.edu.uade.ia.escuela.servicio.reglas;

import ar.edu.uade.ia.escuela.dominio.modelo.facturacion.MetodoPago;
import ar.edu.uade.ia.escuela.dominio.modelo.facturacion.TarjetaCredito;
import ar.edu.uade.ia.escuela.dominio.modelo.facturacion.TransferenciaBancaria;

public class MetodoPagoRegla
{
    public static Boolean esPagoConTarjeta( MetodoPago metodoPago )
    {
        return metodoPago instanceof TarjetaCredito;
    }

    public static Boolean esPagoConTransferencia( MetodoPago metodoPago )
    {
        return metodoPago instanceof TransferenciaBancaria;
    }

}
