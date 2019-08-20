package ar.edu.uade.ia.escuela.configuracion;

import io.jsonwebtoken.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

public class ProveedorToken {

	private ProveedorToken() {
	}

	public static String generarToken(Authentication autenticacion) {
		final String autoridades = autenticacion.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));
		return Jwts.builder()
				.setSubject(autenticacion.getName())
				.claim(Constantes.CLAVE_AUTORIDADES, autoridades)
				.signWith(SignatureAlgorithm.HS256, Constantes.CLAVE_FIRMA)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setIssuer(Constantes.EDITOR_TOKEN)
				.setExpiration(new Date(System.currentTimeMillis() + Constantes.VALIDEZ_TOKEN*1000))
				.compact();
	}

	public static UsernamePasswordAuthenticationToken getAutenticacion(final String token,
			final UserDetails userDetails) {
		final JwtParser jwtParser = Jwts.parser().setSigningKey(Constantes.CLAVE_FIRMA);
		final Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);
		final Claims claims = claimsJws.getBody();
		final Collection<SimpleGrantedAuthority> authorities =
				Arrays.stream(claims.get(Constantes.CLAVE_AUTORIDADES).toString().split(","))
						.map(SimpleGrantedAuthority::new)
						.collect(Collectors.toList());
		return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
	}

	public static String getNombreUsuario(final String token) {
		final JwtParser jwtParser = Jwts.parser().setSigningKey(Constantes.CLAVE_FIRMA);
		final Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);
		return claimsJws.getBody().getSubject();
	}

}