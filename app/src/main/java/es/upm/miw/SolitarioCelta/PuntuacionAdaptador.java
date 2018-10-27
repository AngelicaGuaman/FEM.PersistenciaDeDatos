package es.upm.miw.SolitarioCelta;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class PuntuacionAdaptador extends ArrayAdapter {

    private Context contexto;
    private List<Puntuacion> listaPuntuacion;
    private int idRecursoLayout;

    public PuntuacionAdaptador(Context context, int resource, List<Puntuacion> listaPuntuacion) {
        super(context, resource, listaPuntuacion);
        this.contexto = context;
        this.idRecursoLayout = resource;
        this.listaPuntuacion = listaPuntuacion;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout vista;

        if (null != convertView) {
            vista = (LinearLayout) convertView;
        } else {
            LayoutInflater inflador = (LayoutInflater) contexto
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            vista = (LinearLayout) inflador.inflate(idRecursoLayout, parent, false);
        }

        Puntuacion puntuacionObject = this.listaPuntuacion.get(position);
        TextView idPartida = (TextView) vista.findViewById(R.id.idPartida);
        idPartida.setText(String.valueOf(puntuacionObject.getId()));

        TextView nombreJugador = (TextView) vista.findViewById(R.id.nombreJugador);
        nombreJugador.setText(puntuacionObject.getGamerName());

        TextView puntuacion = (TextView) vista.findViewById(R.id.puntuacion);
        puntuacion.setText(String.valueOf(puntuacionObject.getPuntuacion()));

        TextView fecha = (TextView) vista.findViewById(R.id.fecha);
        fecha.setText(String.valueOf(puntuacionObject.getDate()));


        return vista;
    }
}
