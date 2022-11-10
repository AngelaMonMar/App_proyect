package angela.montoya.app_proyect.ui.gallery.listado;
import android.widget.SearchView;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.ViewModelProvider;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import angela.montoya.app_proyect.R;
import angela.montoya.app_proyect.ui.gallery.Item_estafa;
import angela.montoya.app_proyect.utils.Convertidor_fecha;

import static angela.montoya.app_proyect.utils.Protocolo.SEPARADOR;
import static angela.montoya.app_proyect.utils.Protocolo.TAG_MAIN;

public class ListEstafaFragment extends Fragment  implements SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener {

    private List<Item_estafa>lista;
    List<Item_estafa> listaFiltrada;

    private ListEstafaViewModel galleryViewModel;
    public ListAdapter listAdapter;
    private iEnvio elCallback; // obj IF
    private Context mContext;
    private TextView tv_empty;


    public ListAdapter getListAdapter() {
        return listAdapter;
    }
    RecyclerView recyclerView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        setHasOptionsMenu(true);

    }

    public static Fragment newInstace() {
        return new ListEstafaFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(ListEstafaViewModel.class);
         final View view = inflater.inflate(R.layout.fragment_list_estafa, container, false);

        recyclerView=view.findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        tv_empty = view.findViewById(R.id.tv_empty);
        View layout = inflater.inflate(R.layout.fragment_list_estafa, container, false);



//------------optengo lo enviado desde R.id.btn_enviar_estafa
        Bundle b=getArguments();
        if(b!=null){
            this.lista=new ArrayList<>();
            String str_list=b.getString("LIST");
            if(str_list!=null && str_list.length()>2){
                String[] vStr=str_list.split(";");
                String str_id="";
                String titulo="";
                String category="";
                String fecha="";
                String contenido="";
                String nombre_publico="";

                for (int i = 0; i <vStr.length ; i++) {
                    String [] tupla=vStr[i].split(SEPARADOR);
                    str_id=tupla[0];
                    titulo=tupla[1];
                    fecha=tupla[2];

                  //  Log.v(TAG_MAIN, "||---------------fechax : "+fecha);
                   // String fecha_convertida=getFechaConvertida(fecha);
                   // Log.v(TAG_MAIN, "||---------------fechau : "+fecha_convertida);


                    contenido=tupla[3];
                    category=tupla[4];
                    nombre_publico=tupla[5];
                    if(!TextUtils.isEmpty(str_list)){ //String color, String titulo, String category, String fecha, String contenido
                        this.lista.add(new Item_estafa(Integer.parseInt(str_id), getColor(), titulo, category,
                                fecha,//fecha
                                contenido, 0,nombre_publico ));
                    }
                }
            }else{
                tv_empty.setText("Sin resultados.");
            }

        }
        // item a la escucha
        listAdapter=new ListAdapter(this.lista, R.layout.item_list, getContext(),
                new ListAdapter.OnItemClickListenerX(){

                    @Override
                    public void onItemClick(Item_estafa element, int position) {
                        elCallback.iEnviarElemento(element);
                    }
                });
        recyclerView.setAdapter(listAdapter);



    /*    final TextView textView = root.findViewById(R.id.text_gallery);
        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        return view;
    }

    private String getFechaConvertida(String fecha) {
        String salida="";
        if(fecha.length()>14){// para q no casque fecha old
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
            String ahora=df.format(new Date());

            String fecha_publicacion= fecha;
            salida=Convertidor_fecha.findDifference(fecha_publicacion, ahora);
        }else{
            salida=fecha;
        }


        Log.v("-----------FECHA",salida );

        return salida;

    }

    // Menu Search
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("Buscar");

        super.onCreateOptionsMenu(menu, inflater);
    }

    // filtrado
    @Override
    public boolean onQueryTextSubmit(String s) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText == null || newText.trim().isEmpty()) {
            resetSearch();
            return false;
        }

        listaFiltrada =new ArrayList<>();
        for (Item_estafa value : this.lista) {
            if (value.getTitulo().toLowerCase().contains(newText.toLowerCase())) {
                listaFiltrada.add(value);//la add
            }
        }

        this.listAdapter=new ListAdapter(listaFiltrada, R.layout.item_list, getContext(),
                new ListAdapter.OnItemClickListenerX(){

                    @Override
                    public void onItemClick(Item_estafa element, int position) {
                        elCallback.iEnviarElemento(element);
                    }
                });
        this.recyclerView.setAdapter(this.listAdapter);
        return false;
    }

    public void resetSearch() {
        this.listAdapter=new ListAdapter(this.lista, R.layout.item_list, getContext(),
                new ListAdapter.OnItemClickListenerX(){

                    @Override
                    public void onItemClick(Item_estafa element, int position) {
                        elCallback.iEnviarElemento(element);
                    }
                });
        this.recyclerView.setAdapter(listAdapter);

    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        return true;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        return true;
    }
// fin filtrado


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public String getColor(){
        Random r=new Random();
        int n=r.nextInt(200_000-100_000+1)+100_000;
        return "#"+String.valueOf(n);
    }


    //  -- implements IF
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof iEnvio) {
            elCallback = (iEnvio)  context;
        } else {
            throw new RuntimeException(context.toString()
                    + " debe implement iEnvio");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        elCallback = null;
    }
    //--------------Fin onAttach y detach


    // IF  q hay q Imple en Main para
    public interface iEnvio {
        public void iEnviarElemento(Item_estafa element);
    }


}