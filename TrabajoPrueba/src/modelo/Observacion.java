package modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Observacion {

    private String descripcion;
    private LocalDateTime fechaHora;

    public Observacion(String descripcion) {
        this.descripcion = descripcion;
        this.fechaHora = LocalDateTime.now();
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    @Override
    public String toString() {

        DateTimeFormatter formato =
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        return "Descripción: " + descripcion +
                "\nFecha y hora: " + fechaHora.format(formato);
    }
}
