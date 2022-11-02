package angela.montoya.app_proyect.ui.gallery.details;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import angela.montoya.app_proyect.R;
import angela.montoya.app_proyect.modelo.ConexionServer;
import angela.montoya.app_proyect.ui.gallery.Item_comment;
import angela.montoya.app_proyect.ui.gallery.Item_estafa;
import angela.montoya.app_proyect.ui.gallery.listado.ListEstafaFragment;
import angela.montoya.app_proyect.ui.gallery.listado.List_commentAdapter;
import angela.montoya.app_proyect.utils.Interface_comunica;

import static angela.montoya.app_proyect.utils.Protocolo.*;

//Interface_comunica ,
public class DetallesFragment extends Fragment  {//implements  ListEstafaFragment.iEnvio
    private TextView tv_det_nick;
    private TextView tv_det_titulo;
    private TextView tv_det_category;
    private TextView tv_date;
    private TextView tv_det_contenido;

    private TextView tv_det_email;
    private TextView tv_det_nombre;
    private TextView tv_det_url;
    private TextView tv_det_phone;
    private TextView tv_det_tags;

    private TextView tv_det_isLog;

    private RecyclerView recyclerView_coment;
    private Button btn_enviar_comment;
    private EditText et_comentario;

    private Interface_comunica interface_comunica;
    private Activity activity;
    private ListEstafaFragment.iEnvio elCallback;
    private int id_estafa;
    private List_commentAdapter listAdapter;
    private  List<Item_comment> lista_comments;

    private LinearLayout layout_comentario;
    private LinearLayout layout_det_nombre;
    private LinearLayout layout_det_email;
    private LinearLayout layout_det_url;
    private LinearLayout layout_det_phone;
    private LinearLayout layout_det_tags;

    public ConexionServer conexion;

    public DetallesFragment() {
        // Required empty public constructor
    }

