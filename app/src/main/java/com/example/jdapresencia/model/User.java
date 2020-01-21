package com.example.jdapresencia.model;

import android.provider.BaseColumns;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = User.TABLE_NAME)
public class User {

    //Nombre de la tabla
    public static final String TABLE_NAME = "user";
    //Columnas
    public static final String COLUMN_ID = BaseColumns._ID;
    public static final String TU_C2_ROL = "rol";
    public static final String TU_C3_USERNAME = "username";
    public static final String TU_C4_PASSWORD = "password";
    public static final String TU_C5_PWD_SALT = "salt";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = COLUMN_ID)
    public int idU;

    @ColumnInfo(name = TU_C2_ROL)
    private String rol;

    @ColumnInfo(name = TU_C3_USERNAME)
    private String username;

    @ColumnInfo(name = TU_C4_PASSWORD)
    private String password;

    @ColumnInfo(name = TU_C5_PWD_SALT)
    private String salt;

    //Constructor
    public User(int idU, String rol, String username, String password, String salt) {
        this.idU = idU;
        this.rol = rol;
        this.username = username;
        this.password = password;
        this.salt = salt;
    }

    public User() {

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