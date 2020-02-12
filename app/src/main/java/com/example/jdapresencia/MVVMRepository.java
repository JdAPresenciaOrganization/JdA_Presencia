package com.example.jdapresencia;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jdapresencia.database.AppDatabase;
import com.example.jdapresencia.model.Mensaje;
import com.example.jdapresencia.model.Registro;
import com.example.jdapresencia.model.User;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    //private static SQLiteDatabase db;
    private static AppDatabase dbb;
    //Singleton
    private static MVVMRepository srepository;

    //PostgreSQL Server
    protected static final String driver = "org.postgresql.Driver";
    protected static final String url = "jdbc:postgresql://192.168.0.22:5432/grace";
    protected static final String dbUser = "grace";
    protected static final String dbPass = "hooper";

    private MVVMRepository(Context context){
        this.context = context;
        //DBHelper dbHelper = new DBHelper(context);
        //Permite escribir y leer
        //db = dbHelper.getWritableDatabase();
        //Room SQLite
        dbb = AppDatabase.getFileDatabase(context);
    }
    public static MVVMRepository get(Context context){
        if (srepository == null){
            srepository = new MVVMRepository(context);
        }
        return srepository;
    }

    /*
    public static void closeDatabase(){
        db.close();
    }
    */

    public static void closeDatabaseInstance(){
        AppDatabase.destroyInstance();
    }

    /**
     * Se crea un admin y trabajador por defecto
     */
    public static void initUserTable() {
        int cont = dbb.getUserDao().getUsersCount();

        if (cont == 0) {
            User user1 = new User();
            String salt = PasswordUtils.getSalt(30);

            user1.setRol("admin");
            user1.setUsername("admin");
            user1.setPassword(PasswordUtils.generateSecurePassword("1234", salt));
            user1.setSalt(salt);

            dbb.getUserDao().insertUser(user1);

            User user2 = new User();
            String salt2 = PasswordUtils.getSalt(30);

            user2.setRol("trabajador");
            user2.setUsername("worker");
            user2.setPassword(PasswordUtils.generateSecurePassword("test", salt2));
            user2.setSalt(salt2);

            dbb.getUserDao().insertUser(user2);
        }
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
            User userCheck = dbb.getUserDao().getUserByUsername(user);

            if (userCheck != null) {
                // Passing values
                int uid = userCheck.getIdU();
                String rol = userCheck.getRol();
                String password = userCheck.getPassword();
                String pwd_salt = userCheck.getSalt();

                //Si la contraseña que se ingresa es igual a la contraseña desencriptada del usuario es que los datos son correctos
                boolean passwordMatch = PasswordUtils.verifyUserPassword(pass, password, pwd_salt);
                if (passwordMatch) {
                    LoginActivity.loginSuccess(Integer.toString(uid), rol);
                } else {
                    Toast.makeText(context, "Incorrect password", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, "User not found", Toast.LENGTH_SHORT).show();
            }

            /*
            Cursor cursor = db.rawQuery("Select * from user where username=?", new String[]{user});

            if (cursor.moveToFirst()){
                do {
                    // Passing values
                    String uid = cursor.getString(0);
                    String rol = cursor.getString(1);
                    String username = cursor.getString(2);
                    String password = cursor.getString(3);
                    String pwd_salt = cursor.getString(4);

                    //Si la contraseña que se ingresa es igual a la contraseña desencriptada del usuario es que los datos son correctos
                    boolean passwordMatch = PasswordUtils.verifyUserPassword(pass, password, pwd_salt);
                    if (passwordMatch) {
                        LoginActivity.loginSuccess(uid, rol);
                    } else {
                        Toast.makeText(context, "Incorrect password", Toast.LENGTH_SHORT).show();
                    }
                } while(cursor.moveToNext());
            } else {
                Toast.makeText(context, "User not found", Toast.LENGTH_SHORT).show();
            }
            cursor.close();
            */
        }
    }

    /******** CHECK IN/OUT METHODS ********/

    public static void userCheckIn(String idSession) {

        Registro registroCheck =
                dbb.getRegistroDao().gerRegistroHoy(getDiaActual(fechaActualDiaHora()), idSession);

        if (registroCheck != null) {
            Toast.makeText(context, "¡Ya has hecho check in hoy!",Toast.LENGTH_SHORT).show();
        } else {
            Registro registro1 = new Registro();

            registro1.setFecha(getDiaActual(fechaActualDiaHora()));
            registro1.setHoraEntrada(getHoraActual(fechaActualDiaHora()));
            registro1.setHoraSalida("");
            registro1.setHorasDia("");
            registro1.setId_trabajador(Integer.parseInt(idSession));

            dbb.getRegistroDao().insertRegistro(registro1);

            Toast.makeText(context, "Check in done",Toast.LENGTH_SHORT).show();
        }
        /*
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
        */
    }

    public static void userCheckOut(String idSession) {

        Registro registroCheck =
                dbb.getRegistroDao().gerRegistroHoy(getDiaActual(fechaActualDiaHora()), idSession);

        if (registroCheck != null) {
            // Passing values
            String hSalida = registroCheck.getHoraSalida();

            //Update con la salida
            if (hSalida.equals("")) {
                registroCheck.setHoraSalida(getHoraActual(fechaActualDiaHora()));
                dbb.getRegistroDao().updateRegistro(registroCheck);
                Toast.makeText(context, "Check out done",Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Registro del día ya finalizado", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "¡Aún no has hecho check in hoy!", Toast.LENGTH_SHORT).show();
        }

        /*
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
        */
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

        if (!username.equals("")) {
            User userCheck = dbb.getUserDao().getUserByUsername(username);

            if (userCheck != null) {
                listUser.add(userCheck);
            }
        } else {
            listUser = (ArrayList<User>) dbb.getUserDao().getAllUsersList();
        }
        /*
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
                        cursor.getString(cursor.getColumnIndex(DBDesign.DBEntry.TU_C4_PASSWORD)),
                        cursor.getString(cursor.getColumnIndex(DBDesign.DBEntry.TU_C5_PWD_SALT))
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
                        cursor.getString(cursor.getColumnIndex(DBDesign.DBEntry.TU_C4_PASSWORD)),
                        cursor.getString(cursor.getColumnIndex(DBDesign.DBEntry.TU_C5_PWD_SALT))
                );
                listUser.add(user);
            }
            cursor.close();
        }
        */

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

        listRegistros = (ArrayList<Registro>) dbb.getRegistroDao().getRegistroByUserId(idSession);

        /*
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
        */
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
            User userCheck = dbb.getUserDao().getUserByUsername(user);

            if (userCheck != null) {
                Toast.makeText(context, "User " + userCheck.getUsername() + " already exists", Toast.LENGTH_SHORT).show();
            } else {
                User user1 = new User();
                String salt = PasswordUtils.getSalt(30);

                user1.setRol("trabajador");
                user1.setUsername(user);
                user1.setPassword(PasswordUtils.generateSecurePassword(pass, salt));
                user1.setSalt(salt);

                dbb.getUserDao().insertUser(user1);

                Toast.makeText(context, "User register done", Toast.LENGTH_SHORT).show();
            }

            /*
            Cursor cursor = db.rawQuery("Select * from user where username=?", new String[]{user});

            if (cursor.moveToFirst()){
                do {
                    // Passing values
                    String uid = cursor.getString(0);
                    String rol = cursor.getString(1);
                    String username = cursor.getString(2);
                    String password = cursor.getString(3);

                    Toast.makeText(context, "User " + username + " already exists", Toast.LENGTH_SHORT).show();
                } while(cursor.moveToNext());
            } else {
                ContentValues values = new ContentValues();
                String salt = PasswordUtils.getSalt(30);
                //values.put(DBDesign.DBEntry.TU_C1_ID, 1);
                values.put(DBDesign.DBEntry.TU_C2_ROL, "trabajador");
                values.put(DBDesign.DBEntry.TU_C3_USERNAME, user);
                values.put(DBDesign.DBEntry.TU_C4_PASSWORD, PasswordUtils.generateSecurePassword(pass, salt));
                values.put(DBDesign.DBEntry.TU_C5_PWD_SALT, salt);
                db.insert(DBDesign.DBEntry.TABLE_USER, null, values);

                Toast.makeText(context, "User register done", Toast.LENGTH_SHORT).show();
            }
            cursor.close();
            */
        }
    }

    /**
     * Se puede actualizar el nombre, la contraseña o el rol del trabajador
     * @param username
     * @param newUsername
     * @param newPwd
     * @param userRol
     */
    public static void updateWorker(String username, String newUsername, String newPwd, String userRol) {
        if (TextUtils.isEmpty(username) && TextUtils.isEmpty(newUsername) && TextUtils.isEmpty(newPwd)) {
            Toast.makeText(context, "Form is empty", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(username)) {
            Toast.makeText(context, "Username is required", Toast.LENGTH_SHORT).show();
        } else {
            //Se mira si existe el usuario introducido
            User userCheck = dbb.getUserDao().getUserByUsername(username);

            if (userCheck != null) {
                // Passing values
                int uid = userCheck.getIdU();
                String rol = userCheck.getRol();
                String userName = userCheck.getUsername();
                String password = userCheck.getPassword();

                //Si los campos de nuevo nombre y nueva contraseña estan vacios, solo se actualiza el rol
                if (TextUtils.isEmpty(newUsername) && TextUtils.isEmpty(newPwd)) {
                    dbb.getUserDao().updateUserRol(userRol.toLowerCase(), userName);
                    Toast.makeText(context, "Rol actualizado", Toast.LENGTH_SHORT).show();
                //Si el campo de nuevo nombre esta vacio, solo se actualiza la contraseña y el rol
                } else if (TextUtils.isEmpty(newUsername)) {
                    String salt = PasswordUtils.getSalt(30);
                    dbb.getUserDao().updateUserPassword(userRol.toLowerCase(),
                            PasswordUtils.generateSecurePassword(newPwd, salt), salt, userName);
                    Toast.makeText(context, "Contraseña actualizada", Toast.LENGTH_SHORT).show();
                //Si el campo de nuevo contraseña esta vacio, solo se actualiza el nombre y el rol
                } else if (TextUtils.isEmpty(newPwd)) {
                    //Se mira si ya existe el nuevo nombre
                    User userExists = dbb.getUserDao().getUserByUsername(newUsername);

                    if (userExists != null) {
                        Toast.makeText(context, "User " + newUsername + " already exists", Toast.LENGTH_SHORT).show();
                    } else {
                        dbb.getUserDao().updateUsername(userRol.toLowerCase(), newUsername, userName);
                        Toast.makeText(context, "Username actualizado", Toast.LENGTH_SHORT).show();
                    }
                //Si se rellenan todos los campos, se actualiza con todos los campos
                } else {
                    //Se mira si ya existe el nuevo nombre
                    User userExists = dbb.getUserDao().getUserByUsername(newUsername);

                    if (userExists != null) {
                        Toast.makeText(context, "User " + newUsername + " already exists", Toast.LENGTH_SHORT).show();
                    } else {
                        String salt = PasswordUtils.getSalt(30);
                        userCheck.setRol(userRol.toLowerCase());
                        userCheck.setUsername(newUsername);
                        userCheck.setPassword(PasswordUtils.generateSecurePassword(newPwd, salt));
                        userCheck.setSalt(salt);

                        dbb.getUserDao().updateUser(userCheck);
                        Toast.makeText(context, "Campos actualizados correctamente", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                Toast.makeText(context, "User not found", Toast.LENGTH_SHORT).show();
            }
            /*
            Cursor cursor = db.rawQuery("Select * from user where username=?", new String[]{username});

            if (cursor.moveToFirst()){
                do {
                    // Passing values
                    String uid = cursor.getString(0);
                    String rol = cursor.getString(1);
                    String userName = cursor.getString(2);
                    String password = cursor.getString(3);

                    // Update
                    ContentValues updateValues = new ContentValues();

                    //Si los campos de nuevo nombre y nueva contraseña estan vacios, solo se actualiza el rol
                    if (TextUtils.isEmpty(newUsername) && TextUtils.isEmpty(newPwd)) {
                        updateValues.put(DBDesign.DBEntry.TU_C2_ROL, userRol.toLowerCase());
                        db.update(DBDesign.DBEntry.TABLE_USER, updateValues, "_id="+uid, null);

                        Toast.makeText(context, "Rol actualizado", Toast.LENGTH_SHORT).show();
                    //Si el campo de nuevo nombre esta vacio, solo se actualiza la contraseña y el rol
                    } else if (TextUtils.isEmpty(newUsername)) {
                        String salt = PasswordUtils.getSalt(30);
                        updateValues.put(DBDesign.DBEntry.TU_C2_ROL, userRol.toLowerCase());
                        updateValues.put(DBDesign.DBEntry.TU_C4_PASSWORD, PasswordUtils.generateSecurePassword(newPwd, salt));
                        updateValues.put(DBDesign.DBEntry.TU_C5_PWD_SALT, salt);
                        db.update(DBDesign.DBEntry.TABLE_USER, updateValues, "_id="+uid, null);

                        Toast.makeText(context, "Contraseña actualizada", Toast.LENGTH_SHORT).show();
                    //Si el campo de nuevo contraseña esta vacio, solo se actualiza el nombre y el rol
                    } else if (TextUtils.isEmpty(newPwd)) {

                        //Se mira si ya existe el nuevo nombre
                        Cursor cursor2 = db.rawQuery("Select * from user where username=?", new String[]{newUsername});
                        if (cursor2.moveToFirst()){
                            do {
                                // Passing values
                                String uid2 = cursor2.getString(0);
                                String rol2 = cursor2.getString(1);
                                String userName2 = cursor2.getString(2);
                                String password2 = cursor2.getString(3);

                                if (userName2.equals(newUsername)) {
                                    Toast.makeText(context, "User " + userName + " already exists", Toast.LENGTH_SHORT).show();
                                }
                            } while(cursor.moveToNext());
                        } else {
                            updateValues.put(DBDesign.DBEntry.TU_C2_ROL, userRol.toLowerCase());
                            updateValues.put(DBDesign.DBEntry.TU_C3_USERNAME, newUsername);
                            db.update(DBDesign.DBEntry.TABLE_USER, updateValues, "_id="+uid, null);

                            Toast.makeText(context, "Username actualizado", Toast.LENGTH_SHORT).show();
                        }
                        cursor2.close();
                    //Si se rellenan todos los campos, se actualiza con todos los campos
                    } else {
                        String salt = PasswordUtils.getSalt(30);
                        updateValues.put(DBDesign.DBEntry.TU_C2_ROL, userRol.toLowerCase());
                        updateValues.put(DBDesign.DBEntry.TU_C3_USERNAME, newUsername);
                        updateValues.put(DBDesign.DBEntry.TU_C4_PASSWORD, PasswordUtils.generateSecurePassword(newPwd, salt));
                        updateValues.put(DBDesign.DBEntry.TU_C5_PWD_SALT, salt);
                        db.update(DBDesign.DBEntry.TABLE_USER, updateValues, "_id="+uid, null);

                        Toast.makeText(context, "Campos actualizados correctamente", Toast.LENGTH_SHORT).show();
                    }
                } while(cursor.moveToNext());
            } else {
                Toast.makeText(context, "User not found", Toast.LENGTH_SHORT).show();
            }
            cursor.close();
            */
        }
    }

    /**
     * Delete user
     * @param username
     */
    public static void deleteWorker(String username) {
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(context, "Enter username to delete", Toast.LENGTH_SHORT).show();
        } else {
            User userCheck = dbb.getUserDao().getUserByUsername(username);

            if (userCheck != null) {
                dbb.getUserDao().deleteByUsername(username);
                Toast.makeText(context, "User " + userCheck.getUsername() + " deleted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "User not found", Toast.LENGTH_SHORT).show();
            }

            /*
            Cursor cursor = db.rawQuery("Select * from user where username=?", new String[]{username});

            if (cursor.moveToFirst()) {
                do {
                    // Passing values
                    String uid = cursor.getString(0);
                    String rol = cursor.getString(1);
                    String user = cursor.getString(2);
                    String pwd = cursor.getString(3);

                    db.delete(DBDesign.DBEntry.TABLE_USER, "_id=?", new String[] {uid});

                    Toast.makeText(context, "User " + user + " deleted", Toast.LENGTH_SHORT).show();
                } while (cursor.moveToNext());
            } else {
                Toast.makeText(context, "User not found", Toast.LENGTH_SHORT).show();
            }
            cursor.close();
            */
        }
    }

    /********* FIN GESTIONAR TRABAJADORES METHODS *********/

    /***************** FIREBASE METHODS *******************/

    /**
     * Se consigue el usuario con el id de la sesión y se escribe en firebase
     * @param msg
     * @param idSession
     */
    public static void WriteMsgFirebase(EditText msg, String idSession) {
        /*
        Cursor cursor = db.rawQuery("Select username from user where _id=?", new String[]{idSession});

        String username = "";

        if (cursor.moveToFirst()){
            do {
                // Passing values
                username = cursor.getString(0);
            } while(cursor.moveToNext());
        }
        cursor.close();
        */

        User userCheck = dbb.getUserDao().getUserById(Integer.parseInt(idSession));

        // Read the input field and push a new instance of ChatMessage to the Firebase database
        FirebaseDatabase.getInstance()
                .getReference()
                .child("JdAP")
                .child("mensajes")
                .push()
                .setValue(new Mensaje(userCheck.getUsername(), "admin", msg.getText().toString(), userCheck.getIdU())
                );

        // Clear the input
        msg.setText("");
    }

    /**************** FIN FIREBASE METHODS ****************/

    /***************** POSTGRESQL SERVER ******************/

    //Variable con la conexión al servidor
    public static Connection conn = null;

    /**
     * Devuelve la conexión al servidor
     * @return Connection
     */
    public static Connection getConnection(){
        try {
            Class.forName(MVVMRepository.driver);
            conn = DriverManager.getConnection(MVVMRepository.url, MVVMRepository.dbUser, MVVMRepository.dbPass);

            if (conn == null) {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void closeServerDB(){
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //Clase AsyncTask que comprueba el login inicial
    public static class CheckLoginTask extends AsyncTask<Void, Void, Boolean> {

        String username, pass;

        public CheckLoginTask(String username, String pass){
            this.username = username;
            this.pass = pass;
        }
        @Override
        protected Boolean doInBackground(Void... voids) {

            PreparedStatement ps = null;
            ResultSet rs = null;

            //Toast result
            Handler handler = new Handler(context.getMainLooper());

            try {
                //Llamada a la conexión
                conn = getConnection();
                if (conn == null) {
                    return false;
                } else {
                    /* Se mira si existen usuarios en la tabla de usuarios,
                    si no existe ningun usuario se inserta por defecto un
                    administrador y un trabajador */
                    String sql = "select count(*) from muser;";
                    ps = conn.prepareStatement(sql);
                    rs = ps.executeQuery();
                    int count = 0;

                    while(rs.next()) {
                        count = rs.getInt(1);
                    }

                    if (count == 0) {
                        String sql2 = "insert into muser (rol,username,password,salt)"
                                + "VALUES(?,?,?,?);";
                        ps = conn.prepareStatement(sql2);

                        String salt = PasswordUtils.getSalt(30);

                        ps.setString(1, "admin");
                        ps.setString(2, "admin");
                        ps.setString(3, PasswordUtils.generateSecurePassword("1234", salt));
                        ps.setString(4, salt);

                        if(ps.executeUpdate() == 1) {
                            Log.i("CONN", "insert admin ok");
                        } else {
                            Log.i("CONN", "insert admin KO");
                        }

                        String sql3 = "insert into muser (rol,username,password,salt)"
                                + "VALUES(?,?,?,?);";
                        ps = conn.prepareStatement(sql3);

                        String salt2 = PasswordUtils.getSalt(30);

                        ps.setString(1, "trabajador");
                        ps.setString(2, "worker");
                        ps.setString(3, PasswordUtils.generateSecurePassword("test", salt2));
                        ps.setString(4, salt2);

                        if(ps.executeUpdate() == 1) {
                            Log.i("CONN", "insert worker ok");
                        } else {
                            Log.i("CONN", "insert worker KO");
                        }
                    }

                    /**
                     * ¡¡¡IMPORTANTE!!!
                     * Se tienen 2 variables, 1 que es el contador de la tabla user
                     * de PostgreSQL y otra de SQLite.
                     *
                     * Los datos se guardan en las dos BBDD, es decir que si el contador
                     * entre las dos no coincide, quiere decir que no se ha podido conectar
                     * al servidor, por lo tanto habrá que sincronizar las dos BBDD.
                     *
                     * Se guarda en una lista todos los datos de los usuarios de SQLite Room
                     * y se hace un bucle for inicializando la i con el contador de PostgreSQL
                     * para posteriormente guardar en el servidor los datos restantes y así
                     * poder realizar la sincronización.
                     *
                     * Ejemplo: si en SQLite hay 5 usuarios y en PostgreSQL hay 3 usuarios,
                     * el bucle for recorre la lista de ROOM a partir del último usuario de
                     * PostgreSQL, por lo tanto se guardará en el servidor el 4 y el 5.
                     */

                    //Comprobar la cantidad de usuarios de SQLite
                    int SQLiteCont = dbb.getUserDao().getUsersCount();

                    //Si no coinciden se sincroniza PostgreSQL con los id restantes que le falten
                    if (SQLiteCont != count) {

                        ArrayList<User> listUser = new ArrayList<>();

                        listUser = (ArrayList<User>) dbb.getUserDao().getAllUsersList();

                        for (int i = count; i < listUser.size(); i++) {

                            String sql00 = "insert into muser (id,rol,username,password,salt)"
                                    + "VALUES(?,?,?,?,?);";
                            ps = conn.prepareStatement(sql00);

                            ps.setInt(1, listUser.get(i).getIdU());
                            ps.setString(2, listUser.get(i).getRol());
                            ps.setString(3, listUser.get(i).getUsername());
                            ps.setString(4, listUser.get(i).getPassword());
                            ps.setString(5, listUser.get(i).getSalt());

                            if(ps.executeUpdate() == 1) {
                                Log.i("CONN", "insert ok");
                            } else {
                                Log.i("CONN", "insert KO");
                            }
                        }
                    }

                    //Comprobación si usuario y contraseña de login son correctos
                    if (TextUtils.isEmpty(username) || TextUtils.isEmpty(pass)) {
                        handler.post( new Runnable(){
                            public void run(){
                                Toast.makeText(context, "Enter username and password", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        String consulta = "select * from muser where username = ? ";
                        ps = conn.prepareStatement(consulta);
                        ps.setString(1, username);
                        rs = ps.executeQuery();

                        if (rs.next()) {
                            do {
                                // Passing values
                                String uid = rs.getString(1);
                                String rol = rs.getString(2);
                                String username = rs.getString(3);
                                String password = rs.getString(4);
                                String pwd_salt = rs.getString(5);

                                //Si la contraseña que se ingresa es igual a la contraseña desencriptada del usuario es que los datos son correctos
                                boolean passwordMatch = PasswordUtils.verifyUserPassword(pass, password, pwd_salt);
                                if (passwordMatch) {
                                    LoginActivity.loginSuccess(uid, rol);
                                } else {
                                    handler.post( new Runnable(){
                                        public void run(){
                                            Toast.makeText(context, "Incorrect password", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            } while (rs.next());
                        } else {
                            handler.post( new Runnable(){
                                public void run(){
                                    Toast.makeText(context, "User not found", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }
            } catch (Exception e) {
                Log.e("ERROR Conexion:",e.getMessage());
                return false;
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }

            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            if(result) {
                Log.i("CONN", "conectado");
            } else {
                /***********************************************/
                /* Si ha fallado la conexión se llama a SQLite */
                /***********************************************/
                checkLogin(username, pass);
                Log.i("CONN", "no conectado");
            }
        }
    }

    /**
     * CHECK IN
     */
    public static class UserCheckInTask extends AsyncTask<Void, Void, Boolean> {

        String idsession;

        public UserCheckInTask(String idSession){
            this.idsession = idSession;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            PreparedStatement ps = null;
            ResultSet rs = null;

            //Toast result
            Handler handler = new Handler(context.getMainLooper());

            try {
                //Llamada a la conexión
                conn = getConnection();
                if (conn == null) {
                    return false;
                } else {
                    //Se comprueba si ya existe un check in en el dia actual con el id de session
                    String sql = "select * from mregistro where fecha = ? and uid = ?";
                    ps = conn.prepareStatement(sql);
                    ps.setString(1, getDiaActual(fechaActualDiaHora()));
                    ps.setInt(2, Integer.parseInt(idsession));
                    rs = ps.executeQuery();

                    if (rs.next()) {
                        do {

                        } while (rs.next());
                    } else {
                        String sql2 = "insert into mregistro (fecha,hEntrada,hSalida,hDia,uid)"
                                + "VALUES(?,?,?,?,?);";
                        ps = conn.prepareStatement(sql2);

                        ps.setString(1, getDiaActual(fechaActualDiaHora()));
                        ps.setString(2, getHoraActual(fechaActualDiaHora()));
                        ps.setString(3, "");
                        ps.setString(4, "");
                        ps.setInt(5, Integer.parseInt(idsession));

                        if(ps.executeUpdate() == 1) {

                        } else {
                            Log.i("CONN", "Something wrong");
                        }
                    }
                }

            } catch (Exception e) {
                Log.e("ERROR Conexion:",e.getMessage());
                return false;
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }

            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            if(result) {
                userCheckIn(idsession);
                Log.i("CONN", "conectado check in");
            }else {
                userCheckIn(idsession);
                Log.i("CONN", "no conectado check in");
            }
        }
    }

    /**
     * CHECK OUT
     */
    public static class UserCheckOutTask extends AsyncTask<Void, Void, Boolean> {

        String idsession;

        public UserCheckOutTask(String idSession){
            this.idsession = idSession;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            PreparedStatement ps = null;
            ResultSet rs = null;

            //Toast result
            Handler handler = new Handler(context.getMainLooper());

            try {
                //Llamada a la conexión
                conn = getConnection();
                if (conn == null) {
                    return false;
                } else {
                    //Se comprueba si existe un check in en el dia actual con el id de session
                    String sql = "select * from mregistro where fecha = ? and uid = ?";
                    ps = conn.prepareStatement(sql);
                    ps.setString(1, getDiaActual(fechaActualDiaHora()));
                    ps.setInt(2, Integer.parseInt(idsession));
                    rs = ps.executeQuery();

                    if (rs.next()) {
                        do {
                            // Passing values
                            String idR = rs.getString(1);
                            String fecha = rs.getString(2);
                            String hEntrada = rs.getString(3);
                            String hSalida = rs.getString(4);
                            String hTotales = rs.getString(5);
                            String idU = rs.getString(6);

                            if (hSalida.equals("")) {
                                String sql2 = "update mregistro set hSalida = ? where id = ?";
                                ps = conn.prepareStatement(sql2);
                                ps.setString(1, getHoraActual(fechaActualDiaHora()));
                                ps.setInt(2, Integer.parseInt(idR));
                                ps.executeUpdate();

                            } else {
                                Log.i("CONN", "Registro del día ya finalizado");
                            }
                        } while (rs.next());

                    } else {
                        Log.i("CONN", "¡Aún no has hecho check in hoy!");
                    }
                }

            } catch (Exception e) {
                Log.e("ERROR Conexion:",e.getMessage());
                return false;
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }

            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            if(result) {
                userCheckOut(idsession);
                Log.i("CONN", "conectado check out");
            }else {
                userCheckOut(idsession);
                Log.i("CONN", "no conectado check out");
            }
        }
    }

    /**
     * Admin add new worker
     */
    public static class AddNewWorkerTask extends AsyncTask<Void, Void, Boolean> {

        String user, pass;

        public AddNewWorkerTask(String user, String pass){
            this.user = user;
            this.pass = pass;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            PreparedStatement ps = null;
            ResultSet rs = null;

            //Toast result
            Handler handler = new Handler(context.getMainLooper());

            try {
                //Llamada a la conexión
                conn = getConnection();
                if (conn == null) {
                    return false;
                } else {
                    if (TextUtils.isEmpty(user) || TextUtils.isEmpty(pass)) {
                        handler.post( new Runnable(){
                            public void run(){
                                Toast.makeText(context, "Enter username and password", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        String sql = "select * from muser where username = ?";
                        ps = conn.prepareStatement(sql);
                        ps.setString(1, user);
                        rs = ps.executeQuery();

                        if (rs.next()) {
                            do {
                                // Passing values
                                final String uname = rs.getString(3);

                                handler.post(new Runnable() {
                                    public void run() {
                                        Toast.makeText(context, "User " + uname + " already exists", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } while (rs.next());

                        } else {

                            String sql2 = "insert into muser (rol,username,password,salt)"
                                    + "VALUES(?,?,?,?);";
                            ps = conn.prepareStatement(sql2);

                            String salt = PasswordUtils.getSalt(30);

                            ps.setString(1, "trabajador");
                            ps.setString(2, user);
                            ps.setString(3, PasswordUtils.generateSecurePassword(pass, salt));
                            ps.setString(4, salt);

                            if(ps.executeUpdate() == 1) {
                                Log.i("CONN", "insert ok");
                            } else {
                                Log.i("CONN", "insert KO");
                            }

                            handler.post(new Runnable() {
                                public void run() {
                                    Toast.makeText(context, "User register done", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }

            } catch (Exception e) {
                Log.e("ERROR Conexion:",e.getMessage());
                return false;
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }

            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            /* Tanto si hay conexión como no, se guardará en SQLite
                el usuario para una futura sincronización */
            if(result) {
                addNewWorker(user, pass);
                Log.i("CONN", "conectado");
            }else {
                addNewWorker(user, pass);
                Log.i("CONN", "no conectado");
            }
        }
    }

    /*************** FIN POSTGRESQL SERVER ****************/
}
