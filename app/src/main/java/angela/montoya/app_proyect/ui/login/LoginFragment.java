package angela.montoya.app_proyect.ui.login;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

import java.util.Timer;
import java.util.TimerTask;

import angela.montoya.app_proyect.utils.Interface_comunica;
import angela.montoya.app_proyect.R;
import angela.montoya.app_proyect.modelo.ConexionServer;

import static angela.montoya.app_proyect.utils.Protocolo.*;

public class LoginFragment extends Fragment {
    private final String TAG_LOGIN="TAG_LOGIN";

    private LoginViewModel mViewModel;
    private EditText et_username;// nick-- el nombre a gusto
    private EditText et_password;
    private TextView tv_error_login;
    private Button button_signin;
    private Button button_signup;
    private Button button_forgot_password;
    private ConexionServer conectarServer_INSTANCE;
    private AwesomeValidation validation;
    private String username, pw;

    private Interface_comunica interface_comunica;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_login, container, false);
        return view;
    }


    // Si pulsa signin y es error muestra msg
    // si pulsa signup --> se abre Register
    // si se pulsa olvido_pw--> se abre OlvidoPw

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        et_username=view.findViewById(R.id.et_username);
        et_password=view.findViewById(R.id.et_password);
        tv_error_login=view.findViewById(R.id.tv_error_login);
        tv_error_login.setTextColor(Color.RED);

        button_signin=view.findViewById(R.id.button_signIn);
        button_signup=view.findViewById(R.id.button_signup);
        button_forgot_password=view.findViewById(R.id.button_forgot_password);

        this.conectarServer_INSTANCE = ConexionServer.getINSTANCE();
        validation=new AwesomeValidation(ValidationStyle.BASIC);

        this.button_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validarAntesEnviar(); // valida los editText

                if(validation.validate()){
                    username =et_username.getText().toString();
                    pw=et_password.getText().toString();

                    String msg= LOGIN + SEPARADOR + username + SEPARADOR + pw + SEPARADOR + "false"+SEPARADOR+"APPCLIENT";// pendiente implementar checkbox recordarpw
                    //String s= interface_comunica.icomunicacion(msg);// envio al Main , el main envia al server devoviendo me la respuesta
                    String s= interface_comunica.icomunicacion(msg, R.id.button_signIn);// envio al Main , el main envia al server devoviendo me la respuesta
                    tv_error_login.setTextColor(Color.RED);
                    tv_error_login.setText(" "+s);
                    // para q desaparezca
                    Timer t = new Timer(false);
                    t.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            activity.runOnUiThread(new Runnable() {
                                public void run() {
                                    tv_error_login.setText("");
                                }
                            });
                        }
                    }, 5000); //5000 equivale a 5 segundos (en milisegundos)

                }else{
                    tv_error_login.setTextColor(Color.RED);
                    tv_error_login.setText(R.string.txt_error_datos_login);

                }
                 et_username.setText("");
                 et_password.setText("");
            }
        });

        //BOTON OLVIDO PW
        this.button_forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG_LOGIN, "||-------------------------button_forgot_password ");
                String s= interface_comunica.icomunicacion("mensaje", R.id.button_forgot_password);
                tv_error_login.setText("  "+s);
                et_username.setText("");
                et_password.setText("");
            }
        });

        this.button_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG_LOGIN, "||-------------------------button_signup ");
                String s= interface_comunica.icomunicacion("mensaje ", R.id.button_signup);
                tv_error_login.setText("  "+s);

                et_username.setText("");
                et_password.setText("");
            }
        });

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    Activity activity;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
      //  listener= (Interface_comunica) context;

        if(context instanceof Activity){
            this.activity= (Activity) context;
            interface_comunica = (Interface_comunica) this.activity; // q sea igual q ese contect
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        interface_comunica = null;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(LoginViewModel.class);

        // TODO: Use the ViewModel
    }







    // falta correggir fallo
    private void validarAntesEnviar() {
        this.validation.addValidation(this.getActivity(),R.id.et_username ,
                RegexTemplate.NOT_EMPTY,
                R.string.invalid_name);
        this.validation.addValidation(this.getActivity(),R.id.et_password ,
                ".{1,}",
                R.string.invalid_password);


    }


//probando 1
    public void setOnHeadlineSelectedListener(Interface_comunica callback) {
        this.interface_comunica = callback;
    }

}