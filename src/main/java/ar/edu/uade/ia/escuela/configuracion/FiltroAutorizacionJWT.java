package ar.edu.uade.ia.escuela.configuracion;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import ar.edu.uade.ia.escuela.servicio.ServicioUsuario;

public class FiltroAutorizacionJWT
    extends OncePerRequestFilter
{

    @Autowired
    private ServicioUsuario servicioUsuario;

    @Override
    protected void doFilterInternal( HttpServletRequest req, HttpServletResponse res, FilterChain chain )
        throws IOException, ServletException
    {
        String headerAutorizacion = req.getHeader( Constantes.PREFIJO_HEADER_AUTORIZACION );
        if ( StringUtils.isEmpty( headerAutorizacion ) || !headerAutorizacion.startsWith( Constantes.PREFIJO_TOKEN ) )
        {
            chain.doFilter( req, res );
            return;
        }
        final String token = headerAutorizacion.replace( Constantes.PREFIJO_TOKEN + " ", "" );
        String nombreUsuario = ProveedorToken.getNombreUsuario( token );
        UserDetails usuario = servicioUsuario.loadUserByUsername( nombreUsuario );
        UsernamePasswordAuthenticationToken authenticationToken = ProveedorToken.getAutenticacion( token, usuario );
        SecurityContextHolder.getContext().setAuthentication( authenticationToken );
        chain.doFilter( req, res );
    }

}