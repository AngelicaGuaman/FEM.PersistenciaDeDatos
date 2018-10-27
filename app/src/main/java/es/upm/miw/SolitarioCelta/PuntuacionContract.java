package es.upm.miw.SolitarioCelta;

import android.provider.BaseColumns;

final public class PuntuacionContract {

    public PuntuacionContract() {
    }

    public static abstract class tablaPuntuacion implements BaseColumns {
        public static final String TABLE_NAME = "puntuacion";

        public static final String COL_NAME_ID = _ID;
        public static final String COL_NAME_NOMBRE_JUGADOR = "nombreJugador";
        public static final String COL_NAME_FECHA = "fecha";
        public static final String COL_NAME_PUNTUACION = "puntuacion";
    }
}
