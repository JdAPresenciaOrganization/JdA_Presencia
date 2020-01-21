package com.example.jdapresencia.model;

import android.provider.BaseColumns;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = Registro.TABLE_NAME)
public class Registro {

    //Nombre de la tabla
    public static final String TABLE_NAME = "registro";
    //Columnas
    public static final String COLUMN_ID = BaseColumns._ID;
    public static final String TR_C2_FECHA = "fecha";
    public static final String TR_C3_HORA_ENTRADA = "hora_entrada";
    public static final String TR_C4_HORA_SALIDA = "hora_salida";
    public static final String TR_C5_HORAS_DIA = "horas_dia";
    public static final String TR_C6_ID_TRABAJADOR = "id_trabajador";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = COLUMN_ID)
    int idR;

    @ColumnInfo(name = TR_C2_FECHA)
    String fecha;

    @ColumnInfo(name = TR_C3_HORA_ENTRADA)
    String horaEntrada;

    @ColumnInfo(name = TR_C4_HORA_SALIDA)
    String horaSalida;

    @ColumnInfo(name = TR_C5_HORAS_DIA)
    String horasDia;

    @ColumnInfo(name = TR_C6_ID_TRABAJADOR)
    int id_trabajador;


    public Registro(int idR, String fecha, String horaEntrada, String horaSalida, String horasDia, int id_trabajador) {
        this.idR = idR;
        this.fecha = fecha;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
        this.horasDia = horasDia;
        this.id_trabajador = id_trabajador;
    }

    public Registro() {

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
