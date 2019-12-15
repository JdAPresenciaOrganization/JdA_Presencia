package com.example.jdapresencia;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.jdapresencia.model.Registro;
import com.example.jdapresencia.model.User;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
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
    //Singleton
    private static MVVMRepository srepository;

    private MVVMRepository(Context context){
        this.context = context;
    }
    public static MVVMRepository get(Context context){
        if (srepository == null){
            srepository = new MVVMRepository(context);
        }
        return srepository;
    }

    /******** CHECK IN/OUT METHODS ********/

    public static void userCheckIn(String idSession) throws IOException {
        //Se crea un fichero por cada usuario utilizando el id del usuario
        String FILE_NAME = "/"+idSession+".dat";
        File file = new File(context.getFilesDir().getPath()+FILE_NAME);

        /* Si el fichero existe, se recorren los datos ya existentes en el fichero
        para añadirle el último registro que se haga, sino, se crea el primer registro
        del check in que tendrá id de registro 1 */
        if (file.exists()) {
            //ArrayList para guardar los datos de fichero y poder hacer un add luego
            ArrayList<Registro> userRegisterFileAppend = new ArrayList<>();
            //Variable auxiliar para guardar el id de los registros que se van recorriendo
            int idRegistroSiguiente = 0;
            String diaUltimoRegistroAUX = "";

            for (int i = 0; i < getRegisters(idSession).size(); i++) {
                /*
                Log.i("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX id Registro", getUserRegisterFile(idSession).get(i).getIdR());
                Log.i("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX id FECHA", getUserRegisterFile(idSession).get(i).getFecha());
                Log.i("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX id ENTRADA", getUserRegisterFile(idSession).get(i).getHoraEntrada());
                Log.i("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX id SALIDA", getUserRegisterFile(idSession).get(i).getHoraSalida());
                */

                userRegisterFileAppend.add(getRegisters(idSession).get(i));
                idRegistroSiguiente = Integer.parseInt(getRegisters(idSession).get(i).getIdR());
                diaUltimoRegistroAUX = getRegisters(idSession).get(i).getFecha();
            }

            //Si la fecha del último registro no es igual que la actual es que no se ha hecho check in
            if ( !diaUltimoRegistroAUX.equals(getDiaActual(fechaActualDiaHora())) ) {
                FileOutputStream fileout = new FileOutputStream(file);
                ObjectOutputStream dataOS = new ObjectOutputStream(fileout);

                Registro registro2 = new Registro(Integer.toString(idRegistroSiguiente + 1), getDiaActual(fechaActualDiaHora()), getHoraActual(fechaActualDiaHora()), "", idSession);
                userRegisterFileAppend.add(registro2);

                dataOS.writeObject(userRegisterFileAppend);
                dataOS.close();

                Toast.makeText(context, "Check in done",Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "¡Ya has hecho check in hoy!",Toast.LENGTH_SHORT).show();
            }

        } else {
            /* Se crea guarda en el fichero por primera vez el registro,
            un ArrayList con id registro 1 y la fecha actual con la hora del check in */
            ArrayList<Registro> userRegisterFile = new ArrayList<>();
            Registro registro1 = new Registro("1", "09/12/2019", "10:20:00", "19:00:00", idSession);
            Registro registro2 = new Registro("2", "10/12/2019", "10:00:00", "18:00:00", idSession);
            Registro registro3 = new Registro("3", "11/12/2019", "10:10:00", "18:30:00", idSession);
            Registro registro4 = new Registro("4", "12/12/2019", "10:00:00", "18:00:00", idSession);
            Registro registro5 = new Registro("5", "13/12/2019", "10:10:00", "18:30:00", idSession);

            FileOutputStream fileout = new FileOutputStream(file);
            ObjectOutputStream dataOS = new ObjectOutputStream(fileout);

            userRegisterFile.add(registro1);
            userRegisterFile.add(registro2);
            userRegisterFile.add(registro3);
            userRegisterFile.add(registro4);
            userRegisterFile.add(registro5);

            dataOS.writeObject(userRegisterFile);
            dataOS.close();

            Toast.makeText(context, "Check in done",Toast.LENGTH_SHORT).show();
        }
    }

    public static void userCheckOut(String idSession) throws IOException {
        String FILE_NAME = "/"+idSession+".dat";
        File file = new File(context.getFilesDir().getPath()+FILE_NAME);

        //ArrayList para guardar los datos de fichero y poder hacer un add luego con la hora de salida
        ArrayList<Registro> userRegisterFileAppend = new ArrayList<>();
        int idRegistroActual = 0;
        String diaUltimoRegistroAUX = "";
        boolean lastRegister = false;

        for (int i = 0; i < getRegisters(idSession).size(); i++) {
            userRegisterFileAppend.add(getRegisters(idSession).get(i));
            idRegistroActual = Integer.parseInt(getRegisters(idSession).get(i).getIdR());
            diaUltimoRegistroAUX = getRegisters(idSession).get(i).getFecha();

            if (i == getRegisters(idSession).size() - 1) {
                lastRegister = true;
            }
        }

        //Si la fecha del último registro es igual que la actual es que ya se ha hecho check in
        if ( lastRegister && diaUltimoRegistroAUX.equals(getDiaActual(fechaActualDiaHora())) ) {
            FileOutputStream fileout = new FileOutputStream(file);
            ObjectOutputStream dataOS = new ObjectOutputStream(fileout);

            userRegisterFileAppend.get(idRegistroActual - 1).setHoraSalida(getHoraActual(fechaActualDiaHora()));

            dataOS.writeObject(userRegisterFileAppend);
            dataOS.close();

            Toast.makeText(context, "Check out done",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "¡Aún no has hecho check in hoy!",Toast.LENGTH_SHORT).show();
        }
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

    public static String RestDates(String firstDate, String secondDate) {

        //Convertimos horas, minutos y segundos de la primera hora a segundos
        int firstDateInSeconds_hours = Integer.parseInt(firstDate.substring(0,2))*3600;
        int firstDateInSeconds_minutes = Integer.parseInt(firstDate.substring(3,5))*60;
        int firstDateInSeconds_seconds = Integer.parseInt(firstDate.substring(6,8));

        //Convertimos horas, minutos y segundos de la segunda hora a segundos
        int secondDateInSeconds_hours = Integer.parseInt(secondDate.substring(0,2))*3600;
        int secondDateInSeconds_minutes = Integer.parseInt(secondDate.substring(3,5))*60;
        int secondDateInSeconds_seconds = Integer.parseInt(secondDate.substring(6,8));

        //Sumamos dichos segundos en una variable para luego poder restarlas
        int firstDateInSeconds = firstDateInSeconds_hours + firstDateInSeconds_minutes + firstDateInSeconds_seconds;
        int secondDateInSeconds = secondDateInSeconds_hours + secondDateInSeconds_minutes + secondDateInSeconds_seconds;

        //Restamos las dos horas para obtener los segundos de diferencia y lo convertimos al formato de fecha inicial
        int resultInSeconds = secondDateInSeconds - firstDateInSeconds;
        String finalFormatedTime = fromSecondsToTime(resultInSeconds);

        return finalFormatedTime;
    }

    public static String fromSecondsToTime(int segundos) {

        //Funcion que recibe segundos en un entero y devuelve dichos segundos en en el siguiente formato HH:MM:SS

        //Primero calculamos las horas, minutos y segundos que equivalen a la cantidad de segundos
        String hours = format2numbers(Integer.toString(segundos/3600));
        segundos = segundos%3600;
        String minutes = format2numbers(Integer.toString(segundos/60));
        String seconds = format2numbers(Integer.toString(segundos%60));

        return hours + ":" + minutes + ":" + seconds;

    }
    public static String format2numbers(String number) {
        if (number.length()<2) {
            return "0" + number;
        } else {
            return number;
        }
    }
    /******** FIN DATE METHODS ********/

    /******** USER METHODS ********/

    public static ArrayList<User> getUsersBy(String campo, String valor) {

        ArrayList<User> usersArray = new ArrayList<>();

        try {
            String FILE_NAME = "/usersFile.dat";
            File file = new File(context.getFilesDir().getPath()+FILE_NAME);
            ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(file));
            User usuario = (User) entrada.readObject();

            while (usuario!=null) {

                switch (campo) {
                    case "idU":
                        if (usuario.getIdU().equals(valor)) {
                            usersArray.add(usuario);
                        }
                        break;
                    case "rol":
                        if (usuario.getRol().equals(valor)) {
                            usersArray.add(usuario);
                        }
                        break;
                    case "username":
                        if (usuario.getUsername().equals(valor)) {
                            usersArray.add(usuario);
                        }
                        break;
                    default:
                        usersArray.add(usuario);
                }

                usuario = (User) entrada.readObject();

            }
            entrada.close();
        } catch (EOFException ignored) {
        } catch (IndexOutOfBoundsException | NullPointerException | ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

        return usersArray;
    };


    /******** FIN USER METHODS ********/

    /******** USER'S REGISTERS METHODS ********/

    public static ArrayList<Registro> getRegisters(String uid) {
        ArrayList<Registro> registros = new ArrayList<>();
        String FILE_NAME = "/" + uid + ".dat";

        File file = new File(context.getFilesDir().getPath()+FILE_NAME);

        try {
            ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(file));
            registros = (ArrayList<Registro>) entrada.readObject();
            entrada.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }


        return registros;
    }


    /******** FIN USER'S REGISTERS METHODS ********/
}
