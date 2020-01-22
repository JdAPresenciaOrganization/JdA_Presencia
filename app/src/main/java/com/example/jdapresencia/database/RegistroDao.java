package com.example.jdapresencia.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.jdapresencia.model.Registro;

import java.util.List;

@Dao
public interface RegistroDao {

    @Query("SELECT COUNT(*) FROM " + Registro.TABLE_NAME)
    int getRegistroCount();

    @Query("SELECT * FROM " + Registro.TABLE_NAME)
    List<Registro> getAllRegistroList();

    @Query("SELECT * FROM " + Registro.TABLE_NAME + " WHERE " + Registro.TR_C2_FECHA + " = :fechaHoy AND " +
            Registro.TR_C6_ID_TRABAJADOR + " = :idSession")
    Registro gerRegistroHoy(String fechaHoy, String idSession);

    @Query("SELECT * FROM " + Registro.TABLE_NAME + " WHERE " + Registro.TR_C6_ID_TRABAJADOR + " = :idSession")
    List<Registro> getRegistroByUserId(String idSession);

    @Insert
    void insertRegistro(Registro obj);
}
