package angela.montoya.app_proyect.ui.gallery.listado;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;



import java.util.ArrayList;
import java.util.List;

import angela.montoya.app_proyect.R;
import angela.montoya.app_proyect.ui.gallery.Item_estafa;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>{
    private List<Item_estafa> list_itemEstafas;
    private List<Item_estafa> list_item_all;

    private LayoutInflater layoutInflater;
    private Context context;

    private OnItemClickListenerX elListener;

    public ListAdapter(List<Item_estafa> itemList, int list_items, Context context,
                       OnItemClickListenerX listener) {
        this.layoutInflater=LayoutInflater.from(context);
        this.list_itemEstafas = itemList;
        this.list_item_all =new ArrayList<>(list_itemEstafas);
        this.context = context;
        this.elListener=listener;
    }


    @NonNull
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=layoutInflater.inflate(R.layout.item_list, null);
        return new ListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListAdapter.ViewHolder holder, final int position) {
        holder.bindData(list_itemEstafas.get(position));

        final Item_estafa element= list_itemEstafas.get(position);
        holder.contenedor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               elListener.onItemClick(element,position);  // El puente

            }
        });

    }

    //IF
    public interface  OnItemClickListenerX{
        void onItemClick(Item_estafa item, int position);
    }

    @Override
    public int getItemCount() {
        return list_itemEstafas.size();
    }

 /*   public void setItems(List<Item_estafa> itemList){
        this.list_itemEstafas =itemList;
    }*/


    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView titulo, category, fecha;
        LinearLayout contenedor;
        ViewHolder(View itemView){
            super(itemView);
            imageView=itemView.findViewById(R.id.iconoImageView);
            titulo =itemView.findViewById(R.id.tv_titulo);
            category =itemView.findViewById(R.id.tv_det_category);
            category =itemView.findViewById(R.id.tv_categoriax);
            fecha =itemView.findViewById(R.id.tv_fecha);
            contenedor= itemView.findViewById(R.id.contenedor_item);
        }

        void bindData(final Item_estafa item){
            imageView.setColorFilter(Color.parseColor(item.getColor()), PorterDuff.Mode.SRC_IN);
            titulo.setText(item.getTitulo());
            category.setText(item.getCategory());
            fecha.setText(item.getFecha());
        }

    }


}






