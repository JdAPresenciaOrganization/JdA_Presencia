package com.example.jdapresencia;

public class User {

    String idU, rol, username, password;

    public User(String idU, String rol, String username, String password) {
        this.idU = idU;
        this.rol = rol;
        this.username = username;
        this.password = password;
    }

    public String getIdU() {
        return idU;
    }

    public void setIdU(String idU) {
        this.idU = idU;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
