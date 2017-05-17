package clases;

import clases.ConversorMD5;
import java.util.ArrayList;


/**
 * Usuario object
 * @author Pantuquero
 */
public class Usuario {
    
    private String email;
    private String nombre;
    private String contrasena;
    private ArrayList<Grupo> grupos;
    
    /**
     * Creates a new Usuario with the cyphrated password.
     * @param email String
     * @param nombre String
     * @param contrasena String
     */
    public Usuario(String email, String nombre, String contrasena) {
        
        this.email = email;
        this.nombre = nombre;
        this.contrasena = contrasena;
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
}
