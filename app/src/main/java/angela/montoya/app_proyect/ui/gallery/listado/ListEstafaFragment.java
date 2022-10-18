package angela.montoya.app_proyect.ui.gallery.listado;

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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.ViewModelProvider;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import angela.montoya.app_proyect.R;
import angela.montoya.app_proyect.ui.gallery.Item_estafa;

import static angela.montoya.app_proyect.utils.Protocolo.SEPARADOR;
import static angela.montoya.app_proyect.utils.Protocolo.TAG_MAIN;

public class ListEstafaFragment extends Fragment {

private List<Item_estafa>lista;

    private ListEstafaViewModel galleryViewModel;
    private ListAdapter listAdapter;
    private iEnvio elCallback; // obj IF

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public static Fragment newInstace() {
        return new ListEstafaFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(ListEstafaViewModel.class);
         final View view = inflater.inflate(R.layout.fragment_list_estafa, container, false);


        RecyclerView recyclerView=view.findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));



//------------optengo lo enviado desde R.id.btn_enviar_estafa
        Bundle b=getArguments();
        if(b!=null){
            this.lista=new ArrayList<>();
            String str_list=b.getString("LIST");
            if(str_list!=null){
                String[] vStr=str_list.split(";");
                String str_id="";
                String titulo="";
                String category="";
                String fecha="";
                String contenido="";

                for (int i = 0; i <vStr.length ; i++) {
                    String [] tupla=vStr[i].split(SEPARADOR);
                    str_id=tupla[0];
                    titulo=tupla[1];
                    fecha=tupla[2];
                    contenido=tupla[3];
                    category=tupla[4];
                    if(!TextUtils.isEmpty(str_list)){ //String color, String titulo, String category, String fecha, String contenido
                        this.lista.add(new Item_estafa(Integer.parseInt(str_id), getColor(), titulo, category, fecha, contenido));
                    }
                }

            }

        }
       //listAdapter=new ListAdapter(this.lista, getContext());
        listAdapter=new ListAdapter(this.lista, R.layout.item_list, getContext(),
                new ListAdapter.OnItemClickListenerX(){

                    @Override
                    public void onItemClick(Item_estafa element, int position) {
                      /*  CharSequence text = "Ha pasado algo ..."+element.getFecha();
                        int duration = Toast.LENGTH_SHORT;
                        Context context = getContext();
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.setGravity(Gravity.TOP|Gravity.LEFT, 0, 0);
                        toast.show();*/
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




//prueba
    private void rellenaLista() {
        this.lista.add(new Item_estafa(1,getColor(), "pedro", "WEB", "activo", "Hola. Yo tengo el mismo problema. He pedido un espejo de 156 euros y ni me cogen el teléfono, ni me contestan al correo. Han pasado ya 15 días y el espejo ponía que lo tenían disponible. No he recibido ningún correo diciéndome nada, ni siquiera la fecha estimada de que me lo entreguen.\n" +
                "También reclamaré a mi banco que actúen contra ellos, me devuelvan el dinero ya que tienen un seguro de compras por Internet.\n" +
                "Paypal lo han quitado como medio de pago porque Paypal, les obligaba a devolver el dinero."));
        this.lista.add(new Item_estafa(2,getColor(), "ana", "WEB", "Des_Activo", "Hola. Yo tengo el mismo problema. He pedido un espejo de 156 euros y ni me cogen el teléfono, ni me contestan al correo. Han pasado ya 15 días y el espejo ponía que lo tenían disponible. No he recibido ningún correo diciéndome nada, ni siquiera la fecha estimada de que me lo entreguen.\n" +
                "El próximo lunes me pasaré por la Policía Nacional a interponer una denuncia por presunta estafa. Visto las quejas de esta web, es lo que toca.\n" +
                "También reclamaré a mi banco que actúen contra ellos, me devuelvan el dinero ya que tienen un seguro de compras por Internet.\n" +
                "Paypal lo han quitado como medio de pago porque Paypal, les obligaba a devolver el dinero."));
        this.lista.add(new Item_estafa(3,getColor(), "leo", "WEB", "activo", "Hola. Yo tengo el mismo problema. He pedido un espejo de 156 euros y ni me cogen el teléfono, ni me contestan al correo. Han pasado ya 15 días y el espejo ponía que lo tenían disponible. No he recibido ningún correo diciéndome nada, ni siquiera la fecha estimada de que me lo entreguen.\n" +
                "El próximo lunes me pasaré por la Policía Nacional a interponer una denuncia por presunta " ));
    }

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


    //   comprueba q la Act  impl la IF del fragment
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


// IF  q hay q Imple en Main para
public interface iEnvio {
    public void iEnviarElemento(Item_estafa element);
}

    //----------------------------------Filtrado-- no filtra-- no se q fallo tiene

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        MenuItem searchItem = menu.findItem(R.id.action_searchcategory);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                // playaAdapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText == null || newText.trim().isEmpty()) {
                    // pasando 'a' evito q no salga nada
                    listAdapter.getFilter().filter("a");
                    return false;
                }
                listAdapter.getFilter().filter(newText);
                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                return false;
            }
        });
        super.onPrepareOptionsMenu(menu);
    }
    
    //---------fin filtrado
}