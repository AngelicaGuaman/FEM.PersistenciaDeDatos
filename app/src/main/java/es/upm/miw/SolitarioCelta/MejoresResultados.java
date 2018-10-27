package es.upm.miw.SolitarioCelta;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MejoresResultados extends Activity implements View.OnClickListener {

    static final String TAG_MIW = "MIW";

    private ListView lvListado;
    private Button borrarTodosBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mejores_resultados);

        lvListado = findViewById(R.id.lvListadoElementos);
        borrarTodosBtn = findViewById(R.id.borrarTodosBtn);

        ArrayList<Puntuacion> arrayPuntuacion = this.getIntent().getParcelableArrayListExtra("Puntuacion");

        PuntuacionAdaptador adaptador = new PuntuacionAdaptador(
                this,
                R.layout.item_lista,
                arrayPuntuacion
        );

        lvListado.setAdapter(adaptador);

        borrarTodosBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        DialogFragment dialogFragment = new DeleteDialogFragment();
        dialogFragment.show(getFragmentManager(), "DELETE DIALOG!");
    }
}
