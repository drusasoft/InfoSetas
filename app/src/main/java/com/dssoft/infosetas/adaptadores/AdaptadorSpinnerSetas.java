package com.dssoft.infosetas.adaptadores;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.dssoft.infosetas.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Angel on 19/06/2017.
 */

public class AdaptadorSpinnerSetas extends ArrayAdapter<String>
{

    private Activity context;
    private String[] arrayTipos;

    public AdaptadorSpinnerSetas(@NonNull Activity context, @NonNull String[] arrayTipos)
    {
        super(context, R.layout.spinner_item, arrayTipos);
        this.context = context;
        this.arrayTipos = arrayTipos;

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
            fila = inflater.inflate(R.layout.spinner_item, null);
            holder = new ViewHolder(fila);
            fila.setTag(holder);
        }

        holder.txtTipo.setText(arrayTipos[position]);

        switch (position)
        {
            case 0: holder.imgSpinner.setVisibility(View.GONE);
                    break;

            case 1: holder.imgSpinner.setImageResource(android.R.drawable.star_big_on);
                    holder.imgSpinner.setVisibility(View.VISIBLE);
                    break;

            case 2: holder.imgSpinner.setImageResource(R.drawable.seta_buena_small);
                    holder.imgSpinner.setVisibility(View.VISIBLE);
                    break;

            case 3: holder.imgSpinner.setImageResource(R.drawable.seta_regular_small);
                    holder.imgSpinner.setVisibility(View.VISIBLE);
                    break;

            case 4: holder.imgSpinner.setImageResource(R.drawable.cuidado_small);
                    holder.imgSpinner.setVisibility(View.VISIBLE);
                    holder.txtTipo.setText(R.string.txtPrecaución);
                    break;

            case 5: holder.imgSpinner.setImageResource(R.drawable.seta_venenosa_small);
                    holder.imgSpinner.setVisibility(View.VISIBLE);
                    break;

            case 6: holder.imgSpinner.setImageResource(R.drawable.skull_ico);
                    holder.imgSpinner.setVisibility(View.VISIBLE);
                    break;
        }


        return fila;
    }


    static class ViewHolder
    {

        @BindView(R.id.txtSpinnerSeta) TextView txtTipo;
        @BindView(R.id.imgSpinner) ImageView imgSpinner;

        public ViewHolder(View view)
        {
            ButterKnife.bind(this,view);
        }

    }

}
