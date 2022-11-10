package angela.montoya.app_proyect.ui.recordarContraseña;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

import angela.montoya.app_proyect.R;
import angela.montoya.app_proyect.utils.Interface_comunica;


import static angela.montoya.app_proyect.utils.Protocolo.*;


public class RestablecerPasswordFragment extends Fragment {
    private TextView et_password_cambiar;
    private TextView et_password_cambio_confirm;
    private TextView tv_error_confirm_pw;
    private Button button_cambiarPassword;
    private Interface_comunica interface_comunica;

    public RestablecerPasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_change_pw, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        et_password_cambiar=view.findViewById(R.id.et_password_cambiar);
        et_password_cambio_confirm=view.findViewById(R.id.et_password_cambio_confirm);

        button_cambiarPassword=view.findViewById(R.id.button_cambiarPassword);
        tv_error_confirm_pw=view.findViewById(R.id.tv_error_confirm_pws);

        validation=new AwesomeValidation(ValidationStyle.BASIC);

        this.button_cambiarPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validarAntesEnviar();

                if(validation.validate()){
                    String pw=et_password_cambiar.getText().toString(); //et_password_cambiar.getText().toString();
                    String pw_confirm=et_password_cambio_confirm.getText().toString();
                    if(pw.equals(pw_confirm)){
                        String msg=CHANGE_PW+SEPARADOR+pw;
                        String respuestaServer=interface_comunica.icomunicacion(msg, R.id.button_cambiarPassword);
                        tv_error_confirm_pw.setText(respuestaServer);// Aqui solo entra en caso q no se haya surgido E en server
                    }else{ // se puede quitar-- ya es validad
                        tv_error_confirm_pw.setText(R.string.invalid_confirm_password);
                        et_password_cambiar.setText("");
                        et_password_cambio_confirm.setText("");
                    }
                }
            }
        });

    }

    AwesomeValidation validation;
    private void validarAntesEnviar() {
           this.validation.addValidation(this.getActivity(),R.id.et_password_cambiar ,
                 ".{2,}",
                R.string.invalid_password);

          this.validation.addValidation(this.getActivity(),R.id.et_password_cambio_confirm ,
                R.id.et_password_cambiar,
               R.string.invalid_confirm_password);

}

    Activity activity;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //  loginListener= (Interface_comunica) context;

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
