package clases;

import clases.Conexion;
import clases.Usuario;
import clases.ConversorMD5;
import java.sql.ResultSet;


/**
 * This class is between Usuario and Conexion class, does all the
 * transformations to easily work with the object User into the DB.
 * @author Pantuquero
 */
public class GestorBDD {

    /**
     * Checks the username of the passed Usuario into the DB and returns a
     * boolean.
     * @param usuario
     * @return true -> Username exists in the DB
     */
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
    
    /**
     * Inserts the passed Usuario into de DB.
     * @param usuario
     */
    public static void insertarUsuario(Usuario usuario){
        
        try {
            System.out.println("Insertando usuario...");
            
            Conexion conexion = new Conexion();
            
            conexion.insertar("predeterminado", "usuarios", "email, nombre, contrasena", "'" + usuario.getEmail() + "','" + usuario.getNombre() + "','" + usuario.getContrasena() + "'");
            
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
    }
    
    /**
     * Returns a Usuario with the passed username.
     * @param nombre
     * @return Usuario
     */
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
    
    /**
     * Validates the passed Usuario against the DB.
     * @param usuario
     * @return boolean -> true if the user exists
     */
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