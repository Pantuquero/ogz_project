package clases;

import clases.ConversorMD5;

public class Usuario {
    
    private String email;
    private String nombre;
    private String contrasena;
    
    public Usuario(String email, String nombre, String contrasena) {
        
        this.email = email;
        this.nombre = nombre;
        this.contrasena = ConversorMD5.convertirMD5(contrasena);
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
