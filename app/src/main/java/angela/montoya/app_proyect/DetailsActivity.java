package angela.montoya.app_proyect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import angela.montoya.app_proyect.ui.gallery.Item_comment;
import angela.montoya.app_proyect.ui.gallery.Item_estafa;
import angela.montoya.app_proyect.ui.gallery.listado.List_commentAdapter;
import angela.montoya.app_proyect.utils.Interface_comunica;

import static angela.montoya.app_proyect.utils.Protocolo.COMENTARIO;
import static angela.montoya.app_proyect.utils.Protocolo.SEPARADOR;
import static angela.montoya.app_proyect.utils.Protocolo.TAG_MAIN;

public class DetailsActivity extends AppCompatActivity {
    private TextView tv_det_titulo;
    private TextView tv_det_category;
    private TextView tv_det_contenido;
    private TextView tv_det_isLog;

    private RecyclerView recyclerView_coment;
    private Button btn_enviar_comment;
    private EditText et_comentario;



    private List_commentAdapter listAdapter;
    private List<Item_comment> lista_comments;

    private LinearLayout layout_comentario;
    private Item_estafa element;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);



        tv_det_titulo=findViewById(R.id.tv_det_titulo);
        tv_det_category=findViewById(R.id.tv_det_category);
        tv_det_contenido=findViewById(R.id.tv_det_contenido);
        tv_det_isLog=findViewById(R.id.tv_det_comment_islogged);

        tv_det_isLog.setVisibility(View.GONE);

        recyclerView_coment=findViewById(R.id.recycle_det_comentario);
        et_comentario=findViewById(R.id.et_det_comentario);
        btn_enviar_comment=findViewById(R.id.btn_det_enviar_comment);
        layout_comentario=findViewById(R.id.contenedor_inferior_comment);

        this.lista_comments =new ArrayList<>();
        this.lista_comments.add(new Item_comment("esto es un comentario"));

        element =null;
        element = (Item_estafa) getIntent().getExtras().get(MainActivity.KEY);
        boolean islogged= (boolean) getIntent().getExtras().get(MainActivity.ISLOGGED);




            if(element!=null){
                this.tv_det_titulo.setText(element.getTitulo());
                this.tv_det_category.setText(element.getCategory());
                this.tv_det_contenido.setText(element.getContenido());
            }


            if(islogged){ // si esta loged se muestra layout poner comment
                layout_comentario.setVisibility(View.VISIBLE);
                tv_det_isLog.setVisibility(View.GONE);
            }else{
                layout_comentario.setVisibility(View.GONE);
                tv_det_isLog.setVisibility(View.VISIBLE);
                tv_det_isLog.setText(R.string.det_Logged);
            }





        RecyclerView recyclerView=findViewById(R.id.recycle_det_comentario);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listAdapter=new List_commentAdapter(this.lista_comments, this);
        recyclerView.setAdapter(listAdapter);


        btn_enviar_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG_MAIN, "||------------btn_enviar_comment : ");
                String comment=et_comentario.getText().toString();

                // hay q add este elemento a la BD
                String msg= COMENTARIO +SEPARADOR+comment ;
                String respuestaServer= interface_comunica.icomunicacion(msg, R.id.btn_det_enviar_comment);

                // si comentario es validado--
                // Se add al RV
            }
        });

    }
    private Interface_comunica interface_comunica;
}