package servlets;

import clases.Evento;
import clases.GestorBDD;
import clases.Grupo;
import clases.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
        
        try {
            comprobarSesion(request, response);
            
            HttpSession sesion = (HttpSession) request.getSession();
            Usuario usuario = (Usuario) sesion.getAttribute("usuario");

            // Recojo los botones para saber cual se ha pulsado
            String unirseGrupo = request.getParameter("unirse_grupo");
            String crearGrupo = request.getParameter("crear_grupo");
            String abandonarGrupo = request.getParameter("abandonar_grupo");        
            System.out.println(unirseGrupo);
            // SOLICITUD DE UNION A GRUPO
            if(unirseGrupo != null) {
                
                String cadena_id_grupo = request.getParameter("entrada_texto");
                int id_grupo = Integer.parseInt(cadena_id_grupo);
                
                usuario = unirAgrupo(id_grupo, usuario);
                
                sesion.setAttribute("usuario", usuario);
                
            // SOLICITUD DE GREACION DE GRUPO
            } else if(crearGrupo != null) {

                String nombre_grupo = request.getParameter("entrada_texto");
                crearGrupo(nombre_grupo, usuario);

                sesion.setAttribute("usuario", usuario);

            // SOLICITUD DE ABANDONO DE GRUPO
            } else if(abandonarGrupo != null) {
                
                if(request.getParameter("select_grupos") == null){
                    return;
                }  
                
                String cadena_id_grupo = request.getParameter("select_grupos");              
                int id_grupo = Integer.parseInt(cadena_id_grupo);
                
                usuario = abandonarGrupo(id_grupo, usuario);
                
                sesion.setAttribute("usuario", usuario);
                
            }
            
            response.sendRedirect("index.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
    }
    
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
