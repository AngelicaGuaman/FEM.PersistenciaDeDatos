package es.upm.miw.SolitarioCelta;

import android.os.Parcel;
import android.os.Parcelable;

public class Puntuacion implements Parcelable {

    private int id;

    private String nombreJugador;

    private String fecha;

    private int puntuacion;

    public Puntuacion(int id, String nombreJugador, String fecha, int puntuacion) {
        this.id = id;
        this.nombreJugador = nombreJugador;
        this.fecha = fecha;
        this.puntuacion = puntuacion;
    }

    protected Puntuacion(Parcel in) {
        id = in.readInt();
        nombreJugador = in.readString();
        fecha = in.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGamerName() {
        return nombreJugador;
    }

    public void setGamerName(String gamerName) {
        this.nombreJugador = gamerName;
    }

    public String getDate() {
        return fecha;
    }

    public void setDate(String fecha) {
        this.fecha = fecha;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public static final Creator<Puntuacion> CREATOR = new Creator<Puntuacion>() {
        @Override
        public Puntuacion createFromParcel(Parcel in) {
            return new Puntuacion(in);
        }

        @Override
        public Puntuacion[] newArray(int size) {
            return new Puntuacion[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nombreJugador);
        dest.writeString(fecha);
        dest.writeInt(puntuacion);
    }

}
