package angela.montoya.app_proyect;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.Menu;
import android.support.design.widget.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Timer;
import java.util.TimerTask;

import angela.montoya.app_proyect.modelo.ConexionServer;
import angela.montoya.app_proyect.ui.ContactoFragment;
import angela.montoya.app_proyect.ui.estafa_formulario.Estafa_formFragment;
import angela.montoya.app_proyect.ui.gallery.details.DetallesFragment;
import angela.montoya.app_proyect.ui.gallery.Item_estafa;
import angela.montoya.app_proyect.ui.gallery.listado.ListEstafaFragment;
import angela.montoya.app_proyect.ui.home.HomeFragment;
import angela.montoya.app_proyect.ui.login.LoginFragment;
import angela.montoya.app_proyect.ui.recordarContraseña.RestablecerPasswordFragment;
import angela.montoya.app_proyect.ui.recordarContraseña.ForgotPwFragment;
import angela.montoya.app_proyect.ui.register_formulario.Register_formFragment;

import angela.montoya.app_proyect.utils.Interface_comunica;
import angela.montoya.app_proyect.utils.*;
import static angela.montoya.app_proyect.utils.Protocolo.*;

/*
https://tutorialwing.com/android-form-validation-using-android-awesomevalidation-library-tutorial/

Navigation drawer llama a actividad que se sobrepone, ¿cómo evitarlo?
* https://es.stackoverflow.com/questions/40124/navigation-drawer-llama-a-actividad-que-se-sobrepone-cómo-evitarlo

https://es.stackoverflow.com/questions/208075/como-tener-dos-navigation-drawer-y-mostrarlos-de-acuerdo-a-algun-parametro

cambiar a activity
https://es.stackoverflow.com/questions/45118/como-implementar-un-menú-navigation-drawer-con-actividades


colores
https://coolors.co/f06543-e8e9eb-e6e7e6-e4e4e0-e2e2db-e0dfd5-313638-f09d51

esto es un cambio pa git
* */

public class MainActivity extends AppCompatActivity implements Interface_comunica,Serializable, ListEstafaFragment.iEnvio {
    static final public String KEY="KEY";
    static final public String ISLOGGED="ISLOGGED";


    private AppBarConfiguration mAppBarConfiguration;
    public ConexionServer conexion;
    private Toast toast;
    private boolean estaRegistrado=false;
    private String str =null;
    private String  nombre_usuario =null;
    private String  email_usuario =null;
    private DrawerLayout drawer;
    private boolean isLogueado =false;

