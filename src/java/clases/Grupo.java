package clases;

import java.util.ArrayList;

/**
 *
 * @author alvar
 */
public class Grupo {
    
    private String nombre;
    private ArrayList<Evento> eventos;

    public String getNombre(){
        return this.nombre;
    }
    
    public ArrayList<Evento> getEventos(){
        return this.eventos;
    }
    
}
