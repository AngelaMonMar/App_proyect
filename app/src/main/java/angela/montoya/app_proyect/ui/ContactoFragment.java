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
import android.widget.Toast;

import angela.montoya.app_proyect.R;
import angela.montoya.app_proyect.utils.Interface_comunica;

import static angela.montoya.app_proyect.utils.Protocolo.CONTACTO;
import static angela.montoya.app_proyect.utils.Protocolo.LOGIN;
import static angela.montoya.app_proyect.utils.Protocolo.SEPARADOR;


public class ContactoFragment extends Fragment {
    private EditText tv_asunto_contacto;
    private EditText tv_remtt_contacto;
    private EditText tv_nick_contacto;
    private EditText et_contenido_email;

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

        tv_asunto_contacto=view.findViewById(R.id.et_asunto_contacto);
        tv_remtt_contacto=view.findViewById(R.id.et_remtt_contacto);
        tv_nick_contacto=view.findViewById(R.id.et_nick_contacto);
        et_contenido_email=view.findViewById(R.id.et_contenido_email);

        button_contactar=view.findViewById(R.id.button_contactar);

        this.button_contactar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                asunto =tv_asunto_contacto.getText().toString();
                remitente =tv_remtt_contacto.getText().toString();
                nick_usuario =tv_nick_contacto.getText().toString();
                mensaje =et_contenido_email.getText().toString();

                if(!TextUtils.isEmpty(asunto) && !TextUtils.isEmpty(remitente) &&!TextUtils.isEmpty(nick_usuario)&&
                !TextUtils.isEmpty(asunto) ){
                    Log.v("Contact FRAG", "||-------------------------button_contactar ");
                    String msg= CONTACTO + SEPARADOR + asunto + SEPARADOR + remitente + SEPARADOR + nick_usuario+SEPARADOR+mensaje;

                    interface_comunica.icomunicacion(msg, R.id.button_contactar);
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