package angela.montoya.app_proyect.utils;

public class Protocolo {
    public static final String MESSAGE = "MESSAGE";
    public static final String ObjModeloCl = "ObjModeloCl";

    public final static String OK = "OK"; // AUXILIARES
    public final static String NOT_OK = "NOT_OK";


    public static final String NAME = "NAME";
    public static final String PW = "PW";
    public final static String SEPARADOR = ":";
    public static final String SEPARADOR_checkboxs="?";// ?nombreCheckBos:
    public static final String SEPARADOR_dats="¿";
    public static final String PUNTO_Y_COMA=";";
    public final static String SIN_DATOS = "SIN_DATOS";

    public static final String NOMBRE = "NOMBRE";
    public final static String EMAIL = "EMAIL";
    public static final String URL="URL";// ?nombreCheckBos:
    public static final String TELEFONO="TELEFONO";

    // -- LOGIN FRAME
    public final static String ENVIAR = "ENVIAR";
    public final static String LOGIN = "LOGIN";
    public final static String REGISTER = "REGISTER";
    public final static String ROL_APPCL = "ROL_APPCL";// REGISTER_FORM : nombre : nick : email : password : ROL_APPCL

    public final static String LOGIN_OK = "LOGIN_OK";
    public final static String LOGIN_NOT_OK = "LOGIN_NOT_OK"; //LOGIN_NOT_OK: errorStr, LOGIN_OK: nombreUsusario
    public final static String RECORDAR_DATOS_LOGIN = "RECORDAR_DATOS_LOGIN";

    public final static String ESTADO_CHECKBOX = "ESTADO_CHECKBOX";
    public final static String CHECKBOX_REGISTRO_FALSE = "CHECKBOX_REGISTRO_FALSE";
    public final static String CHECKBOX_REGISTRO_TRUE = "CHECKBOX_REGISTRO_TRUE";
    public final static String VOLVER="VOLVER";

    // VISTA RECUPERAR PW
    public final static String RECUPERAR_PW="RECUPERAR_PW"; //RECUPERAR_PW+SEPARADOR+email
    public final static String VOLVER_VISTA_LOGIN="VOLVER_VISTA_LOGIN";

    // vista Register
    public final static String REGISTER_FORM_NOT_OK = "REGISTER_FORM_NOT_OK";
    public final static String REGISTER_FORM="REGISTER_FORM"; //REGISTER_FORM+:+ nombre + SEPARADOR+phone +SEPARADOR+nick+SEPARADOR+email +SEPARADOR+ password
    public static final String REGISTER_FORM_OK="REGISTER_FORM_OK";

    //vista cambiar la contraseña
    public static final String CHANGE_PW="CHANGE_PW";//
    public final static String PW_CAMBIADO_OK="PW_CAMBIADO_OK";//PW_CAMBIADO_OK:str
    public final static String PW_CAMBIADO_NOTOK="PW_CAMBIADO_NOTOK";//PW_CAMBIADO_NOTOK:str

    //vista estafa
    public static final String REGISTRAR_ESTAFA="REGISTRAR_ESTAFA";//REGISTRAR_ESTAFA: titulo: comentario: fecha : resto de datos
    public final static String REGISTRAR_ESTAFA_OK="REGISTRAR_ESTAFA_OK";
    public final static String REGISTRAR_ESTAFA_NOTOK="REGISTRAR_ESTAFA_NOTOK";

    //vista detalles
    public static final String COMENTARIO="COMENTARIO";//COMENTARIO:'blabla':id_estafa:fecha:email_usuario

    //protocolo menu
    public static final String ACTION1="ACTION1";

    //----------Tags xa el LOG
    public static final String TAG_REGISTER="TAG_REGISTER";
    public static final String TAG_DENUNCIA="TAG_DENUNCIA";
    public static final String TAG_MAIN="TAG_MAIN";
    public static final String TAG_LOGIN="TAG_LOGIN";
    public static final String TAG_FORGOT="TAG_FORGOT";
   // public static final String TAG_LOGIN="TAG_LOGIN";
    public static final String TAG_CHANGE_PW="TAG_CHANGE_PW";
    public static final String CONTACTO="CONTACTO"; //CONTACTO:asunto:email mio:nick:Me gustarria ...

    //keys
    public static final String KEY_CAMBIOPW="KEY_CAMBIOPW";


    //probando obtener cats
    public static final String GET_CATEGORIES="GET_CATEGORIES"; //GET_CATEGORIES+SEPARADOR
    public static final String GET_TAGS="GET_TAGS";//GET_TAGS+SEPARADOR
    public final static String GET_LIST_ESTAFAS = "GET_LIST_ESTAFAS";
    public final static String GET_DETALLES_ESTAFAS= "GET_DETALLES_ESTAFAS";// llamado desde detailsFrag -GET_DETALLES_ESTAFAS+SEPARADOR
    public final static String GET_COMMENTS_ESTAFA="GET_COMMENTS_ESTAFA"; // enviado desde details GET_COMMENTS_ESTAFA+SEPARADOR+ID_ESTAFA
    public final static String GET_CONTADOR_VISTAS="GET_CONTADOR_VISTAS";

    public final static String NICK="NICK";
    public final static String LOG_OK="LOG_OK";
    public final static String ELEMENT="ELEMENT";


    //https://tutorialwing.com/android-form-validation-using-android-awesomevalidation-library-tutorial/
    // ajustar los Tviews
//https://developer.android.com/guide/topics/ui/look-and-feel/autosizing-textview?hl=es-419


  /*  public Timer getTimerToast(){
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                toast.show();
            }
        }, 0, 3000);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                toast.cancel();
                timer.cancel();
            }
        }, cnt );
    }*/
}
