package clases;

import java.util.ArrayList;

/**
 *
 * @author pantuquero
 */
public class Grupo {
    
    private int identificador;
    private String nombre;
    private ArrayList<Evento> eventos;
    
    public Grupo(int identificador, String nombre, ArrayList<Evento> eventos){
        this.identificador = identificador;
        this.nombre = nombre;
        this.eventos = eventos;
    }
    
    public int getIdentificador(){
        return this.identificador;
    }

    public String getNombre(){
        return this.nombre;
    }
    
    public ArrayList<Evento> getEventos(){
        return this.eventos;
    }
    
    public void asignarEvento(Evento evento){
        this.eventos.add(evento);
    }
}
