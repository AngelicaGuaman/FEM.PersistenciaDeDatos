package es.upm.miw.SolitarioCelta;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MejoresResultados extends AppCompatActivity implements ListView.OnItemClickListener {

    static final String TAG_MIW = "MIW";

    private ListView lvListado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // asociar recurso a la vista
        //lvListado = findViewById(R.id.lvListadoElementos);

        // Crear adaptador a partir de datos
        /*PuntuacionAdaptador adaptador = new PuntuacionAdaptador(
                this,
                R.layout.item_lista,
                getResources().getStringArray(R.array.misDatos)
        );*/

       // lvListado.setAdapter(adaptador);
        lvListado.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        StringBuilder texto = new StringBuilder("Opción elegida (")
                .append(String.valueOf(position))
                .append("): ")
                .append(parent.getItemAtPosition(position).toString());

        Log.i(TAG_MIW, texto.toString());
        //Snackbar.make(parent, texto, Snackbar.LENGTH_LONG).show();
    }
}
