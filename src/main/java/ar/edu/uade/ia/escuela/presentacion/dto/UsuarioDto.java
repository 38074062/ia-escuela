package ar.edu.uade.ia.escuela.presentacion.dto;

public class UsuarioDto {
    private String nombreUsuario;

    private String contrasenia;

    public UsuarioDto() {

    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

}