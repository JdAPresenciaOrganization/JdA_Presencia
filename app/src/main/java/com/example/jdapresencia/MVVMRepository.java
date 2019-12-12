package com.example.jdapresencia;

import android.content.Context;
import android.widget.Toast;

import com.example.jdapresencia.model.Registro;

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

    /******** HOME VIEW MODEL METHODS ********/

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

            for (int i = 0; i < getUserRegisterFile(idSession).size(); i++) {
                /*
                Log.i("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX id Registro", getUserRegisterFile(idSession).get(i).getIdR());
                Log.i("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX id FECHA", getUserRegisterFile(idSession).get(i).getFecha());
                Log.i("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX id ENTRADA", getUserRegisterFile(idSession).get(i).getHoraEntrada());
                Log.i("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX id SALIDA", getUserRegisterFile(idSession).get(i).getHoraSalida());
                */

                userRegisterFileAppend.add(getUserRegisterFile(idSession).get(i));
                idRegistroSiguiente = Integer.parseInt(getUserRegisterFile(idSession).get(i).getIdR());
                diaUltimoRegistroAUX = getUserRegisterFile(idSession).get(i).getFecha();
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
            Registro registro1 = new Registro("1", "05/12/2019", "10:00:00", "18:00:00", idSession);
            Registro registro2 = new Registro("2", "06/12/2019", "10:10:00", "18:30:00", idSession);
            Registro registro3 = new Registro("3", "09/12/2019", "10:20:00", "19:00:00", idSession);

            FileOutputStream fileout = new FileOutputStream(file);
            ObjectOutputStream dataOS = new ObjectOutputStream(fileout);

            userRegisterFile.add(registro1);
            userRegisterFile.add(registro2);
            userRegisterFile.add(registro3);

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

        for (int i = 0; i < getUserRegisterFile(idSession).size(); i++) {
            userRegisterFileAppend.add(getUserRegisterFile(idSession).get(i));
            idRegistroActual = Integer.parseInt(getUserRegisterFile(idSession).get(i).getIdR());
            diaUltimoRegistroAUX = getUserRegisterFile(idSession).get(i).getFecha();

            if (i == getUserRegisterFile(idSession).size() - 1) {
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

    public static ArrayList<Registro> getUserRegisterFile(String idSession){
        String FILE_NAME = "/"+idSession+".dat";
        ArrayList<Registro> userRegisterList = new ArrayList<>();
        try {
            File file = new File(context.getFilesDir().getPath()+FILE_NAME);

            FileInputStream filein = new FileInputStream(file);
            ObjectInputStream dataIS = new ObjectInputStream(filein);

            for (Registro userRegisterFromFileList : (ArrayList<Registro>) dataIS.readObject()) {
                userRegisterList.add(userRegisterFromFileList);
            }
            dataIS.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return userRegisterList;
    }

    /******** FIN HOME VIEW MODEL METHODS ********/

    /******** BUSCADOR TRABAJADORES VIEW MODEL METHODS ********/

    /******** BUSCADOR TRABAJADORES VIEW MODEL METHODS ********/
}
