package com.example.jdapresencia.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.jdapresencia.MVVMRepository;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "jdaPresencia.db";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 11);
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
        try {
            values.put(DBDesign.DBEntry.TU_C4_PASSWORD, MVVMRepository.encrypt("1234"));
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        db.insert(DBDesign.DBEntry.TABLE_USER, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}