    private MenuItem item_form_estafa;
    private MenuItem item_form_home;
    private MenuItem item_form_register;
    private MenuItem item_form_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(savedInstanceState!=null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.nav_host_fragment,
                            ListEstafaFragment.newInstace()).commitNow();
        }

        IncialFragment fragment = new IncialFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment, fragment)
                .commit();

        conexion = ConexionServer.getINSTANCE();

  /*      FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        // depende si esta logueado puede ver items_menu
        Menu menu=navigationView.getMenu();
       item_form_estafa=menu.findItem(R.id.nav_estafa);
       item_form_home=menu.findItem(R.id.nav_home);
       item_form_register=menu.findItem(R.id.nav_register);
       item_form_login=menu.findItem(R.id.nav_login);
       item_form_estafa.setVisible(false);
       item_form_home.setVisible(false);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home,R.id.nav_estafa,  R.id.nav_login, R.id.nav_register,
                R.id.nav_estafa, R.id.nav_list_frag)//, R.id.nav_forgotPw solo accesible desde loginFrame
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

     /*
     https://es.stackoverflow.com/questions/40124/navigation-drawer-llama-a-actividad-que-se-sobrepone-cómo-evitarlo
     probando-- W?
     HomeFragment fragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment, fragment)
                .commit();*/

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                boolean fragmentTransaction = false;
                Fragment fragment=null;
                Bundle bundle;
               // Toast.makeText(MainActivity.this, "menuItem.getItemId() "+menuItem.getItemId(), Toast.LENGTH_SHORT).show();
                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        Toast.makeText(MainActivity.this, "home ", Toast.LENGTH_SHORT).show();
                        fragment = new HomeFragment();
                        fragmentTransaction = true;
                        getSupportActionBar().setTitle(getText(R.string.menu_home));
                        break;

                   // case R.id.nav_list_frag:
                    case R.id.nav_list_frag:
                        conexion.enviarMensaje_alServidor(GET_LIST_ESTAFAS+SEPARADOR);
                        String str=conexion.recibirMensaje_delServidor();
                       // Toast.makeText(MainActivity.this, "listado "+str, Toast.LENGTH_SHORT).show();

                         bundle = new Bundle();
                         bundle.putString("LIST", str);

                        fragment = new ListEstafaFragment();
                        fragment.setArguments(bundle);
                        fragmentTransaction = true;
                        getSupportActionBar().setTitle(getText(R.string.menu_lista_estafas));
                        break;
                    case R.id.nav_login:
                        Toast.makeText(MainActivity.this, "R.id.login ", Toast.LENGTH_SHORT).show();
                        fragment = new LoginFragment();
                        fragmentTransaction = true;
                        getSupportActionBar().setTitle(getText(R.string.menu_login));
                        break;
                    case R.id.nav_register:
                        Toast.makeText(MainActivity.this, "R.id.reguister ", Toast.LENGTH_SHORT).show();
                        fragment = new Register_formFragment();
                        fragmentTransaction = true;
                        getSupportActionBar().setTitle(getText(R.string.menu_register));
                        break;
                    case R.id.nav_estafa: // form estafa--> peticion server --> categorias, tgas
                        conexion.enviarMensaje_alServidor(GET_CATEGORIES+SEPARADOR);
                        String categorias=conexion.recibirMensaje_delServidor();
                        conexion.enviarMensaje_alServidor(GET_TAGS+SEPARADOR);
                        String tags=conexion.recibirMensaje_delServidor();

                        Toast.makeText(MainActivity.this, "publica estafa "+categorias, Toast.LENGTH_SHORT).show();

                        bundle = new Bundle();
                        bundle.putString(GET_CATEGORIES,categorias);
                        bundle.putString(GET_TAGS, tags);
                        fragment = new Estafa_formFragment();
                        fragment.setArguments(bundle);
                        fragmentTransaction = true;
                        getSupportActionBar().setTitle(getText(R.string.menu_estafa));
                        break;
                    case R.id.nav_logout:
                         MainActivity.super.onBackPressed();
                         System.exit(0);
                         break;
                    case R.id.nav_contacto:
                        fragment = new ContactoFragment();
                        fragmentTransaction = true;
                        getSupportActionBar().setTitle(getText(R.string.menu_estafa));
                        break;
                }

                if (fragmentTransaction) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.nav_host_fragment, fragment)
                            .commit();

                    menuItem.setChecked(true);
                    //getSupportActionBar().setTitle(menuItem.getTitle());
                }

                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        //Drawer lateral
        NavigationView rightNavigationView = (NavigationView) findViewById(R.id.nav_right_view);
        rightNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                // Handle Right navigation view item clicks here.
                int id = item.getItemId();
                Log.v(TAG_MAIN, "||----------------PULSO---------onNavigationItemSelected : ");
                if (id == R.id.nav_contacto) {
                    Toast.makeText(MainActivity.this, "Right Drawer - Settings", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.nav_logout) {
                    Toast.makeText(MainActivity.this, "Right Drawer - Logout", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.nav_help) {
                    Toast.makeText(MainActivity.this, "Right Drawer - Help me", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.nav_about) {
                    Toast.makeText(MainActivity.this, "Right Drawer - About", Toast.LENGTH_SHORT).show();
                }

                drawer.closeDrawer(GravityCompat.END); /*Important Line*/
                return true;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_openRight) {
            drawer.openDrawer(GravityCompat.END);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } /*else if (drawer.isDrawerOpen(GravityCompat.END)) {  *//*Closes the Appropriate Drawer*//*
            drawer.closeDrawer(GravityCompat.END);
        }*/ else {
            if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
                getSupportFragmentManager().popBackStack();
            } else {
                super.onBackPressed();
                System.exit(0);
            }
    /*        super.onBackPressed();
            System.exit(0);*/
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    TextView tv_navheaderTop;


    //-Interface que comunica con las fragments y devuelve-> str==la respuesta del servidor
    // evalua,
    // las acciones q  conecta con server , recibe respuesta otras str vacio--cambiar esta forma??
    @Override
    public String icomunicacion(String data, int elBotonPulsado) {
        Log.v(TAG_MAIN, "||-------------------------icomunicacion2 : "+data);

        String [] vstr=null;
        String str_recibidoSever="nada";
        String str_envia_alFragmnt="";
        Bundle bundle = new Bundle();  //Crear bundle, que son los datos que pasaremos(la respusta del server)
        Fragment fragment;
        switch (elBotonPulsado){
            //fragment LOGIN casos [LOGIN_NOT_OK: errorStr, LOGIN_OK: nombreUsusario]
            case R.id.button_signIn: // se pulso boton signin[LOGIN]
                 this.conexion.enviarMensaje_alServidor(data);
                 Log.v(TAG_MAIN, "||-------------------------button_signIn : "+data);
                 str_recibidoSever= conexion.recibirMensaje_delServidor();

                 vstr=str_recibidoSever.split(SEPARADOR);
                  tv_navheaderTop=findViewById(R.id.tv_navheaderTop); // establece el nombre


                 if(vstr[0].equals(LOGIN_OK)){ //LOGIN_OK+SEPARADOR+nombre_ususario+SEPARADOR+CHECKBOX_REGISTRO_TRUE)
                     this.nombre_usuario=vstr[1];
                     tv_navheaderTop.setText(nombre_usuario);// stoy registed
                     this.isLogueado =true;
                     item_form_estafa.setVisible(isLogueado);// muestra item_form_estafa
                     item_form_login.setVisible(!isLogueado);
                     item_form_register.setVisible(!isLogueado);
                     rellena_listaestafa();

                 }else{
                     estaRegistrado=false;
                     tv_navheaderTop.setText( "NO ____stoy registrado");
                     str_envia_alFragmnt="No estas registrado/a/e.Inténtelo de nuevo "+str_recibidoSever;
                 }
                 // la respuesta del server el fragment es el q evalua ??? o el Main???
                 break;

            case R.id.button_signup: //  abre el fragm registro
                fragment = new Register_formFragment();
                hacerTransaction(bundle, fragment, R.string.menu_register);

                break;

            //fragment REGISTER ok o not_ok
            case R.id.button_signInRegister:
                this.conexion.enviarMensaje_alServidor(data);
                Log.v(TAG_MAIN, "||-------------------------button_signInRegister : "+data);
                str_recibidoSever= conexion.recibirMensaje_delServidor();//REGISTER_FORM_OK + SEPARADOR + nick
                vstr=str_recibidoSever.split(SEPARADOR);
                if(vstr[0].equals(REGISTER_FORM_OK)){
                    this.nombre_usuario=vstr[1];
                    LoginFragment fragment1=new LoginFragment();
                    hacerTransaction(bundle, fragment1, R.string.menu_login);

                }else{// surgio error durante registro
                    str_envia_alFragmnt=vstr[1];
                }
                break;

                //  LoginFragment-- pasa a ForgotPwFragment
            case R.id.button_forgot_password: // abre Fragm forgotp
                fragment = new ForgotPwFragment(); // Preparar el fragmento
                hacerTransaction(bundle, fragment, R.string.menu_olvidoPw);
                str_envia_alFragmnt="boton PW";
                break;


                //fragment RECUPERAR PW-
            case R.id.button_forgotPw: // RECUPERAR_PW+SEPARADOR+email; si email exist--> abrir loginFrag, si no--> enviar msg error
                String [] vStr_separadata=data.split(SEPARADOR);

                 this.conexion.enviarMensaje_alServidor(data);// envia al server
                 str_recibidoSever= conexion.recibirMensaje_delServidor();// la respuesta del server
                 vstr=str_recibidoSever.split(Protocolo.SEPARADOR);

                 if(vstr[1].equals("NOK")){ //email NOesta en la bd
                     str_envia_alFragmnt="Introduce una dirección de correo electrónico válida";
                 }else{// email- si esta en la BD .abre al loginfragment
                    //Log.v(TAG_MAIN, "<<------------------------button_forgotPw : "+vstr[1]);
                    this.email_usuario=vStr_separadata[1];// guarda el email q voy necesitar en cambiarPws
                    this.estaRegistrado=true;
                    str_envia_alFragmnt=" ";
                    fragment= new RestablecerPasswordFragment(); // Preparar el fragmento
                    hacerTransaction(bundle, fragment, R.string.menu_Cambiar_password);
                }
                break;

                 //fragment RestablecerPasswordFragment
            case R.id.button_cambiarPassword: //CHANGE_PW+SEPARADOR+pw;
                vStr_separadata=data.split(SEPARADOR);
                String new_password=vStr_separadata[1];

                //CAMBIA EN LA bd LA pw--tengo el pw
                this.conexion.enviarMensaje_alServidor(CHANGE_PW+SEPARADOR+this.email_usuario +SEPARADOR+new_password);//CHANGE_PW:pw:email
                str_recibidoSever= conexion.recibirMensaje_delServidor(); // recibo si se cambio /no
                //str_recibidoSever=PW_CAMBIADO_NOTOK;-- solo para hacer pruebas--> borrar
                vstr=str_recibidoSever.split(Protocolo.SEPARADOR);
                if(vstr[0].equals(PW_CAMBIADO_OK)){// se cambio el password-> pasa a loginFragm
                    str_envia_alFragmnt="";
                    fragment=new LoginFragment();
                    hacerTransaction(bundle, fragment, R.string.menu_login);
                }else{// no cambia de fragm--> envia str
                    str_envia_alFragmnt="No se ha efectuado el cambio. Inténtelo de nuevo ";
                }
                break;


            //fragment de inicio- recibe Ip y conecta con el server
            case R.id.button_enviarIp: //Ha introducido la IP
                //Log.v(TAG_MAIN, "||-------------------------button_host : "+data);
                conectar(data);//HOST
                conexion.enviarMensaje_alServidor(GET_LIST_ESTAFAS+SEPARADOR);
                String lista=conexion.recibirMensaje_delServidor();
                bundle=new Bundle();
                bundle.putString("LIST", lista);
                fragment=new ListEstafaFragment();
                hacerTransaction(bundle, fragment, R.string.menu_lista_estafas);

                break;

            case R.id.button_contactar:
                Log.v(TAG_MAIN, "||-------------------------button_contactar : "+data);
                conexion.enviarMensaje_alServidor(CONTACTO+SEPARADOR+data);
                String s=conexion.recibirMensaje_delServidor();
                Toast.makeText(this, "Mensaje enviado", Toast.LENGTH_SHORT).show();

                conexion.enviarMensaje_alServidor(GET_LIST_ESTAFAS+SEPARADOR);
                String listax=conexion.recibirMensaje_delServidor();
                bundle=new Bundle();
                bundle.putString("LIST", listax);
                fragment=new ListEstafaFragment();
                hacerTransaction(bundle, fragment, R.string.menu_contacto);
                break;


                //Fragment formulario estafa
            case R.id.btn_enviar_estafa: //// ha pulsado REGISTRAR_ESTAFA: titulo: comentario: checboxs: resto datos
                this.conexion.enviarMensaje_alServidor(data+this.nombre_usuario);
                str_recibidoSever= conexion.recibirMensaje_delServidor();// deberia recibir + los datos del server

                // new estafa se inserta y recibo
                // no tengo claro si recibo aqui tds de nuevo o como
                Log.v(TAG_MAIN, "||------------btn_enviar_estafa : "+str_recibidoSever);

                vstr=str_recibidoSever.split(SEPARADOR);

                if(vstr[0].equals(REGISTRAR_ESTAFA_OK)){ // REGISTRAR_ESTAFA_OK:Web:titul
                    Toast.makeText(this, "Estafa publicada", Toast.LENGTH_SHORT).show();

                    rellena_listaestafa();

                }else{
                    Toast.makeText(this, "Estafa NO publicada.Intente lo de nuevo", Toast.LENGTH_SHORT).show();
                }
                break;


            case R.id.btn_det_enviar_comment:// / COMENTARIO:'blabla':id_estafa:fecha
                vstr=data.split(SEPARADOR);
                String comment=vstr[1];
                String id_estafa=vstr[2];

                if(!id_estafa.equals("0"))// si publica mas de un comment rescato  id_estafa
                     this.aux=id_estafa;
                else
                    id_estafa=aux;

                String msg=COMENTARIO+SEPARADOR+comment+SEPARADOR+id_estafa+SEPARADOR+vstr[3]+SEPARADOR+ this.nombre_usuario;
                this.conexion.enviarMensaje_alServidor(msg);

                str_recibidoSever= conexion.recibirMensaje_delServidor();

                Log.v(TAG_MAIN, " DATA ||------------DATA: "+data);
                Log.v(TAG_MAIN, "||------------str_recibidoSever : "+str_recibidoSever);

                str_envia_alFragmnt="Gracias por su comentario";
                enviarToast(str_envia_alFragmnt);

                rellena_listaestafa();
                break;


            default:
                Log.v(TAG_MAIN, "||-------------------------default : "+data);
                break;
        }

        return str_envia_alFragmnt;
    }

    String aux="0";
    private void rellena_listaestafa(){
        conexion.enviarMensaje_alServidor(GET_LIST_ESTAFAS+SEPARADOR);
        String list=conexion.recibirMensaje_delServidor();
        Bundle bundle = new Bundle();
        bundle.putString("LIST", list);

        Fragment fragment = new ListEstafaFragment();
        hacerTransaction(bundle, fragment, R.string.menu_lista_estafas);
    }

    private void conectar(String host_ip) {
           estableceHost(host_ip); // q cl introduce Al iniciar la app
         /* conexion con server
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .permitNetwork().build());*/
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectCustomSlowCalls()
                .penaltyFlashScreen()
                .permitNetwork().build());
        conexion.conectarConElServidor();
        conexion.crearFlujos();
        //Toast.makeText(getApplicationContext(), " stoy conected  ->"+ conexion.isEsconectado(), Toast.LENGTH_SHORT).show();
    }

    private void estableceHost(String host_ip) {
        Field field= null;
        try {
            field = ConexionServer.class.getDeclaredField("HOST");
            field.setAccessible(true);
            field.set(null, host_ip);
        } catch (NoSuchFieldException e) {
            Log.v(TAG_MAIN, "||-------------------------ERROR NoSuchFieldException : "+host_ip);
        } catch (IllegalAccessException e) {
            Log.v(TAG_MAIN, "||-------------------------ERROR IllegalAccessException : "+host_ip);
        }
    }


    // cambio de fragment
    public void hacerTransaction(Bundle bundle, Fragment fragment, int id_menu_tituloFragmm){

        FragmentManager fragmentManager=null;
        FragmentTransaction fragmentTransaction =null;

        fragment.setArguments(bundle);// ¡Importante! darle argumentos
        fragmentManager =this.getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
        getSupportActionBar().setTitle(getText(id_menu_tituloFragmm));

        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();// fin. muestra frag destino
    }

    //_M para mostrar Toast + tiempo
    public void showMyToast(final Toast toast, final int cnt) {
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
    }


    //al pulsar en un item de la lista estafa
    @Override
    public void iEnviarElemento(Item_estafa element) {
        DetallesFragment fragment=new DetallesFragment();

        if(fragment!=null){
            Bundle bundle=new Bundle();
            bundle.putSerializable(ELEMENT, (Serializable) element);
            bundle.putBoolean(LOG_OK, isLogueado);
            bundle.putString(NICK, this.nombre_usuario);
            hacerTransaction(bundle,fragment, R.string.menu_detalles_estafas );
        }else{
            Toast toast = Toast.makeText(getApplicationContext(), "Hi HO hi HO ", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP|Gravity.LEFT, 0, 0);
            //toast.show();

            Intent intent=new Intent(MainActivity.this, DetallesFragment.class);
            intent.putExtra(KEY, element);
            intent.putExtra(ISLOGGED, isLogueado);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            startActivity(intent);
           /* Bundle bundle=new Bundle();
            hacerTransaction(bundle,fragment, R.string.menu_gallery );*/
        }
    }



/*    // Se controla la pulsacion del boton atras Quitado
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==event.KEYCODE_BACK){
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setMessage("Desea salir de la aplicacíon?")
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent=new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    })
                 .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {
                         dialog.dismiss();
                     }
                 });
            builder.show();
        }
        return super.onKeyDown(keyCode, event);
    }*/

    private void enviarToast(String msg){
        CharSequence text = msg;
        int duration = Toast.LENGTH_LONG;
        Context context = this;
        Toast toast = Toast.makeText(context, text, duration);
        toast.setGravity(Gravity.TOP|Gravity.LEFT, 100, 100);
        toast.show();
    }
}

