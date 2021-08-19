package com.example.MS_reservas.models;

import org.springframework.data.annotation.Id;

import java.util.Date;

public class Reserva {
    @Id
    private String idReserva;
    private String descripcion;
    private Date fechaSolicitud;
    private Date fechaFin;
    private String duracion;
    private String idUsuario;
    private String idLaboratorio;

    public Reserva(String idReserva, String descripcion, Date fechaSolicitud, Date fechaFin, String duracion, String idUsuario, String idLaboratorio) {
        this.idReserva = idReserva;
        this.descripcion = descripcion;
        this.fechaSolicitud = fechaSolicitud;
        this.fechaFin = fechaFin;
        this.duracion = duracion;
        this.idUsuario = idUsuario;
        this.idLaboratorio = idLaboratorio;
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
}
