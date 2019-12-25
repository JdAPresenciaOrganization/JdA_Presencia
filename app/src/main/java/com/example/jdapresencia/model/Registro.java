package com.example.jdapresencia.model;

import java.io.Serializable;

public class Registro implements Serializable {

    int idR, id_trabajador;
    String fecha, horaEntrada, horaSalida, horasDia;

    public Registro(int idR, String fecha, String horaEntrada, String horaSalida, String horasDia, int id_trabajador) {
        this.idR = idR;
        this.fecha = fecha;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
        this.horasDia = horasDia;
        this.id_trabajador = id_trabajador;
    }

    public int getIdR() {
        return idR;
    }

    public void setIdR(int idR) {
        this.idR = idR;
    }

    public int getId_trabajador() {
        return id_trabajador;
    }

    public void setId_trabajador(int id_trabajador) {
        this.id_trabajador = id_trabajador;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(String horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public String getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(String horaSalida) {
        this.horaSalida = horaSalida;
    }

    public String getHorasDia() {
        return horasDia;
    }

    public void setHorasDia(String horasDia) {
        this.horasDia = horasDia;
    }
}
