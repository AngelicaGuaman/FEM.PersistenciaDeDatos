package es.upm.miw.SolitarioCelta;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    JuegoCelta mJuego;
    private final String CLAVE_TABLERO = "TABLERO_SOLITARIO_CELTA";
    private final String FILE = "partidaFile.txt";
    private String partida = "";
    private int numFichas = 0;
    private final String PARTIDA_INICIAL = "0011100001110011111111110111111111100111000011100";

    PuntuacionRepository puntuacionRepository;
    TextView nombreJugadorFichasText;

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
        nombreJugadorFichasText = findViewById(R.id.nombreJugadorFichasText);

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
                    nombreJugadorFichasText.setText(getString(R.string.nombreJugadorNumFichasText, getNombreJugador(), String.valueOf(mJuego.getNumFichas())));
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
                leerPartidaFichas();
                if (partida != null) {
                    if (!partida.equals(PARTIDA_INICIAL) && !partida.equals(mJuego.serializaTablero())) {
                        dialogFragment = new LoadDialogFragment();
                        dialogFragment.show(getFragmentManager(), "LOAD DIALOG");
                    } else {
                        cargarPartida(partida);
                    }

                }
                return true;
            case R.id.reiniciar:
                if (!PARTIDA_INICIAL.equals(mJuego.serializaTablero())) {
                    dialogFragment = new ResetDialogFragment();
                    dialogFragment.show(getFragmentManager(), "RESTART DIALOG");
                } else {
                    Toast.makeText(this, getString(R.string.txtEstadoInicial), Toast.LENGTH_LONG).show();
                }

                return true;
            case R.id.mejoresResultados:
                ArrayList<Puntuacion> arrayPuntuacion = this.puntuacionRepository.getAll();
                Log.i("MIW: ", "Recogiendo datos de la bbdd");
                if (!arrayPuntuacion.isEmpty()) {
                    Intent intent = new Intent(this, MejoresResultados.class);
                    intent.putParcelableArrayListExtra("Puntuacion", arrayPuntuacion);
                    startActivity(intent);
                } else {
                    Log.i("MIW: ", "No hay registros en la base de datos");
                    Toast.makeText(this, getString(R.string.noHayRegistros), Toast.LENGTH_LONG).show();
                }

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
            fileOutputStream.write(String.valueOf(mJuego.getNumFichas()).getBytes());
            fileOutputStream.write('\n');
            fileOutputStream.close();
            Log.i("MIW: ", "Guardando el fichero...");
        } catch (IOException e) {
            Log.e("MIW: ", "FILE I/O ERROR" + e.getMessage());
            e.printStackTrace();
        }
    }

    public void leerPartidaFichas() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(openFileInput(FILE)));

            partida = bufferedReader.readLine();
            numFichas = Integer.parseInt(bufferedReader.readLine());

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
    }

    public void cargarPartida(String partida) {
        mJuego.deserializaTablero(partida);
        mostrarTablero();
        Toast.makeText(this, getString(R.string.restoreGameText), Toast.LENGTH_LONG).show();
        nombreJugadorFichasText.setText(getString(R.string.nombreJugadorNumFichasText, getNombreJugador(), String.valueOf(numFichas)));
    }

    public String getPartida() {
        return partida;
    }

    public String getNombreJugador() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        return preferences.getString("playerName", "jugadorPorDefecto");
    }

    public void guardarPuntuacion() {
        int puntuacionFinal = mJuego.getPuntuacionTotal();
        long idJugador = puntuacionRepository.add(getNombreJugador(), puntuacionFinal);
        Log.i("MIW: Puntuación", String.valueOf(puntuacionFinal));
        Log.i("MIW: idJugador", String.valueOf(idJugador));
    }

    @Override
    protected void onDestroy() {
        puntuacionRepository.close();
        super.onDestroy();
    }
}
