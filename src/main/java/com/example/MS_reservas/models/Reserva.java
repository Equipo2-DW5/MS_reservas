package com.example.MS_reservas.models;

import org.springframework.data.annotation.Id;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
@Validated
public class Reserva {
    @Id
    private String idReserva;
    @NotBlank(message = "El campo descripción es requerido")
    private String descripcion;
    @NotNull(message = "El campo fecha inicio de solicitud es requerido")
    private Date fechaSolicitud;
    @NotNull(message = "El campo fecha fin de solicitud es requerido")
    private Date fechaFin;
    @NotBlank(message = "El campo duracion es requerido")
    private String duracion;
    @NotBlank(message = "El campo id de usuario es requerido")
    private String idUsuario;
    @NotBlank(message = "El campo id de laboratorio es requerido")
    private String idLaboratorio;
    private Boolean estado;


    public Reserva(String descripcion, Date fechaSolicitud, Date fechaFin, String duracion, String idUsuario, String idLaboratorio) {
        this.descripcion = descripcion;
        this.fechaSolicitud = fechaSolicitud;
        this.fechaFin = fechaFin;
        this.duracion = duracion;
        this.idUsuario = idUsuario;
        this.idLaboratorio = idLaboratorio;
        this.estado = false;
    }

    public String getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(String idReserva) {
        this.idReserva = idReserva;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getIdLaboratorio() {
        return idLaboratorio;
    }

    public void setIdLaboratorio(String idLaboratorio) {
        this.idLaboratorio = idLaboratorio;
    }

    public Boolean getEstado() {return estado; }

    public void setEstado(Boolean estado) { this.estado = estado;  }

}
