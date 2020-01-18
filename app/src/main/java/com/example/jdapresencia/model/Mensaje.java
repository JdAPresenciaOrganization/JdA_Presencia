package com.example.jdapresencia.model;

import java.util.Date;

public class Mensaje {

    int id_trabajador;
    String emisor, receptor, mensaje;
    long messageTime;

    public Mensaje(String emisor, String receptor, String mensaje, int id_trabajador) {
        this.emisor = emisor;
        this.receptor = receptor;
        this.mensaje = mensaje;
        this.id_trabajador = id_trabajador;

        // Initialize to current time
        messageTime = new Date().getTime();
    }

    public Mensaje(){

    }

    public int getId_trabajador() {
        return id_trabajador;
    }

    public void setId_trabajador(int id_trabajador) {
        this.id_trabajador = id_trabajador;
    }

    public String getEmisor() {
        return emisor;
    }

    public void setEmisor(String emisor) {
        this.emisor = emisor;
    }

    public String getReceptor() {
        return receptor;
    }

    public void setReceptor(String receptor) {
        this.receptor = receptor;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }
}
