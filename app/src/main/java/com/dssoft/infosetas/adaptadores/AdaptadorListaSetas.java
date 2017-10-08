package com.dssoft.infosetas.adaptadores;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.dssoft.infosetas.R;
import com.dssoft.infosetas.pojos.Seta;
import com.squareup.picasso.Picasso;
import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Angel on 24/05/2017.
 */

public class AdaptadorListaSetas extends ArrayAdapter<Seta>
{

    private Activity context;
    private List<Seta> listaSetas;


    public AdaptadorListaSetas(@NonNull Activity context, @NonNull List<Seta> listaSetas)
    {
        super(context, R.layout.layout_lista_setas, listaSetas);

        this.context = context;
        this.listaSetas = listaSetas;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {

        View fila = convertView;
        ViewHolder holder;

        if(fila != null)
        {
           holder = (ViewHolder) fila.getTag();

        }else
        {
            LayoutInflater inflater = context.getLayoutInflater();
            fila = inflater.inflate(R.layout.layout_lista_setas, parent, false);
            holder = new ViewHolder(fila);
            fila.setTag(holder);
        }

        if(listaSetas.get(position).getSpanableNombre()!= null)
        {
            holder.txtNombreSeta.setText(listaSetas.get(position).getSpanableNombre());
        }else
        {
            holder.txtNombreSeta.setText(listaSetas.get(position).getNombre());
        }

        if(listaSetas.get(position).getSpanableNombreComun() != null)
        {
            holder.txtNombreSetaComun.setText(listaSetas.get(position).getSpanableNombreComun());
        }else
        {
            holder.txtNombreSetaComun.setText(listaSetas.get(position).getNombre_comun());
        }

        //Picasso.with(context).load(listaSetas.get(position).getFoto_lista()).into(holder.imgSeta);
        holder.imgSeta.setBackgroundResource(listaSetas.get(position).getFoto_lista());


        //Se comprueba que tipo de seta es para mostrar el icono adecuado
        switch(listaSetas.get(position).getComestible())
        {

            case ("sin_interes"): Picasso.with(context).load(R.drawable.seta_regular_small).into(holder.imgSetaMario);

                                  break;

            case ("precaucion"): Picasso.with(context).load(R.drawable.cuidado_small).into(holder.imgSetaMario);

                                 break;

            case ("toxica"): Picasso.with(context).load(R.drawable.seta_venenosa_small).into(holder.imgSetaMario);

                             break;

            case ("mortal"): Picasso.with(context).load(R.drawable.skull_ico).into(holder.imgSetaMario);

                             break;

            default: Picasso.with(context).load(R.drawable.seta_buena_small).into(holder.imgSetaMario);
                     break;

        }


        //Se comprueba si la seta esta en la lista de favoritas, para mostrar el icono de la estrella
       if(listaSetas.get(position).isFavorita())
       {
            holder.imgFavorita.setVisibility(View.VISIBLE);

       }else
       {
            holder.imgFavorita.setVisibility(View.GONE);
       }

        return fila;

    }


    static class ViewHolder
    {

        @BindView(R.id.txtList_nombre_seta) TextView txtNombreSeta;
        @BindView(R.id.txtList_nombre_seta_comun) TextView txtNombreSetaComun;
        @BindView(R.id.imageView_ListaSetas) ImageView imgSeta;
        @BindView(R.id.imageView_seta_mario) ImageView imgSetaMario;
        @BindView(R.id.imageView_favorita) ImageView imgFavorita;

        public ViewHolder(View view)
        {
            ButterKnife.bind(this, view);
        }
    }
}
