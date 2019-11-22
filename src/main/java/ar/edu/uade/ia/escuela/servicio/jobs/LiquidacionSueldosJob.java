package ar.edu.uade.ia.escuela.servicio.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.uade.ia.escuela.servicio.ServicioUsuario;

public class LiquidacionSueldosJob
    implements Job
{
    
    @Autowired
    private ServicioUsuario servicio;

    @Override
    public void execute( JobExecutionContext context )
        throws JobExecutionException
    {
        servicio.liquidarSueldos();

    }

}
