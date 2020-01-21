package com.example.jdapresencia.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.jdapresencia.model.Registro;

import java.util.List;

@Dao
public interface RegistroDao {

    @Query("SELECT COUNT(*) FROM " + Registro.TABLE_NAME)
    int getUsersCount();

    @Query("SELECT * FROM " + Registro.TABLE_NAME)
    List<Registro> getAllUsersList();

    @Insert
    void insertUser(Registro obj);
}
