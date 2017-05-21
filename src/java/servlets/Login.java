package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import clases.Conexion;
import clases.ConversorMD5;
import clases.Usuario;
import clases.GestorBDD;
import javax.servlet.http.HttpSession;


/**
 * This servlet is called by the login.jsp, checks user information with the DB
 * when users try to login.
 * @author Pantuquero
 */
@WebServlet(name = "login", urlPatterns = {"/login"})
public class Login extends HttpServlet {

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
        
        Conexion conexion = new Conexion();
        String nombre = request.getParameter("nombreusu");
        String contrasena = request.getParameter("contrasena");
        Usuario usuario_provisional = new Usuario(-1, "nomail", nombre, ConversorMD5.convertirMD5(contrasena), null);
        
        try {
            boolean validacion_usuario = GestorBDD.validarUsuario(usuario_provisional);

            if(validacion_usuario){
                
                // Creo la sesión y le paso el usuario
                HttpSession sesion = request.getSession(true);
                sesion.setMaxInactiveInterval(600); //10 min de sesion
                Usuario usuario = GestorBDD.recibirUsuario(usuario_provisional.getNombre());
                sesion.setAttribute("usuario", usuario);
                
                // Redirijo al main
                response.sendRedirect("index.jsp");
                //response.sendRedirect("/orgamingzation/Index");
                //request.getRequestDispatcher("/Index.java").forward(request, response);
            } else {
                // Usuario fallido, devuelvo al login con un mensaje de error
                request.setAttribute("mensaje", "Incorrect username/password.");
                request.setAttribute("tipo_mensaje","false");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch(Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        
        /*
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet login</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet login at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
        */
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
    /*
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
    */

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
