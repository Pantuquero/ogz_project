package clases;

import clases.Conexion;
import clases.Usuario;
import clases.ConversorMD5;
import java.sql.ResultSet;

public class GestorBDD {

    public static boolean comprobarUsuario(Usuario usuario){
        
        try {
            System.out.println("Comprobando usuario...");
            
            Conexion conexion = new Conexion();
            
            int contador = 0;            
            ResultSet resultado = conexion.seleccionar("*","predeterminado", "usuarios", "nombre = '" + usuario.getNombre() + "'");
                        
            while (resultado.next()){
                contador++;
            }
            resultado.close();
            
            if(contador == 0){
                return false;
            } else {
                return true;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        
        return false;
    }
    
    public static boolean insertarUsuario(Usuario usuario){
        
        try {
            System.out.println("Insertando usuario...");
            
            Conexion conexion = new Conexion();
            
            conexion.insertar("predeterminado", "usuarios", "email, nombre, contrasena", "'" + usuario.getEmail() + "','" + usuario.getNombre() + "','" + usuario.getContrasena() + "'");
            
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        
        return false;
    }
    
    public static Usuario recibirUsuario(String nombre){
        Usuario usuario = null;
        
        try {
            System.out.println("Recibiendo usuario...");
            
            Conexion conexion = new Conexion();
            
            int contador = 0;            
            ResultSet resultado = conexion.seleccionar("email, nombre, contrasena","predeterminado", "usuarios", "nombre = '" + nombre + "'");
                        
            if(resultado.next() == false){
                return usuario;
            }
            usuario = new Usuario(resultado.getString("email"),resultado.getString("nombre"),resultado.getString("contrasena"));
            resultado.close();
            
            return usuario;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        
        return usuario;
    }
    
    public static boolean validarUsuario(Usuario usuario){
        
        try {
            System.out.println("Logueando usuario...");
            
            Conexion conexion = new Conexion();
            
            int contador = 0;            
            ResultSet resultado = conexion.seleccionar("*","predeterminado", "usuarios", "nombre = '" + usuario.getNombre() + "' AND contrasena = '" + usuario.getContrasena() + "'");
                        
            while (resultado.next()){
                contador++;
            }
            resultado.close();
            
            if(contador == 0){
                return false;
            } else {
                return true;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        
        return false;
        
    }
    
    /*
    public static void main (String args[]){
        Usuario usuario = new Usuario("prueba@mail.com","prueba","pruebaprueba");
        //Usuario usuario = new Usuario("pantuquero@mail.com","pantuquero","pantuquero");
        GestorBDD gestor = new GestorBDD();
        
        //System.out.println(gestor.comprobarUsuarioBDD(usuario));
        
        gestor.insertarUsuario(usuario);
        
        //System.out.println(gestor.comprobarUsuarioBDD(usuario));
        
        //Usuario usuarioDos = gestor.recibirUsuario("María");
        
        //System.out.println(usuarioDos.getEmail());
        //System.out.println(usuarioDos.getNombre());
        //System.out.println(usuarioDos.getContrasena());
        
    }
    */
}