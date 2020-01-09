package com.example.jdapresencia.model;

public class User {

    int idU;
    String rol, username, password, salt;

    public User(int idU, String rol, String username, String password, String salt) {
        this.idU = idU;
        this.rol = rol;
        this.username = username;
        this.password = password;
        this.salt = salt;
    }

    public int getIdU() {
        return idU;
    }

    public void setIdU(int idU) {
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

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
