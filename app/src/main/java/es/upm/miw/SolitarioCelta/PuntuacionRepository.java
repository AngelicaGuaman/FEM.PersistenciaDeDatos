package es.upm.miw.SolitarioCelta;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static es.upm.miw.SolitarioCelta.PuntuacionContract.tablaPuntuacion;

public class PuntuacionRepository extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = tablaPuntuacion.TABLE_NAME + ".db";

    public PuntuacionRepository(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String consultaSQL = "CREATE TABLE " + tablaPuntuacion.TABLE_NAME + " ("
                + tablaPuntuacion.COL_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + tablaPuntuacion.COL_NAME_NOMBRE_JUGADOR + " TEXT, "
                + tablaPuntuacion.COL_NAME_FECHA + " TEXT, "
                + tablaPuntuacion.COL_NAME_PUNTUACION + " TEXT)";
        db.execSQL(consultaSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String consultaSQL = "DROP TABLE IF EXISTS " + tablaPuntuacion.TABLE_NAME;
        db.execSQL(consultaSQL);
        onCreate(db);
    }

    public long add(String nombreJugador, int puntuacion) {
        SQLiteDatabase db = this.getWritableDatabase();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

        ContentValues valores = new ContentValues();
        valores.put(tablaPuntuacion.COL_NAME_NOMBRE_JUGADOR, nombreJugador);
        valores.put(tablaPuntuacion.COL_NAME_FECHA, dateFormat.format(new Date()));
        valores.put(tablaPuntuacion.COL_NAME_PUNTUACION, puntuacion);

        return db.insert(tablaPuntuacion.TABLE_NAME, null, valores);
    }

    public long count() {
        SQLiteDatabase db = this.getReadableDatabase();
        return DatabaseUtils.queryNumEntries(db, tablaPuntuacion.TABLE_NAME);
    }

    public ArrayList<Puntuacion> getAll() {
        String consultaSQL = "SELECT * FROM " + tablaPuntuacion.TABLE_NAME + " ORDER BY " + tablaPuntuacion.COL_NAME_PUNTUACION;
        ArrayList<Puntuacion> listaPuntuacion = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(consultaSQL, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Puntuacion puntuacion = new Puntuacion(
                        cursor.getInt(cursor.getColumnIndex(tablaPuntuacion.COL_NAME_ID)),
                        cursor.getString(cursor.getColumnIndex(tablaPuntuacion.COL_NAME_NOMBRE_JUGADOR)),
                        cursor.getString(cursor.getColumnIndex(tablaPuntuacion.COL_NAME_FECHA)),
                        cursor.getInt(cursor.getColumnIndex(tablaPuntuacion.COL_NAME_PUNTUACION))
                );

                listaPuntuacion.add(puntuacion);
                cursor.moveToNext();
            }
        }

        cursor.close();
        db.close();

        return listaPuntuacion;
    }

    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + tablaPuntuacion.TABLE_NAME);
    }
}
