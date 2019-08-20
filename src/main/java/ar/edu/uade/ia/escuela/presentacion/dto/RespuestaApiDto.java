package ar.edu.uade.ia.escuela.presentacion.dto;

public class RespuestaApiDto<T> {
    private String mensaje;
    private T datos;
    private Boolean estado;

    public RespuestaApiDto(){
        
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public T getDatos() {
        return datos;
    }

    public void setDatos(T datos) {
        this.datos = datos;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }


}