package angela.montoya.app_proyect.ui.gallery.listado;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import angela.montoya.app_proyect.R;
import angela.montoya.app_proyect.ui.gallery.Item_comment;

public class List_commentAdapter extends RecyclerView.Adapter<List_commentAdapter.ViewHolder> implements Filterable {
    private List<Item_comment> lista;
    private LayoutInflater layoutInflater;
    private Context context;

    public List_commentAdapter(List<Item_comment> lista, Context context) {
        this.lista = lista;
        this.layoutInflater=LayoutInflater.from(context);
        this.context = context;
    }


    @NonNull
    @Override
    public List_commentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=layoutInflater.inflate(R.layout.item_list_coment, null);
        return new List_commentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull List_commentAdapter.ViewHolder holder, int position) {
        holder.bindData(lista.get(position));
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_det_comment, tv_det_nick_coment, tv_det_fecha_coment;
        LinearLayout contenedor;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_det_comment=itemView.findViewById(R.id.tv_det_comment);
            tv_det_nick_coment=itemView.findViewById(R.id.tv_det_nick_coment);
            tv_det_fecha_coment=itemView.findViewById(R.id.tv_det_fecha_coment);
            contenedor= itemView.findViewById(R.id.contenedor_item_comment);
        }

        public void bindData(Item_comment item) {

            tv_det_comment.setText(item.getComment());
            tv_det_nick_coment.setText("Publicado por "+item.getNick());
            tv_det_fecha_coment.setText(item.getFecha());
        }
    }



    // caso que quiera filtar comentario-- No es necesario--.-quitar filterable
    @Override
    public Filter getFilter() {
        return null;
    }
}
