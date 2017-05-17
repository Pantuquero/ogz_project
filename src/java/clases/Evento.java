package clases;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Pantuquero
 */
public class Evento {
    
    private Date fecha;
    private String juego;
    private ArrayList<Usuario> asistentes;
    
    public Date getFecha(){
        return this.fecha;
    }
    
    public String getJuego(){
        return this.juego;
    }
    
    public ArrayList<Usuario> getAsistentes(){
        return this.asistentes;
    }
    
}
