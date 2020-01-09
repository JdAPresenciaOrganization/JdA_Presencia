package com.example.jdapresencia.database;

public class DBDesign {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private DBDesign(){}

    public static class DBEntry {
        /* Inner class that defines the table contents */
        public static final String TABLE_USER = "user";
        public static final String TU_C1_ID = "_id";
        public static final String TU_C2_ROL = "rol";
        public static final String TU_C3_USERNAME = "username";
        public static final String TU_C4_PASSWORD = "password";
        public static final String TU_C5_PWD_SALT = "salt";

        public static final String TABLE_REGISTRO = "registro";
        public static final String TR_C1_ID = "_id";
        public static final String TR_C2_FECHA = "fecha";
        public static final String TR_C3_HORA_ENTRADA = "hora_entrada";
        public static final String TR_C4_HORA_SALIDA = "hora_salida";
        public static final String TR_C5_HORAS_DIA = "horas_dia";
        public static final String TR_C6_ID_TRABAJADOR = "id_trabajador";
    }

    public static final String SQL_CRATE_USER_ENTRIES =
            "CREATE TABLE " + DBEntry.TABLE_USER + " (" +
                    DBEntry.TU_C1_ID + " INTEGER primary key autoincrement," +
                    DBEntry.TU_C2_ROL + " TEXT," +
                    DBEntry.TU_C3_USERNAME + " TEXT," +
                    DBEntry.TU_C4_PASSWORD + " TEXT,"+
                    DBEntry.TU_C5_PWD_SALT + " TEXT)";

    public static final String SQL_CRATE_REGISTRO_ENTRIES =
            "CREATE TABLE " + DBEntry.TABLE_REGISTRO + " (" +
                    DBEntry.TR_C1_ID + " INTEGER primary key autoincrement," +
                    DBEntry.TR_C2_FECHA + " TEXT," +
                    DBEntry.TR_C3_HORA_ENTRADA + " TEXT," +
                    DBEntry.TR_C4_HORA_SALIDA + " TEXT," +
                    DBEntry.TR_C5_HORAS_DIA + " TEXT," +
                    DBEntry.TR_C6_ID_TRABAJADOR + " INTEGER, " +
                    "FOREIGN KEY("+DBEntry.TR_C6_ID_TRABAJADOR+") REFERENCES " +
                    DBEntry.TABLE_USER + "("+DBEntry.TU_C1_ID+"));";

}
