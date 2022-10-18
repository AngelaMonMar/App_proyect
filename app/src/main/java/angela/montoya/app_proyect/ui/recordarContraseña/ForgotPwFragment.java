package angela.montoya.app_proyect.ui.recordarContraseña;

import android.app.Activity;
import android.content.Context;
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

import angela.montoya.app_proyect.R;
import angela.montoya.app_proyect.utils.Interface_comunica;

import static angela.montoya.app_proyect.utils.Protocolo.*;


public class ForgotPwFragment extends Fragment {
    private EditText et_recuperar_password;
    private TextView textview_aux;
    private Button button_forgotPw;
    private Interface_comunica interface_comunica;

    public ForgotPwFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ForgotPwFragment newInstance(String param1, String param2) {
        ForgotPwFragment fragment = new ForgotPwFragment();
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
        View view= inflater.inflate(R.layout.fragment_forgot_pw, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        et_recuperar_password=view.findViewById(R.id.et_recuperar_password);
        button_forgotPw=view.findViewById(R.id.button_forgotPw);
        textview_aux=view.findViewById(R.id.textview_aux);

        this.button_forgotPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Log.v(TAG_FORGOT, "||-------------------------button_forgot_password ");
                String email=et_recuperar_password.getText().toString();
                String msg=RECUPERAR_PW+SEPARADOR+email;
                //String msg= LOGIN + ":" + "pepe" + ":" + "123" + ":" + "false";
                String s= interface_comunica.icomunicacion(msg, R.id.button_forgotPw);
                textview_aux.setText(""+s);
                et_recuperar_password.setText("");
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