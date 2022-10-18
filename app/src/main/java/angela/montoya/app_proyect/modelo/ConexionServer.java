package angela.montoya.app_proyect.modelo;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.net.Socket;

public final class ConexionServer implements Serializable {
    
    private static ConexionServer INSTANCE=null;

    private static int PUERTO = 19999;
    private static String HOST = "192";//192.168.1.138
   // private static String HOST;
    private static Socket socket;
    private static BufferedReader br;
    private static BufferedWriter bw;
    static Socket socketCl;
    static boolean esconectado;

    public ConexionServer() {

    }

    public static ConexionServer getINSTANCE() {
        if(INSTANCE==null){
            synchronized ((ConexionServer.class)){
                if(INSTANCE==null){
                    INSTANCE=new ConexionServer();
                }
            }
        }
        return INSTANCE;
    }

    public static void conectarConElServidor(){
         esconectado=false;
        try {
            socket = new Socket(getHOST(), PUERTO);
            esconectado=true;
        } catch (IOException ex) {
           // recibirMensaje_delServidor(tv_respuesta, ex.getMessage().toString());
        }

    }

    public static boolean isEsconectado() {
        return esconectado;
    }

    public static void crearFlujos(){
        try {
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            br = new BufferedReader(isr);

            OutputStream os = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            bw = new BufferedWriter(osw);

        } catch (IOException ex) {
        }
    }

    public static void enviarMensaje_alServidor(String mensaje){
        Log.v("TAG", "||-------------------------enviarMensaje_alServidor "+mensaje);
        try {
            bw.write(mensaje);
            bw.newLine();
            bw.flush();
        } catch (IOException ex) {
        }
    }

    public static String recibirMensaje_delServidor(){
        try {
            String mensaje = br.readLine();
            return mensaje;
        } catch (IOException ex) {

        }
        return "";
    }

    public static String getHOST() {
        return HOST;
    }

    //---------------------------------------


    public void closeSocket(){
        try {
            socketCl.close();
        } catch (IOException e) {
           // Log.e(LOG, "---ERROR closeSocket  "+e.getMessage());
        }
    }

    private String RecibeMsgServer(String cadena){
        return cadena;
    }

    public static void setHOST(String HOST) {
        ConexionServer.HOST = HOST;
    }

    //-----------------------------------------------------------------------------




}
