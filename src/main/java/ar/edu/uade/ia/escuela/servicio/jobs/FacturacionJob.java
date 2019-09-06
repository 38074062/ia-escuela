package ar.edu.uade.ia.escuela.servicio.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.uade.ia.escuela.servicio.ServicioFactura;

public class FacturacionJob
    implements Job
{
    
    @Autowired
    private ServicioFactura servicio;

    @Override
    public void execute( JobExecutionContext context )
        throws JobExecutionException
    {
        servicio.generarFactura();

    }

}
