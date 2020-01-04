package com.example.jdapresencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.jdapresencia.database.DBDesign;
import com.example.jdapresencia.database.DBHelper;
import com.example.jdapresencia.model.Registro;
import com.example.jdapresencia.model.User;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class MVVMRepository {

    private static Context context;
    private static SQLiteDatabase db;
    //Singleton
    private static MVVMRepository srepository;

    private MVVMRepository(Context context){
        this.context = context;
        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    public static MVVMRepository get(Context context){
        if (srepository == null){
            srepository = new MVVMRepository(context);
        }
        return srepository;
    }

    /**
     * Login Activity
     * @param user
     * @param pass
     */
    public static void checkLogin(String user, String pass) {
        if (TextUtils.isEmpty(user) || TextUtils.isEmpty(pass)) {
            Toast.makeText(context, "Enter username and password",Toast.LENGTH_SHORT).show();
        } else {
            DBHelper dbHelper = new DBHelper(context);
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("Select * from user where username=? and password=?", new String[]{user, pass});

            if (cursor.moveToFirst()){
                do {
                    // Passing values
                    String column1 = cursor.getString(0);
                    String column2 = cursor.getString(1);
                    String column3 = cursor.getString(2);
                    String column4 = cursor.getString(3);

                    //Se pasa el id y el rol de usuario
                    LoginActivity.loginSuccess(column1, column2);
                } while(cursor.moveToNext());
            } else {
                Toast.makeText(context, "User not found", Toast.LENGTH_SHORT).show();
            }
            cursor.close();
            db.close();
        }
    }

    /******** CHECK IN/OUT METHODS ********/

    public static void userCheckIn(String idSession) {

        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from registro where fecha=? and id_trabajador=?",
                new String[]{getDiaActual(fechaActualDiaHora()), idSession});

        if (cursor.moveToFirst()){
            do {
                // Passing values
                String fecha = cursor.getString(1);
                String hEntrada = cursor.getString(2);
                String hSalida = cursor.getString(3);
                String hTotales = cursor.getString(4);
                String idU = cursor.getString(5);

                Toast.makeText(context, "¡Ya has hecho check in hoy!",Toast.LENGTH_SHORT).show();
            } while(cursor.moveToNext());
        } else {
            ContentValues insertValues = new ContentValues();
            insertValues.put(DBDesign.DBEntry.TR_C2_FECHA, getDiaActual(fechaActualDiaHora()));
            insertValues.put(DBDesign.DBEntry.TR_C3_HORA_ENTRADA, getHoraActual(fechaActualDiaHora()));
            insertValues.put(DBDesign.DBEntry.TR_C4_HORA_SALIDA, "");
            insertValues.put(DBDesign.DBEntry.TR_C5_HORAS_DIA, "");
            insertValues.put(DBDesign.DBEntry.TR_C6_ID_TRABAJADOR, idSession);
            //Insert the new row, returning the primary key value of the new row
            long rowInserted = db.insert(DBDesign.DBEntry.TABLE_REGISTRO, null, insertValues);

            if(rowInserted != -1) {
                Toast.makeText(context, "Check in done",Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(context, "Something wrong", Toast.LENGTH_SHORT).show();
            }
        }
        cursor.close();
        db.close();
    }

    public static void userCheckOut(String idSession) {

        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from registro where fecha=? and id_trabajador=?",
                new String[]{getDiaActual(fechaActualDiaHora()), idSession});

        if (cursor.moveToFirst()){
            do {
                // Passing values
                String idR = cursor.getString(0);
                String fecha = cursor.getString(1);
                String hEntrada = cursor.getString(2);
                String hSalida = cursor.getString(3);
                String hTotales = cursor.getString(4);
                String idU = cursor.getString(5);

                //Update con la fecha de salida
                if (hSalida.equals("")) {
                    ContentValues updateValues = new ContentValues();
                    updateValues.put(DBDesign.DBEntry.TR_C4_HORA_SALIDA, getHoraActual(fechaActualDiaHora()));
                    db.update(DBDesign.DBEntry.TABLE_REGISTRO, updateValues, "_id="+idR, null);

                    Toast.makeText(context, "Check out done",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Registro del día ya finalizado",Toast.LENGTH_SHORT).show();
                }

            } while(cursor.moveToNext());
        } else {
            Toast.makeText(context, "¡Aún no has hecho check in hoy!",Toast.LENGTH_SHORT).show();
        }
        cursor.close();
        db.close();
    }
    /******** FIN CHECK IN/OUT METHODS ********/


    /******** DATE METHODS ********/

    /**
     * Devuelve la fecha en formato dd/MM/yyyy HH:mm:ss
     * @return
     */
    public static String fechaActualDiaHora(){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+1"));
        Date date = new Date();

        return dateFormat.format(date);
    }

    public static String spanishDate(){
        String output = ZonedDateTime.now(ZoneId.of("Europe/Madrid"))
                        .format(DateTimeFormatter.ofLocalizedDate (FormatStyle.FULL)
                                .withLocale (new Locale("es" , "ES"))
                        );
        return output;
    }

    /**
     * Devuelve la fecha actual dd/MM/yyyy antes del espacio (sin la hora)
     * @param fechaEnteraActual
     * @return
     */
    public static String getDiaActual(String fechaEnteraActual){
        String[] fechaActual = fechaEnteraActual.split(" ");
        String dia = fechaActual[0];

        return dia;
    }

    /**
     * Devuelve la hora actual HH:mm:ss después del espacio (sin el día)
     * @param fechaEnteraActual
     * @return
     */
    public static String getHoraActual(String fechaEnteraActual){
        String[] fechaActual = fechaEnteraActual.split(" ");
        String hora = fechaActual[1];

        return hora;
    }

    public static String totalDayHours(String entrada, String salida) throws ParseException {
        if (salida.length()==0) {
            return "Not closed";
        }

        //Se pasan los 2 strings a formato date y se restan, develve milisegundos
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Date date1 = format.parse(entrada);
        Date date2 = format.parse(salida);
        long difference = date2.getTime() - date1.getTime();

        //Se pasan los milisegundos a HH:mm:ss
        return String.format("%1$tH:%1$tM:%1$tS", difference);
    }
    /******** FIN DATE METHODS ********/

    /******** BUSCADOR USER METHODS ********/

    /**
     * Se muestran todos los usuarios si no se ha introducido nada,
     * de lo contrario, buscará el trabajador por el username
     * @param username
     * @return
     */
    public static ArrayList<User> getUsersByUsername(String username) {
        ArrayList<User> listUser = new ArrayList<>();

        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        if (!username.equals("")) {
            Cursor cursor = db.query(
                    DBDesign.DBEntry.TABLE_USER,
                    null,
                    DBDesign.DBEntry.TU_C3_USERNAME + "=?",
                    new String[]{username},
                    null,
                    null,
                    null);

            while (cursor.moveToNext()) {
                User user = new User(
                        cursor.getInt(cursor.getColumnIndex(DBDesign.DBEntry.TU_C1_ID)),
                        cursor.getString(cursor.getColumnIndex(DBDesign.DBEntry.TU_C2_ROL)),
                        cursor.getString(cursor.getColumnIndex(DBDesign.DBEntry.TU_C3_USERNAME)),
                        cursor.getString(cursor.getColumnIndex(DBDesign.DBEntry.TU_C4_PASSWORD))
                );
                listUser.add(user);
            }
            cursor.close();

        } else {
            Cursor cursor = db.query(
                    DBDesign.DBEntry.TABLE_USER,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null);

            while (cursor.moveToNext()) {
                User user = new User(
                        cursor.getInt(cursor.getColumnIndex(DBDesign.DBEntry.TU_C1_ID)),
                        cursor.getString(cursor.getColumnIndex(DBDesign.DBEntry.TU_C2_ROL)),
                        cursor.getString(cursor.getColumnIndex(DBDesign.DBEntry.TU_C3_USERNAME)),
                        cursor.getString(cursor.getColumnIndex(DBDesign.DBEntry.TU_C4_PASSWORD))
                );
                listUser.add(user);
            }
            cursor.close();
        }

        return listUser;
    }
    /******** FIN BUSCADOR USER METHODS ********/

    /******** MY REGISTERS METHODS ********/

    /**
     * Listado de registros donde el id es el del usuario en sesion,
     * se muestran los registros en orden descendente
     * @param idSession
     * @return
     */
    public static ArrayList<Registro> getListRegistros(String idSession) {
        ArrayList<Registro> listRegistros = new ArrayList<>();

        Cursor cursor = db.query(
                DBDesign.DBEntry.TABLE_REGISTRO,
                null,
                DBDesign.DBEntry.TR_C6_ID_TRABAJADOR + "=?",
                new String[]{idSession},
                null,
                null,
                DBDesign.DBEntry.TR_C1_ID + " DESC");

        while (cursor.moveToNext()) {
            Registro registro = new Registro(
                    cursor.getInt(cursor.getColumnIndex(DBDesign.DBEntry.TR_C1_ID)),
                    cursor.getString(cursor.getColumnIndex(DBDesign.DBEntry.TR_C2_FECHA)),
                    cursor.getString(cursor.getColumnIndex(DBDesign.DBEntry.TR_C3_HORA_ENTRADA)),
                    cursor.getString(cursor.getColumnIndex(DBDesign.DBEntry.TR_C4_HORA_SALIDA)),
                    cursor.getString(cursor.getColumnIndex(DBDesign.DBEntry.TR_C5_HORAS_DIA)),
                    cursor.getInt(cursor.getColumnIndex(DBDesign.DBEntry.TR_C6_ID_TRABAJADOR))
            );
            listRegistros.add(registro);
        }
        cursor.close();
        return listRegistros;
    }

    /******** FIN MY REGISTERS METHODS ********/

    /********* GESTIONAR TRABAJADORES METHODS *********/

    /**
     * Se añade el nuevo trabajador si el username no esta ya registrado
     * @param user
     * @param pass
     */
    public static void addNewWorker(String user, String pass) {
        if (TextUtils.isEmpty(user) || TextUtils.isEmpty(pass)) {
            Toast.makeText(context, "Enter username and password", Toast.LENGTH_SHORT).show();
        } else {
            DBHelper dbHelper = new DBHelper(context);
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("Select * from user where username=?", new String[]{user});

            if (cursor.moveToFirst()){
                do {
                    // Passing values
                    String column1 = cursor.getString(0);
                    String column2 = cursor.getString(1);
                    String column3 = cursor.getString(2);
                    String column4 = cursor.getString(3);

                    Toast.makeText(context, "User " + column3 + " already exists", Toast.LENGTH_SHORT).show();
                } while(cursor.moveToNext());
            } else {
                ContentValues values = new ContentValues();
                //values.put(DBDesign.DBEntry.TU_C1_ID, 1);
                values.put(DBDesign.DBEntry.TU_C2_ROL, "trabajador");
                values.put(DBDesign.DBEntry.TU_C3_USERNAME, user);
                values.put(DBDesign.DBEntry.TU_C4_PASSWORD, pass);
                db.insert(DBDesign.DBEntry.TABLE_USER, null, values);

                Toast.makeText(context, "User register done", Toast.LENGTH_SHORT).show();
            }
            cursor.close();
            db.close();
        }
    }

    /********* FIN GESTIONAR TRABAJADORES METHODS *********/
}
