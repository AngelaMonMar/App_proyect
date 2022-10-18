package angela.montoya.app_proyect;

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

import angela.montoya.app_proyect.utils.Interface_comunica;

import static angela.montoya.app_proyect.utils.Protocolo.TAG_FORGOT;

/*
* Es el primer fragment q se muestra.
* Pide una IP
  Cambia el host en el main
*
* */

public class IncialFragment extends Fragment {
    private EditText et_hostIp;
    private Button button_host;
    private Interface_comunica interface_comunica;
    private Activity activity;

    public IncialFragment() {
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
        return inflater.inflate(R.layout.fragment_hostip, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

         et_hostIp =view.findViewById(R.id.et_hostIp);
         button_host=view.findViewById(R.id.button_enviarIp);


        this.button_host.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String host= et_hostIp.getText().toString();
                 //host= "192.168.1.138";
                if(!host.isEmpty())
                    interface_comunica.icomunicacion(host, R.id.button_enviarIp);
                else
                    Log.v(TAG_FORGOT, "||-------------------------button_host No han intruducido algo");
            }
        });
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