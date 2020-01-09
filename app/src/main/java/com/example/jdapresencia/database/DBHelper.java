package com.example.jdapresencia.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.jdapresencia.PasswordUtils;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "jdaPresencia.db";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DBDesign.SQL_CRATE_USER_ENTRIES);
        db.execSQL(DBDesign.SQL_CRATE_REGISTRO_ENTRIES);

        //default users
        ContentValues values = new ContentValues();
        String salt = PasswordUtils.getSalt(30);
        //values.put(DBDesign.DBEntry.TU_C1_ID, 1);
        values.put(DBDesign.DBEntry.TU_C2_ROL, "admin");
        values.put(DBDesign.DBEntry.TU_C3_USERNAME, "admin");
        values.put(DBDesign.DBEntry.TU_C4_PASSWORD, PasswordUtils.generateSecurePassword("1234", salt));
        values.put(DBDesign.DBEntry.TU_C5_PWD_SALT, salt);
        db.insert(DBDesign.DBEntry.TABLE_USER, null, values);

        //values.put(DBDesign.DBEntry.TU_C1_ID, 2);
        String salt2 = PasswordUtils.getSalt(30);
        values.put(DBDesign.DBEntry.TU_C2_ROL, "trabajador");
        values.put(DBDesign.DBEntry.TU_C3_USERNAME, "worker");
        values.put(DBDesign.DBEntry.TU_C4_PASSWORD, PasswordUtils.generateSecurePassword("test", salt2));
        values.put(DBDesign.DBEntry.TU_C5_PWD_SALT, salt2);
        db.insert(DBDesign.DBEntry.TABLE_USER, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}


