package ar.edu.uade.ia.escuela;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class EscuelaApplication
{

    public static void main( String[] args )
    {
        SpringApplication.run( EscuelaApplication.class, args );
    }

    @Bean
    public RestTemplate getRestTemplate()
    {
        return new RestTemplate();
    }
}
