package clases;

import clases.Conexion;
import clases.Usuario;
import clases.ConversorMD5;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;


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
            String columnas = "*";
            String tablas = "predeterminado.usuarios";
            String condiciones = "nombre = '" + usuario.getNombre() + "'";
            ResultSet resultado = conexion.seleccionar(columnas, tablas, condiciones);
                        
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
            
            String esquema = "predeterminado";
            String tabla = "usuarios";
            String columnas = "email, nombre, contrasena";
            String valores = "'" + usuario.getEmail() + "','" + usuario.getNombre() + "','" + usuario.getContrasena() + "'";
            conexion.insertar(esquema, tabla, columnas, valores);
            
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
            int identificador = -1;
            String email = null;
            String contrasena = null;
            ArrayList<Grupo> grupos = new ArrayList();
            ResultSet resultado = null;
            
            String columnas = "id, email, nombre, contrasena";
            String tablas = "predeterminado.usuarios";
            String condiciones = "nombre = '" + nombre + "'";
            resultado = conexion.seleccionar(columnas, tablas, condiciones);            
            resultado.next();
            
            identificador = resultado.getInt("id");
            email = resultado.getString("email");
            nombre = resultado.getString("nombre");
            contrasena = resultado.getString("contrasena");
            resultado.close();
            
            Usuario usuario_provisional = new Usuario(identificador, email, nombre, contrasena, grupos);
            grupos = recibirGruposUsuario(usuario_provisional);
            
            usuario = new Usuario(identificador, email, nombre, contrasena, grupos);
            
            /*
            usuario = new Usuario(resultado.getString("email"),resultado.getString("nombre"),resultado.getString("contrasena"));
            resultado.close();
            */
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        
        return usuario;
    }
    
    public static ArrayList<Grupo> recibirGruposUsuario(Usuario usuario){
        Conexion conexion = new Conexion();
        ArrayList<Grupo> grupos = new ArrayList<Grupo>();
        
        try {
            System.out.println("Recibiendo grupos del usuario...");
            
            String columnas = "grupos.id, grupos.nombre";
            String tablas = "predeterminado.grupos INNER JOIN predeterminado.grupo_usuario ON (grupos.id = grupo_usuario.id_grupo)";
            String condiciones = "grupo_usuario.id_usuario = " + usuario.getIdentificador();
            ResultSet resultado = conexion.seleccionar(columnas, tablas, condiciones);
            
            while (resultado.next()){
                
                int identificador = resultado.getInt("id");
                String nombre = resultado.getString("nombre");
                
                ArrayList<Evento> eventos = new ArrayList<Evento>();
                Grupo grupo_provisional = new Grupo(identificador, nombre, eventos);
                eventos = recibirEventosGrupo(grupo_provisional);
                
                Grupo grupo = new Grupo(identificador, nombre, eventos);
                
                grupos.add(grupo);
            }
            
            resultado.close();
            
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        
        return grupos;
    }
    
    public static ArrayList<Evento> recibirEventosGrupo(Grupo grupo){
        Conexion conexion = new Conexion();
        ArrayList<Evento> eventos = new ArrayList<Evento>();
        
        try {
            System.out.println("Recibiendo eventos del grupo...");
            
            String columnas = "id, fecha_inicio, fecha_fin, (SELECT nombre FROM predeterminado.juegos WHERE juegos.id = id_juego) AS juego";
            String tablas = "predeterminado.eventos";
            String condiciones = "id_grupo = " + grupo.getIdentificador();
            ResultSet resultado = conexion.seleccionar(columnas, tablas, condiciones);
            
            while(resultado.next()){
                
                int identificador = resultado.getInt("id");
                Calendar fecha_inicio = Calendar.getInstance();
                    fecha_inicio.setTime(resultado.getDate("fecha_inicio"));
                Calendar fecha_fin = Calendar.getInstance();
                    fecha_fin.setTime(resultado.getDate("fecha_fin"));
                String juego = resultado.getString("juego");
                
                ArrayList<String> asistentes = new ArrayList<String>();
                Evento evento_provisional = new Evento(identificador, fecha_inicio, fecha_fin, juego, asistentes);
                asistentes = recibirAsistentesEvento(evento_provisional);
                
                Evento evento = new Evento(identificador, fecha_inicio, fecha_fin, juego, asistentes);
                
                eventos.add(evento);
            }
            resultado.close();
            
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        
        return eventos;
    }
    
    public static ArrayList<String> recibirAsistentesEvento(Evento evento){
        Conexion conexion = new Conexion();
        ArrayList<String> asistentes = new ArrayList<String>();
        
        try{
            System.out.println("Recibiendo asistentes de evento...");
            
            String columnas = "usuarios.nombre";
            String tabla = "predeterminado.usuarios INNER JOIN predeterminado.evento_usuario ON (usuarios.id = evento_usuario.id_usuario)";
            String condiciones = "evento_usuario.id_evento = " + evento.getIdentificador();
            ResultSet resultado = conexion.seleccionar(columnas, tabla, condiciones);
            
            while(resultado.next()){
                asistentes.add(resultado.getString("nombre"));
            }
            
            resultado.close();
            
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        
        return asistentes;
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
            
            String columnas = "*";
            String tablas = "predeterminado.usuarios";
            String condiciones = "nombre = '" + usuario.getNombre() + "' AND contrasena = '" + usuario.getContrasena() + "'";
            ResultSet resultado = conexion.seleccionar(columnas, tablas, condiciones);
                        
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
     * Inserts a group with the passed name and retrieves a group with it's ID.
     * @param grupo
     * @return 
     */
    public static Grupo insertarGrupo(String nombre){
        Conexion conexion = new Conexion();
        Grupo grupo = null;
        
        try {
            System.out.println("Creando grupo...");
            
            //Creo el grupo
            String esquema = "predeterminado";
            String tabla = "grupos";
            String columnas = "nombre";
            String valores = "'" + nombre + "'";
            int id = conexion.insertar(esquema, tabla, columnas, valores);
            
            ArrayList<Evento> eventos = new ArrayList<Evento>();
            grupo = new Grupo(id, nombre, eventos);
            
        }catch(Exception e){
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        
        return grupo;
    }
    
    public static boolean asignarGrupoAusuario(Grupo grupo, Usuario usuario){
        Conexion conexion = new Conexion();
        
        try {
            System.out.println("Asignando grupo " + grupo.getNombre() + " a usuario " + usuario.getNombre());
            
            String esquema = "predeterminado";
            String tabla = "grupo_usuario";
            String columnas = "id_grupo, id_usuario";
            String valores = "'" + grupo.getIdentificador() + "','" + usuario.getIdentificador() + "'";
            conexion.insertar(esquema, tabla, columnas, valores);
            
            return true;
            
        }catch(Exception e){
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