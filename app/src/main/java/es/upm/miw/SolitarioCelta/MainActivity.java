package es.upm.miw.SolitarioCelta;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    JuegoCelta mJuego;
    private final String CLAVE_TABLERO = "TABLERO_SOLITARIO_CELTA";
    private final String FILE = "partidaFile.txt";
    private String partida = "";
    PuntuacionRepository puntuacionRepository;

    private final int[][] ids = {
            {0, 0, R.id.p02, R.id.p03, R.id.p04, 0, 0},
            {0, 0, R.id.p12, R.id.p13, R.id.p14, 0, 0},
            {R.id.p20, R.id.p21, R.id.p22, R.id.p23, R.id.p24, R.id.p25, R.id.p26},
            {R.id.p30, R.id.p31, R.id.p32, R.id.p33, R.id.p34, R.id.p35, R.id.p36},
            {R.id.p40, R.id.p41, R.id.p42, R.id.p43, R.id.p44, R.id.p45, R.id.p46},
            {0, 0, R.id.p52, R.id.p53, R.id.p54, 0, 0},
            {0, 0, R.id.p62, R.id.p63, R.id.p64, 0, 0}
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        puntuacionRepository = new PuntuacionRepository(getApplicationContext());

        mJuego = new JuegoCelta();
        mostrarTablero();
    }

    /**
     * Se ejecuta al pulsar una posición
     *
     * @param v Vista de la posición pulsada
     */
    public void posicionPulsada(View v) {
        String resourceName = getResources().getResourceEntryName(v.getId());
        int i = resourceName.charAt(1) - '0';
        int j = resourceName.charAt(2) - '0';

        mJuego.jugar(i, j);

        mostrarTablero();
        if (mJuego.juegoTerminado()) {
            guardarPuntuacion();
            new AlertDialogFragment().show(getFragmentManager(), "ALERT DIALOG");
        }
    }

    /**
     * Visualiza el tablero
     */
    public void mostrarTablero() {
        RadioButton button;

        for (int i = 0; i < JuegoCelta.TAMANIO; i++)
            for (int j = 0; j < JuegoCelta.TAMANIO; j++)
                if (ids[i][j] != 0) {
                    button = findViewById(ids[i][j]);
                    button.setChecked(mJuego.obtenerFicha(i, j) == 1);
                }
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putString(CLAVE_TABLERO, mJuego.serializaTablero());
        super.onSaveInstanceState(outState);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String grid = savedInstanceState.getString(CLAVE_TABLERO);
        mJuego.deserializaTablero(grid);
        mostrarTablero();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.opciones_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        DialogFragment dialogFragment;
        switch (item.getItemId()) {
            case R.id.menuAbout:
                startActivity(new Intent(this, About.class));
                return true;
            case R.id.preferences:
                startActivity(new Intent(this, SCeltaPreferences.class));
                return true;
            case R.id.guardar:
                guardarPartida();
                return true;
            case R.id.cargar:
                partida = leerPartida();
                if (partida != null) {
                    if (!partida.equals(mJuego.serializaTablero())) {
                        dialogFragment = new LoadDialogFragment();
                        dialogFragment.show(getFragmentManager(), "LOAD DIALOG");
                    } else {
                        cargarPartida(partida);
                    }
                }
                return true;
            case R.id.reiniciar:
                dialogFragment = new ResetDialogFragment();
                dialogFragment.show(getFragmentManager(), "LOAD DIALOG");
                return true;
            case R.id.mejoresResultados:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void guardarPartida() {
        Bundle bundle = new Bundle();
        bundle.putString(CLAVE_TABLERO, mJuego.serializaTablero());

        String tableroSerializado = bundle.getString(CLAVE_TABLERO);
        guardarPartidaEnElFichero(tableroSerializado);

        Toast.makeText(this, getString(R.string.savedGameText), Toast.LENGTH_LONG).show();
    }

    public void guardarPartidaEnElFichero(String tableroSerializado) {
        try {
            FileOutputStream fileOutputStream = openFileOutput(FILE, Context.MODE_PRIVATE);
            fileOutputStream.write(tableroSerializado.getBytes());
            fileOutputStream.write('\n');
            fileOutputStream.close();
            Log.i("MIW: ", "Guardando el fichero...");
        } catch (IOException e) {
            Log.e("MIW: ", "FILE I/O ERROR" + e.getMessage());
            e.printStackTrace();
        }
    }

    public String leerPartida() {
        String line = "";

        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(openFileInput(FILE)));

            line = bufferedReader.readLine();

            /*while (line != null) {
                line = bufferedReader.readLine();
                bundle.putString(CLAVE_TABLERO, mJuego.serializaTablero());
            }*/

            Log.i("MIW: ", "Puntuacion leída...");

            bufferedReader.close();

        } catch (FileNotFoundException e) {
            Toast.makeText(this, getString(R.string.fileNotFoundText), Toast.LENGTH_LONG).show();
            Log.e("MIW: ", "FILE I/O ERROR: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            Toast.makeText(this, getString(R.string.fileEmptyText), Toast.LENGTH_LONG).show();
            Log.e("MIW: ", "FILE EMPTY: " + e.getMessage());
            e.printStackTrace();
        }

        return line;
    }

    public void cargarPartida(String partida) {
        mJuego.deserializaTablero(partida);
        mostrarTablero();
        Toast.makeText(this, getString(R.string.restoreGameText), Toast.LENGTH_LONG).show();
    }

    public String getPartida() {
        return partida;
    }


    public void guardarPuntuacion() {
        int puntuacionFinal = mJuego.getPuntuacionTotal();
        long idJugador = puntuacionRepository.add("Angelica", puntuacionFinal);
        Log.i("MIW: Puntuación", String.valueOf(puntuacionFinal));
        Log.i("MIW: idJugador", String.valueOf(idJugador));
    }

    @Override
    protected void onDestroy() {
        puntuacionRepository.close();
        super.onDestroy();
    }
}
