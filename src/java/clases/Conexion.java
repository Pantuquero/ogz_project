package clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conexion {
    
    private String driver;
    private String conector;
    private String host;
    private Integer puerto;
    private String basedatos;
    private String url;
    
    private String usuario;
    private String contrasena;
    
    private Connection conexion;
    
    public Conexion() {
        this.driver = "org.postgresql.Driver";
        this.conector = "jdbc:postgresql";
        this.host = "localhost";
        this.puerto = 5432;
        this.basedatos = "ogzdb";
        this.url = conector + "://" + host + ":" + puerto + "/" + basedatos;

        this.usuario = "readinsert";
        this.contrasena = "fujireins17";
    }
    
    public void conectar() {
      
        try {
            System.out.println("Conectando con la BDD...");
            
            Class.forName(driver);
            this.conexion = DriverManager.getConnection(url, usuario, contrasena);
        
        } catch(Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        } finally {
            System.out.println("Conexion establecida con la BDD");
        }
   }
    
    public void desconectar() {
        
        try {
            System.out.println("Desconectando de la BDD...");
            
            this.conexion.close();
            
        } catch(Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        } finally {
            System.out.println("Desconectado de la BDD");
        }
    }
    
    public void insertar(String esquema, String tabla, String columnas, String valores){
        
        conectar();
        
        try {
            System.out.println("Preparando insercion...");
            
            Statement sentencia = this.conexion.createStatement();
            String consulta = "INSERT INTO " + esquema + "." + tabla + " (" + columnas + ") "
                            + "VALUES (" + valores + ");";
            
            System.out.println("Insertando con consulta: <<" + consulta + ">>");
            
            sentencia.executeUpdate(consulta);
            sentencia.close();
            //this.conexion.commit();
            
        } catch(Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        } finally {
            System.out.println("Insercion completada");
        }
        
        desconectar();
    }
    
    public ResultSet seleccionar(String columnas, String esquema, String tabla, String condiciones){
        
        conectar();
        ResultSet resultado = null;
        
        try {
            System.out.println("Preparando seleccion...");
            
            if(condiciones == "*"){
                condiciones = " WHERE 1 = 1";
            } else {
                condiciones = " WHERE " + condiciones;
            }
            
            Statement sentencia = this.conexion.createStatement();
            String consulta = "SELECT " + columnas + " FROM " + esquema + "." + tabla + condiciones + ";";
            
            System.out.println("Seleccionando con consulta: <<" + consulta + ">>");
            
            resultado = sentencia.executeQuery(consulta);
            
            //resultado.close();
            //sentencia.close();
            
        } catch(Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }finally {
            System.out.println("Seleccion completada, devolviendo datos...");
        }
        
        desconectar();
        return resultado;
    }
    
    
    /*
    public static void main (String[] args){
        Conexion conexion = new Conexion();
        
        //conexion.insertar("usuarios","predeterminado","email,nombre,contrasena","'prueba2@mail.com','prueba2','prueba'");

        try {
            ResultSet resultado = conexion.seleccionar("*","predeterminado", "usuarios", "*");
            
            while (resultado.next()){
                System.out.println(resultado.getString("nombre"));
            }
            resultado.close();
            
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
    }
    */
}
