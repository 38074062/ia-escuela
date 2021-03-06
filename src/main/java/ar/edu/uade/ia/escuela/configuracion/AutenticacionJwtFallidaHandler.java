package ar.edu.uade.ia.escuela.configuracion;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class AutenticacionJwtFallidaHandler implements AuthenticationFailureHandler {

    private final HttpStatus estadoRespuesta;

	public AutenticacionJwtFallidaHandler(HttpStatus statusErrorResponse) {
		this.estadoRespuesta = statusErrorResponse;
	}

	public AutenticacionJwtFallidaHandler() {
		this.estadoRespuesta = HttpStatus.UNAUTHORIZED;
	}

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException e) throws IOException, ServletException {
		response.setStatus(estadoRespuesta.value());
		response.setContentType("application/json");
		response.getWriter().append(jsonResponse());
	}

	private String jsonResponse() {
		long date = new Date().getTime();
		return "{\"timestamp\": " + date + ", "
				+ "\"status\": " + estadoRespuesta.value() + ", "
				+ "\"error\": \"Unauthorized\", "
				+ "\"message\": \"Authentication failed: bad credentials\", "
				+ "\"path\": \"/login\"}";
	}

}