package ar.edu.uade.ia.escuela.configuracion;

import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import ar.edu.uade.ia.escuela.datos.RepositorioPrivilegio;
import ar.edu.uade.ia.escuela.datos.RepositorioRol;
import ar.edu.uade.ia.escuela.datos.RepositorioUsuario;
import ar.edu.uade.ia.escuela.dominio.modelo.Cargo;
import ar.edu.uade.ia.escuela.dominio.modelo.Privilegio;
import ar.edu.uade.ia.escuela.dominio.modelo.Rol;
import ar.edu.uade.ia.escuela.dominio.modelo.Usuario;

@Component
public class DatosInicialesLoader implements ApplicationListener<ContextRefreshedEvent> {
    private Boolean configurado = false;

    @Autowired
    private RepositorioRol repositorioRol;

    @Autowired
    private RepositorioPrivilegio repositorioPrivilegio;

    @Autowired
    private RepositorioUsuario repositorioUsuario;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (configurado) {
            return;
        }
        Privilegio privilegioLectura = crearPrivilegioSiNoExiste("LECTURA");
        Privilegio privilegioEscritura = crearPrivilegioSiNoExiste("ESCRITURA");
        List<Privilegio> privilegiosAdministrador = Arrays.asList(privilegioLectura, privilegioEscritura);
        crearRolSiNoExiste(Cargo.ADMINISTRADOR.getRol(), privilegiosAdministrador);
        crearRolSiNoExiste(Cargo.EMPLEADO.getRol(), Arrays.asList(privilegioEscritura, privilegioLectura));
        crearRolSiNoExiste(Cargo.DOCENTE.getRol(), Arrays.asList(privilegioLectura));
        crearUsuarioAdministradorSiNoExiste("test@test.com");
        configurado = true;
    }

    @Transactional
    private void crearUsuarioAdministradorSiNoExiste(String nombreUsuario) {
        Usuario usuario = repositorioUsuario.findByNombreUsuario(nombreUsuario);
        if (usuario == null) {
            usuario = new Usuario();
            Rol rolAdministrador = repositorioRol.findByRol("ROLE_ADMINISTRADOR").get();
            usuario.setNombre("Admin");
            usuario.setApellido("Admin");
            usuario.setContrasenia(passwordEncoder.encode("test"));
            usuario.setNombreUsuario("test@test.com");
            usuario.setRoles(Arrays.asList(rolAdministrador));
            usuario.setCargo(Cargo.ADMINISTRADOR);
            repositorioUsuario.save(usuario);
        }
    }

    @Transactional
    private Rol crearRolSiNoExiste(String nombre, List<Privilegio> privilegios) {
        Rol rol = repositorioRol.findByRol(nombre).orElse(null);
        if (rol == null) {
            rol = new Rol();
            rol.setNombre(nombre);
            rol.setPrivilegios(privilegios);
            repositorioRol.save(rol);
        }
        return rol;
    }

    @Transactional
    private Privilegio crearPrivilegioSiNoExiste(String nombre) {
        Privilegio privilegio = repositorioPrivilegio.findByPrivilegio(nombre).orElse(null);
        if (privilegio == null) {
            privilegio = new Privilegio();
            privilegio.setNombre(nombre);
            repositorioPrivilegio.save(privilegio);
        }
        return privilegio;
    }

}
