package angela.montoya.app_proyect.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import angela.montoya.app_proyect.R;
import angela.montoya.app_proyect.utils.Interface_comunica;

import static angela.montoya.app_proyect.utils.Protocolo.CONTACTO;
import static angela.montoya.app_proyect.utils.Protocolo.SEPARADOR;


public class ContactoFragment extends Fragment {
    private EditText et_asunto_contacto;
    private EditText et_remtt_contacto;
    private EditText et_nick_contacto;
    private EditText et_contenido_email;
    private TextView et_error_contacto;

    private Button button_contactar;
    private Interface_comunica interface_comunica;
    private String remitente, nick_usuario, mensaje, asunto;

    public ContactoFragment() {
        // Required empty public constructor
    }

    public static ContactoFragment newInstance(String param1, String param2) {
        ContactoFragment fragment = new ContactoFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contacto, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        et_asunto_contacto =view.findViewById(R.id.et_asunto_contacto);
        et_remtt_contacto =view.findViewById(R.id.et_remtt_contacto);
        et_nick_contacto=view.findViewById(R.id.et_nick_contacto);
        et_contenido_email=view.findViewById(R.id.et_contenido_email);
        et_error_contacto=view.findViewById(R.id.tv_error_contacto);

        button_contactar=view.findViewById(R.id.button_contactar);
        //tv_error_contacto.setText("");
        this.button_contactar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                asunto = et_asunto_contacto.getText().toString();
                remitente = et_remtt_contacto.getText().toString();
                nick_usuario =et_nick_contacto.getText().toString();
                mensaje =et_contenido_email.getText().toString();

                if(!TextUtils.isEmpty(asunto) && !TextUtils.isEmpty(remitente) &&!TextUtils.isEmpty(nick_usuario)&&
                !TextUtils.isEmpty(asunto) ){
                    Log.v("Contact FRAG", "||-------------------------button_contactar ");
                    et_error_contacto.setText("Enviado el email.......");
                    String msg= CONTACTO + SEPARADOR + asunto + SEPARADOR + remitente + SEPARADOR + nick_usuario+SEPARADOR+mensaje;

                    String s= interface_comunica.icomunicacion(msg, R.id.button_contactar);
                    et_error_contacto.setText(s);

                    Timer t = new Timer(false);
                    t.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            activity.runOnUiThread(new Runnable() {
                                public void run() {
                                    et_error_contacto.setText("");
                                    et_asunto_contacto.setText("");
                                    et_remtt_contacto.setText("");
                                    et_nick_contacto.setText("");
                                    et_contenido_email.setText("");
                                }
                            });
                        }
                    }, 5000); //5000 equivale a 5 segundos (en milisegundos)

                }else{
                    et_error_contacto.setText("Campos no pueden estar vacio.");
                }

            }
        });
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
}