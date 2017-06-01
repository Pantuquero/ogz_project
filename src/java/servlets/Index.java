package servlets;

import clases.Evento;
import clases.GestorBDD;
import clases.Grupo;
import clases.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Pantuquero
 */
@WebServlet(name = "index", urlPatterns = {"/index"})
public class Index extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html;charset=UTF-8");
        System.out.println("Accedido a index.java, procesando peticion...");
        
        try {
            comprobarSesion(request, response);
            
            HttpSession sesion = (HttpSession) request.getSession();
            Usuario usuario = (Usuario) sesion.getAttribute("usuario");

            // Recojo los botones para saber cual se ha pulsado
            String unirse_grupo = request.getParameter("unirse_grupo");
            String crear_grupo = request.getParameter("crear_grupo");
            String abandonar_grupo = request.getParameter("abandonar_grupo");
            String crear_evento = request.getParameter("crear_evento");
            String unirse_evento = request.getParameter("unirse_evento");
            String abandonar_evento = request.getParameter("abandonar_evento");
            
            // SOLICITUD DE UNION A GRUPO
            if(unirse_grupo != null) {
                
                String cadena_id_grupo = request.getParameter("entrada_texto");
                int id_grupo = Integer.parseInt(cadena_id_grupo);
                
                usuario = unirAgrupo(id_grupo, usuario);
                
                sesion.setAttribute("usuario", usuario);
                
            // SOLICITUD DE GREACION DE GRUPO
            } else if(crear_grupo != null) {

                String nombre_grupo = request.getParameter("entrada_texto");
                crearGrupo(nombre_grupo, usuario);

                sesion.setAttribute("usuario", usuario);

            // SOLICITUD DE ABANDONO DE GRUPO
            } else if(abandonar_grupo != null) {
                
                if(request.getParameter("select_grupos") == null){
                    return;
                }  
                
                String cadena_id_grupo = request.getParameter("select_grupos");              
                int id_grupo = Integer.parseInt(cadena_id_grupo);
                
                usuario = abandonarGrupo(id_grupo, usuario);
                
                sesion.setAttribute("usuario", usuario);
                
            // SOLICITUD DE CREACION DE EVENTO
            } else if (crear_evento != null) {
                String grupo_seleccionado = request.getParameter("grupo_oculto");
                
                if(grupo_seleccionado != ""){
                    String fecha_evento = request.getParameter("fecha_evento");
                    String desde_hora = request.getParameter("desde_hora");
                    String hasta_hora = request.getParameter("hasta_hora");
                    String juego = request.getParameter("juego");
                    
                    int id_grupo = Integer.parseInt(grupo_seleccionado);
                    
                    ArrayList<String> asistentes = new ArrayList();
                    asistentes.add(usuario.getNombre());
                    
                    SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd kk:mm");
                    
                    System.out.println(fecha_evento + " " + desde_hora);
                    
                    Date fecha_desde = formato.parse(fecha_evento + " " + desde_hora);
                    Date fecha_hasta = formato.parse(fecha_evento + " " + hasta_hora);
                    Calendar fecha_inicio = Calendar.getInstance();
                    Calendar fecha_fin = Calendar.getInstance();
                    fecha_inicio.setTime(fecha_desde);
                    fecha_fin.setTime(fecha_hasta);

                    Evento evento_provisional = new Evento(-1, fecha_inicio, fecha_fin, juego, asistentes);

                    crearEvento(usuario, id_grupo, evento_provisional);
                }
                
            // SOLICITUD DE UNION A EVENTO
            } else if (unirse_evento != null){
                String cadena_id_evento = request.getParameter("id_evento");
                int id_evento = Integer.parseInt(cadena_id_evento);
                
                usuario = unirAevento(usuario, id_evento);
            
            // SOLICITUD DE ABANDONO DE EVENTO
            } else if (abandonar_evento != null){
                String cadena_id_evento = request.getParameter("id_evento");
                int id_evento = Integer.parseInt(cadena_id_evento);
                
                usuario = abandonarEvento(usuario, id_evento);
            }
            
            response.sendRedirect("index.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
    }
    
    private Usuario abandonarEvento(Usuario usuario, int id_evento){
        
        ArrayList<String> asistentes = new ArrayList<String>();
        Evento evento_provisional = new Evento(id_evento, Calendar.getInstance(), Calendar.getInstance(), "nogame", asistentes);
        
        GestorBDD.eliminarAsistente(usuario, evento_provisional);
        
        usuario.setGrupos(GestorBDD.recibirGruposUsuario(usuario));
        
        return usuario;
    }
    
    /**
     * 
     * @param usuario
     * @param id_evento
     * @return 
     */
    private Usuario unirAevento(Usuario usuario, int id_evento){
        
        ArrayList<String> asistentes = new ArrayList<String>();
        Evento evento_provisional = new Evento(id_evento, Calendar.getInstance(), Calendar.getInstance(), "nogame", asistentes);
        
        GestorBDD.insertarAsistente(usuario, evento_provisional);
        
        usuario.setGrupos(GestorBDD.recibirGruposUsuario(usuario));
        
        return usuario;
    }
    
    /**
     * 
     * @param usuario
     * @param id_grupo
     * @param evento_provisional
     * @return 
     */
    private Usuario crearEvento(Usuario usuario, int id_grupo, Evento evento_provisional){
        Evento evento = evento_provisional;
        
        evento = GestorBDD.insertarEvento(evento_provisional, id_grupo, usuario);
        
        //Recorro los grupos del usuario para insertar el evento donde toque
        for(int i=0; i <= usuario.getGrupos().size(); i++){
            if(usuario.getGrupos().get(i).getIdentificador() == id_grupo){
                usuario.getGrupos().get(i).asignarEvento(evento);
                break;
            }
        }
        
        return usuario;
    }
    
    /**
     * 
     * @param id_grupo
     * @param usuario
     * @return 
     */
    private Usuario unirAgrupo( int id_grupo, Usuario usuario){
        System.out.println("Uniendo usuario a grupo...");
        
        ArrayList<Evento> eventos = new ArrayList<Evento>();
        Grupo grupo_provisional = new Grupo(id_grupo, "noname", eventos);
        
        // Inserto la relación
        GestorBDD.asignarGrupoAusuario(grupo_provisional, usuario);
        
        //Recargo los grupos del usuario en el usuario
        ArrayList<Grupo> grupos = GestorBDD.recibirGruposUsuario(usuario);
        usuario.setGrupos(grupos);
        
        return usuario;
    }
    
    /**
     * 
     * @param nombre_grupo
     * @param usuario
     * @return 
     */
    private Usuario crearGrupo(String nombre_grupo, Usuario usuario){
        System.out.println("Creando grupo...");
        
        //Inserto el grupo en la BDD
        Grupo grupo = GestorBDD.insertarGrupo(nombre_grupo);
        
        //Inserto la relación
        GestorBDD.asignarGrupoAusuario(grupo, usuario);
        
        //Asigno el grupo al usuario
        usuario.asignarGrupo(grupo);
        
        return usuario;
    }
    
    /**
     * 
     * @param id_grupo
     * @param usuario
     * @return 
     */
    private Usuario abandonarGrupo(int id_grupo, Usuario usuario){
        System.out.println("Abandonando grupo...");
        
        Grupo grupo = null;
        
        // Recorro los grupos del usuario para coger su grupo
        for(int i=0; i<=usuario.getGrupos().size(); i++){
            if(usuario.getGrupos().get(i).getIdentificador() == id_grupo){
                grupo = usuario.getGrupos().get(i);
                break;
            }
        }
        
        if(grupo != null){
            GestorBDD.desasignarGrupoAusuario(grupo, usuario);
            
            usuario.desasignarGrupo(grupo);
        }
        
        return usuario;
    }
    
    /**
     * To validate the session
     * @param request
     * @param response 
     */
    protected void comprobarSesion(HttpServletRequest request, HttpServletResponse response){
        
        HttpSession sesion = (HttpSession) request.getSession();
        Usuario usuario =  (Usuario) sesion.getAttribute("usuario");
        
        try {
            
            if(usuario == null) {
                
                request.setAttribute("mensaje", "Your session has expired.");
                request.setAttribute("tipo_mensaje","false");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
    }
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
