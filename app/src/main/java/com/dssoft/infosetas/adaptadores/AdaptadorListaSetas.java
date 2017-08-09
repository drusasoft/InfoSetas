package com.dssoft.infosetas.adaptadores;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

        holder.txtNombreSeta.setText(listaSetas.get(position).getNombre());
        holder.txtNombreSetaComun.setText(listaSetas.get(position).getNombre_comun());
        //Picasso.with(context).load(listaSetas.get(position).getFoto_lista()).into(holder.imgSeta);
        holder.imgSeta.setBackgroundResource(listaSetas.get(position).getFoto_lista());

        if(listaSetas.get(position).getComestible().equals("toxica"))
        {
            Picasso.with(context).load(R.drawable.seta_venenosa_small).into(holder.imgSetaMario);
        }else
        {
            if(listaSetas.get(position).getComestible().equals("mortal"))
            {
                Picasso.with(context).load(R.drawable.skull_ico).into(holder.imgSetaMario);
            }else
            {
                Picasso.with(context).load(R.drawable.seta_buena_small).into(holder.imgSetaMario);
            }
        }

        return fila;

    }


    static class ViewHolder
    {

        @BindView(R.id.txtList_nombre_seta) TextView txtNombreSeta;
        @BindView(R.id.txtList_nombre_seta_comun) TextView txtNombreSetaComun;
        @BindView(R.id.imageView_ListaSetas) ImageView imgSeta;
        @BindView(R.id.imageView_seta_mario) ImageView imgSetaMario;

        public ViewHolder(View view)
        {
            ButterKnife.bind(this, view);
        }
    }
}
