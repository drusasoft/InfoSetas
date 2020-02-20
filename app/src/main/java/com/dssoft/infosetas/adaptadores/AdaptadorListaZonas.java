package com.dssoft.infosetas.adaptadores;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.dssoft.infosetas.R;
import com.dssoft.infosetas.pojos.Zona;
import java.util.List;
import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Angel on 24/02/2018.
 */

public class AdaptadorListaZonas extends ArrayAdapter<Zona>
{

    private Activity context;
    private List<Zona> listZonas;

    public AdaptadorListaZonas(@NonNull Activity context, @NonNull List<Zona> listZonas)
    {
        super(context, R.layout.layout_lista_zonas, listZonas);

        this.context = context;
        this.listZonas = listZonas;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {

        View fila = convertView;
        ViewHolder holder;

        if(fila == null)
        {
            LayoutInflater inflater = context.getLayoutInflater();
            fila = inflater.inflate(R.layout.layout_lista_zonas, parent, false);
            holder = new ViewHolder(fila);
            fila.setTag(holder);

        }else
        {
            holder = (ViewHolder) fila.getTag();
        }

        holder.txtNombreZona.setText(listZonas.get(position).getNombre());
        holder.txtDescZona.setText(listZonas.get(position).getDescripcion());

        if(listZonas.get(position).isSeleccionado())
        {
            holder.txtNombreZona.setTextColor(holder.colorNaranja);
            holder.txtDescZona.setTextColor(holder.colorNaranja);
            holder.layoutListaZona.setBackgroundResource(R.drawable.borde_lista_zona_selec);
            holder.layoutSeparadorZona.setBackgroundResource(R.drawable.borde_lista_zona_selec2);

        }else
        {
            holder.txtNombreZona.setTextColor(context.getResources().getColor(R.color.texto_pulsacion));
            holder.txtDescZona.setTextColor(context.getResources().getColor(R.color.texto_pulsacion));
            holder.layoutListaZona.setBackgroundResource(R.drawable.borde_lista_zonas);
            holder.layoutSeparadorZona.setBackgroundResource(R.drawable.borde_lista_zonas2);
        }

        return fila;
    }

    static class ViewHolder
    {
        @BindView(R.id.layout_lista_zona) LinearLayout layoutListaZona;
        @BindView(R.id.layout_separador_lista_zona) LinearLayout layoutSeparadorZona;
        @BindView(R.id.txtList_nombre_zona) TextView txtNombreZona;
        @BindView(R.id.txtList_desc_zona) TextView txtDescZona;
        @BindColor(R.color.colorPrimaryDarkOrange) int colorNaranja;

        public ViewHolder(View view)
        {
            ButterKnife.bind(this, view);
        }
    }
}
