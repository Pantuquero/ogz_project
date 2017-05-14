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


/**
 * This servlet is called by the login.jsp, inserts a new user into the DB
 * when requests a registration.
 * @author Pantuquero
 */
@WebServlet(name = "Register", urlPatterns = {"/Register"})
public class Registro extends HttpServlet {

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
        String email = request.getParameter("correoreg");
        String nombre = request.getParameter("nombreusureg");
        String contrasena = request.getParameter("contrasenareg");
        Usuario usuario_provisional = new Usuario(email, nombre, ConversorMD5.convertirMD5(contrasena));
        
        try {
            
            if(GestorBDD.validarUsuario(usuario_provisional)){
                request.setAttribute("mensaje", "This user already exists!");
                request.setAttribute("tipo_mensaje","false");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
            
            System.out.println("Registrando usuario...");
            
            GestorBDD.insertarUsuario(usuario_provisional);
            request.setAttribute("mensaje", "Registration successful!");
            request.setAttribute("tipo_mensaje","true");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            
        } catch(Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
