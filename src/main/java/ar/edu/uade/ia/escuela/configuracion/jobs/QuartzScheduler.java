package ar.edu.uade.ia.escuela.configuracion.jobs;

import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import ar.edu.uade.ia.escuela.servicio.jobs.FacturacionJob;

@Configuration
@ConditionalOnExpression( "'${using.spring.schedulerFactory}'=='true'" )
@EnableScheduling
public class QuartzScheduler
{

    private static final String CRON_MENSUAL = "0 0 1 * * ?";

    @Bean
    @Autowired
    public SpringBeanJobFactory springBeanJobFactory( ApplicationContext applicationContext )
    {
        AutoWiringSpringBeanJobFactory jobFactory = new AutoWiringSpringBeanJobFactory();
        jobFactory.setApplicationContext( applicationContext );
        return jobFactory;
    }

    @Bean
    @Autowired
    public SchedulerFactoryBean scheduler( Trigger[] triggers, JobDetail[] jobs, ApplicationContext applicationContext )
    {
        SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
        schedulerFactory.setConfigLocation( new ClassPathResource( "quartz.properties" ) );
        schedulerFactory.setJobFactory( springBeanJobFactory( null ) );
        schedulerFactory.setJobDetails( jobs );
        schedulerFactory.setTriggers( triggers );
        return schedulerFactory;
    }

    @Bean( name = "FacturacionJob" )
    public JobDetailFactoryBean detalleJobFacturacion()
    {
        JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
        jobDetailFactory.setJobClass( FacturacionJob.class );
        jobDetailFactory.setDurability( true );
        return jobDetailFactory;
    }

    @Bean
    public CronTriggerFactoryBean triggerJobFacturacion( @Qualifier( "FacturacionJob" ) JobDetail job )
    {
        CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
        trigger.setJobDetail( job );
        trigger.setCronExpression( CRON_MENSUAL );
        return trigger;
    }

}