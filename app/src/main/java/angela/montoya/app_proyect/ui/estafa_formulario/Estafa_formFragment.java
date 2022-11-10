package angela.montoya.app_proyect.ui.estafa_formulario;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import angela.montoya.app_proyect.MainActivity;
import angela.montoya.app_proyect.R;
import angela.montoya.app_proyect.utils.Interface_comunica;

import static angela.montoya.app_proyect.utils.Protocolo.EMAIL;
import static angela.montoya.app_proyect.utils.Protocolo.GET_CATEGORIES;
import static angela.montoya.app_proyect.utils.Protocolo.GET_TAGS;
import static angela.montoya.app_proyect.utils.Protocolo.NOMBRE;
import static angela.montoya.app_proyect.utils.Protocolo.REGISTRAR_ESTAFA;
import static angela.montoya.app_proyect.utils.Protocolo.SEPARADOR;
import static angela.montoya.app_proyect.utils.Protocolo.SEPARADOR_dats;
import static angela.montoya.app_proyect.utils.Protocolo.TAG_DENUNCIA;
import static angela.montoya.app_proyect.utils.Protocolo.TAG_MAIN;
import static angela.montoya.app_proyect.utils.Protocolo.TELEFONO;
import static angela.montoya.app_proyect.utils.Protocolo.URL;

public class Estafa_formFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private String TAG_ESTAFA="TAG_ESTAFA";
    private EstafaViewModel mViewModel;
    private Spinner spinner;
    TextView tv_date;
    private EditText et_titulo;
    private String category_selected;
    //private SearchView searchView ;
    private EditText et_name_estafador;
    private EditText et_phone_e;
    private EditText et_email_estafador;
    private EditText et_url_estafador;
    private EditText et_anunciado;
    private Activity activity;

    private EditText tv_error_register_denuncia;

    private Button btn_enviar_estafa;
    private   List<CheckBox> listado_checkboxs=new ArrayList<>();

    private AwesomeValidation validation;
    private Interface_comunica interface_comunica;
    private String datos_delMap="";

   private List<String> lista_conCategoria=new ArrayList<>();

    // Log.v(TAG_DENUNCIA, "||------------------------- : "+ msg);

    public static Estafa_formFragment newInstance() {
        return new Estafa_formFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pbc_estafa, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(EstafaViewModel.class);

        // TODO: Use the ViewModel
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // spinner
        spinner=view.findViewById(R.id.spinner);
        tv_date=view.findViewById(R.id.tv_date);

        et_titulo=view.findViewById(R.id.et_titulo_estafa);


        et_name_estafador=view.findViewById(R.id.et_name_estafador);
        et_phone_e =view.findViewById(R.id.et_phone_estafador);
        et_email_estafador=view.findViewById(R.id.et_email_estafador);
        et_url_estafador=view.findViewById(R.id.et_url_estafador);
        et_anunciado=view.findViewById(R.id.et_anunciado);
        tv_error_register_denuncia=view.findViewById(R.id.tv_error_register_denuncia);
        btn_enviar_estafa=view.findViewById(R.id.btn_enviar_estafa);

        Date date=new Date();
        // establece la fecha
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String fecha_str = sdf.format(date);
        tv_date.setText("Fecha " +fecha_str.toString());

        List<String>list_category=new ArrayList<>();
        List<String>list_tags=new ArrayList<>();
        GridLayout gridLayout=view.findViewById(R.id.grid_checkbox);
        gridLayout.removeAllViews();

        //recibo el bundle del main con las categorias y los tags
        Bundle b=getArguments();
        if(b!=null) {
            String categorias = b.getString(GET_CATEGORIES);
            String tags = b.getString(GET_TAGS);
            String [] vStr_categories=categorias.split(SEPARADOR);
            String [] vStr_tags=tags.split(SEPARADOR);
            list_category= Arrays.asList(vStr_categories);
            list_tags= Arrays.asList(vStr_tags);


            int total=list_tags.size();
            int columns=4;
            int row=total/columns;
            gridLayout.setColumnCount(columns);
            gridLayout.setRowCount(row + 1);

            int c=0;
            int r=0;
            for(String s : list_tags) {
                CheckBox checkBox = new CheckBox(view.getContext());
                checkBox.setText(s);
                checkBox.setTag(s);

                listado_checkboxs.add(checkBox);
                gridLayout.addView(checkBox);
            }
        }else{
            Toast.makeText(this.getContext(), "BUNDLE VACIO", Toast.LENGTH_SHORT).show();
        }

        //rellena el spinnner
        spinner.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, list_category));
        // spinner a la escucha y implents los M
        spinner.setOnItemSelectedListener(this);

        validation=new AwesomeValidation(ValidationStyle.BASIC);

        this.btn_enviar_estafa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarAntesEnviar();
                if(validation.validate()){
                    String str_datos=damedatos_aEnvia();
                    String msg=REGISTRAR_ESTAFA+SEPARADOR+str_datos; //REGISTRAR_ESTAFA: titulo: comentario: checboxs: resto datos
                    interface_comunica.icomunicacion(msg, R.id.btn_enviar_estafa);
                }else{
                    tv_error_register_denuncia.setText("El titulo (máximo 100 carateres),\ncontenido(máximo 500 caracteres).");
                }


            }
        });
    }

    private String damedatos_aEnvia() {
        String titulo="", nombre="", phone="",email="", url="", comentario="";
        String str_checkboxs="";
        String str_datos_estafador="";
         if(validation.validate()){

            titulo=et_titulo.getText().toString();//titulo y comentario obligatorio obligatorio
            comentario=et_anunciado.getText().toString();

            nombre=et_name_estafador.getText().toString();//
            phone=et_phone_e.getText().toString();
            url=et_url_estafador.getText().toString();
            email=et_email_estafador.getText().toString();


            if(nombre!=null && !TextUtils.isEmpty(nombre)){
               str_datos_estafador+=NOMBRE+SEPARADOR_dats+nombre+SEPARADOR;
            }
            if(phone!=null && !TextUtils.isEmpty(phone)){
                str_datos_estafador+=TELEFONO+SEPARADOR_dats+phone+SEPARADOR;
            }
            if(email!=null && !TextUtils.isEmpty(email)){
                str_datos_estafador+=EMAIL+SEPARADOR_dats+email+SEPARADOR;
            }
            if(url!=null && !TextUtils.isEmpty(url)){
                str_datos_estafador+=URL+SEPARADOR_dats+url+SEPARADOR;
            }

            //recorre los checkboxs y los almcena de esta forma checkTag:

            Log.v(TAG_ESTAFA, "||-------------------------voy recorrer checkboxs : ");
            for(CheckBox c:this.listado_checkboxs){
                if(c.isChecked()){
                    str_checkboxs+="?"+c.getTag().toString()+SEPARADOR;
                    Log.v(TAG_ESTAFA, "||-------------------------: "+c.getTag().toString());
                }
            }
         }//validation.validate()
       return  titulo+SEPARADOR+ category_selected + SEPARADOR+comentario+SEPARADOR+str_checkboxs+str_datos_estafador;

    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
         category_selected =parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //
    }



    private void validarAntesEnviar() {
        String regexTitulo = ".{1,5}";
        String regexContenido = ".{1,500}";

        this.validation.addValidation(this.getActivity(),R.id.et_titulo_estafa,
                regexTitulo,
                R.string.invalid_titulo);
        this.validation.addValidation(this.getActivity(),R.id.et_anunciado ,
                ".{2,500}",
                R.string.invalid_name);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
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

/*
* click en formulario--> envia al Main--> main lo envia al server
* main recibe respuesta -- evalua y envia al frag x
* */