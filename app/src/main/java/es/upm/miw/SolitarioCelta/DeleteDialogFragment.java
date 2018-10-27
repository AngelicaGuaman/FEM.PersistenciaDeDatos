package es.upm.miw.SolitarioCelta;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class DeleteDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final MejoresResultados mejoresResultados = (MejoresResultados) getActivity();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.txtDialogoBorrarTitulo);
        builder.setMessage(R.string.txtDialogoBorrarPregunta);
        builder.setPositiveButton(
                getString(R.string.txtDialogoBorrarAfirmativo),
                new DialogInterface.OnClickListener() {
                    @SuppressLint("NewApi")
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PuntuacionRepository puntuacionRepository = new PuntuacionRepository(mejoresResultados.getApplicationContext());
                        puntuacionRepository.deleteAll();
                        Log.i("MIW:", getString(R.string.txtDialogoBorrarText));
                        Toast.makeText(getContext(), getString(R.string.txtDialogoBorrarText), Toast.LENGTH_LONG).show();
                        mejoresResultados.finish();
                    }
                }
        ).setNegativeButton(
                getString(R.string.txtDialogoBorrarNegativo),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }
        );

        return builder.create();
    }
}
