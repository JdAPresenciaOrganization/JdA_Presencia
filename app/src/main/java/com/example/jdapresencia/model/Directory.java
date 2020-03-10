package com.example.jdapresencia.model;

public class Directory {

    String name, number, email;

    public Directory(String name, String number, String email) {
        this.name = name;
        this.number = number;
        this.email = email;
    }

    public Directory(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
