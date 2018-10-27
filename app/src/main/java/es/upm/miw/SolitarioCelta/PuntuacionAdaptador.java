package es.upm.miw.SolitarioCelta;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import java.util.List;

public class PuntuacionAdaptador extends ArrayAdapter {

    private Context contexto;
    private List<Puntuacion> listaPuntuacion;
    private int idRecursoLayout;

    public PuntuacionAdaptador(@NonNull Context context, int resource, @NonNull List<Puntuacion> listaPuntuacion) {
        super(context, resource, listaPuntuacion);
        this.contexto = context;
        this.idRecursoLayout = resource;
        this.listaPuntuacion = listaPuntuacion;
    }
}
