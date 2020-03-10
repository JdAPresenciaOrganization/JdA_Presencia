package com.example.jdapresencia.model;

public class Directory {

    String name, number, email, photo;

    public Directory(String name, String number, String email, String photo) {
        this.name = name;
        this.number = number;
        this.email = email;
        this.photo = photo;
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