    public static DetallesFragment newInstance(String param1, String param2) {
        DetallesFragment fragment = new DetallesFragment();
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
        View view=inflater.inflate(R.layout.fragment_detalles, container, false);
        tv_det_nick=view.findViewById(R.id.tv_det_nick);
        tv_date=view.findViewById(R.id.tv_det_fechax);
        tv_det_titulo=view.findViewById(R.id.tv_det_titulo);
        tv_det_category=view.findViewById(R.id.tv_det_category);
        tv_det_contenido=view.findViewById(R.id.tv_det_contenido);

        tv_det_nombre=view.findViewById(R.id.tv_det_nombre);
        tv_det_email=view.findViewById(R.id.tv_det_email);
        tv_det_url=view.findViewById(R.id.tv_det_url);
        tv_det_tags=view.findViewById(R.id.tv_det_tags);
        tv_det_phone=view.findViewById(R.id.tv_det_phone);

        tv_det_isLog=view.findViewById(R.id.tv_det_comment_islogged);

        tv_det_isLog.setVisibility(view.GONE);

        recyclerView_coment=view.findViewById(R.id.recycle_det_comentario);
        et_comentario=view.findViewById(R.id.et_det_comentario);
        btn_enviar_comment=view.findViewById(R.id.btn_det_enviar_comment);
        layout_comentario=view.findViewById(R.id.contenedor_inferior_comment);

        layout_det_nombre=view.findViewById(R.id.layout_det_nombre);
        layout_det_email=view.findViewById(R.id.layout_det_email);
        layout_det_url=view.findViewById(R.id.layout_det_url);
        layout_det_phone=view.findViewById(R.id.layout_det_phone);
        layout_det_tags=view.findViewById(R.id.layout_det_tags);

        // SOL VISIBLE CUANDO HAY DATOS
        layout_det_nombre.setVisibility(View.GONE);
        layout_det_url.setVisibility(View.GONE);
        layout_det_email.setVisibility(View.GONE);
        layout_det_phone.setVisibility(View.GONE);

        this.lista_comments =new ArrayList<>();
        //this.lista_comments.add(new Item_comment("esto es un comentario"));

        if (getArguments() != null) {
            Bundle b=getArguments();
            Item_estafa element= (Item_estafa) b.getSerializable(ELEMENT);
            Boolean isLoged=b.getBoolean(LOG_OK);
            String nombre_usuario=b.getString(NICK);

            if(element!=null){ // se ha pulsado un item-- envia 2 peticiones (los detalle, y comentarios de este item) al servidor
               this.id_estafa=element.getId();

               conexion = ConexionServer.getINSTANCE();
               conexion.enviarMensaje_alServidor(GET_DETALLES_ESTAFAS+SEPARADOR+Integer.toString(element.getId()));
               String ss=conexion.recibirMensaje_delServidor();

               conexion.enviarMensaje_alServidor(GET_COMMENTS_ESTAFA+SEPARADOR+Integer.toString(element.getId()));
               String comments=conexion.recibirMensaje_delServidor(); // 2022/03/09:otra m:n14:;2022/03/09:comentariod XX:n:;

                conexion.enviarMensaje_alServidor(GET_TAGS+SEPARADOR+id_estafa);
                String tags=conexion.recibirMensaje_delServidor();

               // Log.v(TAG_MAIN, " DETALLES ||------------TAGS: "+tags);
                if(!tags.equals("")){
                    layout_det_tags.setVisibility(View.VISIBLE);
                    tv_det_tags.setText(tags);
                }

                if(comments.contains(PUNTO_Y_COMA)){
                    String[] vstr=comments.split(PUNTO_Y_COMA);
                    String fecha="";
                    String nick_publica="";
                    String contenido="";

                    for (int i = 0; i <vstr.length ; i++) {
                        String[] tupla = vstr[i].split(SEPARADOR);
                        fecha=tupla[0];
                        contenido=tupla[1];
                        nick_publica=tupla[2];
                        this.lista_comments.add(new Item_comment(fecha, contenido, nick_publica));
                    }
                }

             //  setId_estafa(id_estafa);
               this.tv_det_nick.setText(""+nombre_usuario);
               this.tv_date.setText(element.getFecha());
               this.tv_det_titulo.setText(element.getTitulo());
               this.tv_det_category.setText(element.getCategory());
               this.tv_det_contenido.setText(element.getContenido());
                String[] str_claveValor=null;
                str_claveValor=ss.split(PUNTO_Y_COMA);

                if(str_claveValor!=null && str_claveValor.length>=2){

                    for(String s: str_claveValor){

                        int separa=s.indexOf(SEPARADOR_dats);
                        String clave=s.substring(0, separa);
                        String valor=s.substring(separa+1);
                     //   Log.v(TAG_MAIN, " DETALLES ||-----------separa: "+clave+" valor "+valor);

                    switch (clave){
                        case NOMBRE:
                            layout_det_nombre.setVisibility(View.VISIBLE);
                            tv_det_nombre.setText(valor);
                            break;
                        case EMAIL:
                            layout_det_email.setVisibility(View.VISIBLE);
                            tv_det_email.setText(valor);
                            break;
                        case URL:
                            layout_det_url.setVisibility(View.VISIBLE);
                            tv_det_url.setText(valor);
                            break;
                        case TELEFONO:
                            layout_det_phone.setVisibility(View.VISIBLE);
                            tv_det_phone.setText(valor);
                            break;
                    }

                    }
                }

           }

           if(isLoged){ // si esta loged se muestra layout poner comment
               layout_comentario.setVisibility(View.VISIBLE);
               tv_det_isLog.setVisibility(view.GONE);
           }else{
               layout_comentario.setVisibility(View.GONE);
               tv_det_isLog.setVisibility(view.VISIBLE);
               tv_det_isLog.setText(R.string.det_Logged);
           }

        }

        RecyclerView recyclerView=view.findViewById(R.id.recycle_det_comentario);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        listAdapter=new List_commentAdapter(this.lista_comments, getContext());
        recyclerView.setAdapter(listAdapter);



        btn_enviar_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date=new Date();
                // establece la fecha
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                String fecha_str = sdf.format(date);

                String comment=et_comentario.getText().toString();
                int id=getId_estafa();

                String msg= COMENTARIO +SEPARADOR+comment+SEPARADOR+Integer.toString(id)+SEPARADOR+fecha_str;

                if(!TextUtils.isEmpty(msg)){
                    String respuestaServer= interface_comunica.icomunicacion(msg, R.id.btn_det_enviar_comment);
                    Log.v(TAG_MAIN, "||------------id_estafa : "+respuestaServer);

                }
            }
        });

        return view;
    }

    public int getId_estafa() {
        return id_estafa;
    }

    public void setId_estafa(int id_estafa) {
        this.id_estafa = id_estafa;
    }

    //   comprueba q la Act  impl la IF del fragment
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ListEstafaFragment.iEnvio) {
            elCallback = (ListEstafaFragment.iEnvio)  context;
        } else {
            throw new RuntimeException(context.toString()
                    + " debe implement iEnvio");
        }

        if(context instanceof Activity){
            this.activity= (Activity) context;
            interface_comunica = (Interface_comunica) this.activity; // q sea igual q ese contect
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        elCallback = null;
        interface_comunica = null;
    }



}