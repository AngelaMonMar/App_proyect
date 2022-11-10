package angela.montoya.app_proyect.ui.register_formulario;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
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

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

import angela.montoya.app_proyect.R;
import angela.montoya.app_proyect.utils.Interface_comunica;

import static angela.montoya.app_proyect.utils.Protocolo.*;

public class Register_formFragment extends Fragment {

    public static final String PW = "PW";
    private Button button_signInRegister;
    private EditText et_name, et_nick, et_email;
    private EditText et_password, et_confirm_password, tv_error_register;

    private AwesomeValidation validation;
    private String email;
    private String password;
    private String nombre;
    private String nick;
    private Interface_comunica interface_comunica;

    private RegisterViewModel mViewModel;

    public static Register_formFragment newInstance() {
        return new Register_formFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_register, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        button_signInRegister = view.findViewById(R.id.button_signInRegister);

        et_email = view.findViewById(R.id.et_email);
        et_name = view.findViewById(R.id.et_name);
        // et_phone=view.findViewById(R.id.et_phone);// en server = apellido-- pendiente
        et_nick = view.findViewById(R.id.et_nick);
        et_password = view.findViewById(R.id.et_password);
        et_confirm_password = view.findViewById(R.id.et_confirm_password);
        tv_error_register = view.findViewById(R.id.tv_error_register);

        validation = new AwesomeValidation(ValidationStyle.BASIC);

        button_signInRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarAntesEnviar(); // valida los campos introducidos

                Log.v(TAG_REGISTER, "||-------------------------button_signInRegister " + email);
                if (validation.validate()) {  // si validad OK--> mira si las dos pws son iguales
                    email = et_email.getText().toString();
                    nombre = et_name.getText().toString();

                    nick = et_nick.getText().toString();
                    password = et_password.getText().toString();
                    String confirm_password = et_confirm_password.getText().toString();

                    //compruebo si pw son iguales
                    if (password.equals(confirm_password)) {
                        // usuario=new Usuario(nombre, nick, email,pw);
                        String msg = REGISTER_FORM + SEPARADOR + nombre + SEPARADOR + nick + SEPARADOR +
                                email + SEPARADOR + password+SEPARADOR+ROL_APPCL;

                        String respuesta = interface_comunica.icomunicacion(msg, R.id.button_signInRegister);
                        Log.v(TAG_REGISTER, "||-------------------------button_signInRegister " + respuesta);
                        tv_error_register.setText("--" + respuesta);
                    } else {
                        et_password.setText("");
                        et_confirm_password.setText("");
                        Log.v(TAG_REGISTER, "||-------------------------se pulso button_pw no es igual");
                        tv_error_register.setText("PW NO SON IGUALES");
                    }
                }
            }//  if(validation.validate())
        });
    }

/*        //-------------------SIN VALIDAR + rapido PRUEBA
        button_signInRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg=REGISTER_FORM+SEPARADOR + "ana" +SEPARADOR+"n"+SEPARADOR+
                        "email" +SEPARADOR+ "123" ;
                //Log.v(TAG_REGISTER,"||-------------------------button_signInRegister "+R.id.button_signInRegister);
                interface_comunica.icomunicacion(msg, R.id.button_signInRegister);
            }
        });
    }*/

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(RegisterViewModel.class);
        // TODO: Use the ViewModel
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

    // M valida los datos con AwesomeValidation
    //https://tutorialwing.com/android-form-validation-using-android-awesomevalidation-library-tutorial/
    private void validarAntesEnviar() {
        this.validation.addValidation(this.getActivity(),R.id.et_name ,
                RegexTemplate.NOT_EMPTY,
                R.string.invalid_name);
        this.validation.addValidation(this.getActivity(),R.id.et_nick ,
                RegexTemplate.NOT_EMPTY,
                R.string.invalid_nick);
      /*  this.validation.addValidation(this.getActivity(),R.id.et_email ,
                Patterns.EMAIL_ADDRESS,
                R.string.invalid_email);*/
        this.validation.addValidation(this.getActivity(),R.id.et_phone_estafador ,
                ".{1,}", // [5-9]{1}[0-9]$
                R.string.invalid_phone);


        this.validation.addValidation(this.getActivity(),R.id.et_password ,
                ".{2,}",
                R.string.invalid_password);
        this.validation.addValidation(this.getActivity(),R.id.et_confirm_password ,
                ".{2,}",
                R.string.invalid_confirm_password);
        this.validation.addValidation(this.getActivity(),R.id.et_password, R.id.et_confirm_password ,
                         R.string.invalid_confirm_password);


    }



}