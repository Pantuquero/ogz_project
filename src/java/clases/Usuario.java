package clases;

import clases.ConversorMD5;
import java.util.ArrayList;


/**
 * Usuario object
 * @author Pantuquero
 */
public class Usuario {
    
    private int identificador;
    private String email;
    private String nombre;
    private String contrasena;
    private ArrayList<Grupo> grupos;
    
    
    public Usuario(int identificador, String email, String nombre, String contrasena, ArrayList<Grupo> grupos) {
        this.identificador = identificador;
        this.email = email;
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.grupos = grupos;
    }
    
    public int getIdentificador(){
        return this.identificador;
    }
    
    public String getEmail(){
        return this.email;
    }
    
    public String getNombre(){
        return this.nombre;
    }
    
    public String getContrasena(){
        return this.contrasena;
    }
    
    public ArrayList<Grupo> getGrupos(){
        return this.grupos;
    }
    
    public void asignarGrupo(Grupo grupo){
        this.grupos.add(grupo);
    }
    
    public void desasignarGrupo(Grupo grupo){
        this.grupos.remove(grupo);
    }
    
    public void setGrupos(ArrayList<Grupo> grupos){
        this.grupos = grupos;
    }
}
