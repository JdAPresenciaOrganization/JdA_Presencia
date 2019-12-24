package com.example.jdapresencia.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "jdaPresencia.db";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DBDesign.SQL_CRATE_USER_ENTRIES);
        db.execSQL(DBDesign.SQL_CRATE_REGISTRO_ENTRIES);

        //default users
        ContentValues values = new ContentValues();
        //values.put(DBDesign.DBEntry.TU_C1_ID, 1);
        values.put(DBDesign.DBEntry.TU_C2_ROL, "admin");
        values.put(DBDesign.DBEntry.TU_C3_USERNAME, "admin");
        values.put(DBDesign.DBEntry.TU_C4_PASSWORD, "1234");
        db.insert(DBDesign.DBEntry.TABLE_USER, null, values);

        //values.put(DBDesign.DBEntry.TU_C1_ID, 2);
        values.put(DBDesign.DBEntry.TU_C2_ROL, "trabajador");
        values.put(DBDesign.DBEntry.TU_C3_USERNAME, "worker");
        values.put(DBDesign.DBEntry.TU_C4_PASSWORD, "1234");
        db.insert(DBDesign.DBEntry.TABLE_USER, null, values);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}


