package ar.edu.uade.ia.escuela.configuracion;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import ar.edu.uade.ia.escuela.presentacion.dto.UsuarioDto;

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FiltroAutenticacionJWT
    extends UsernamePasswordAuthenticationFilter
{

    private AuthenticationManager authenticationManager;

    public FiltroAutenticacionJWT( AuthenticationManager authenticationManager )
    {
        this.authenticationManager = authenticationManager;
        super.setAuthenticationFailureHandler( new AutenticacionJwtFallidaHandler() );
    }

    @Override
    public Authentication attemptAuthentication( HttpServletRequest request, HttpServletResponse response )
        throws AuthenticationException
    {
        try
        {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure( Feature.AUTO_CLOSE_SOURCE, true );
            UsuarioDto credenciales = objectMapper.readValue( request.getInputStream(), UsuarioDto.class );
            return authenticationManager.authenticate( new UsernamePasswordAuthenticationToken( credenciales.getNombreUsuario(),
                                                                                                credenciales.getContrasenia(),
                                                                                                new ArrayList<>() ) );
        }
        catch ( IOException e )
        {
            throw new RuntimeException( e );
        }
    }

    @Override
    protected void successfulAuthentication( HttpServletRequest request, HttpServletResponse response,
                                             FilterChain chain, Authentication auth )
        throws IOException, ServletException
    {
        String token = ProveedorToken.generarToken( auth );
        response.addHeader( Constantes.PREFIJO_HEADER_AUTORIZACION, Constantes.PREFIJO_TOKEN + " " + token );
    }
}