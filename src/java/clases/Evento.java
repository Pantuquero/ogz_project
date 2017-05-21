package clases;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Pantuquero
 */
public class Evento {
    
    private int identificador;
    private Calendar fecha_inicio;
    private Calendar fecha_fin;
    private String juego;
    private ArrayList<String> asistentes;
    
    public Evento(int identificador, Calendar fecha_inicio, Calendar fecha_fin, String juego, ArrayList<String> asistentes){
        this.identificador = identificador;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.juego = juego;
        this.asistentes = asistentes;
    }
    
    public int getIdentificador(){
        return this.identificador;
    }
    
    public Calendar getFechaInicio(){
        return this.fecha_inicio;
    }
    
    public Calendar getFechaFin(){
        return this.fecha_fin;
    }
    
    public String getJuego(){
        return this.juego;
    }
    
    public ArrayList<String> getAsistentes(){
        return this.asistentes;
    }
    
}
