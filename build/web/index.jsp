<%@page import="java.util.ArrayList"%>
<%@page import="clases.Grupo"%>
<%@page import="clases.Usuario"%>

<!DOCTYPE html>
<!--[if IE 9]><html class="no-js ie9"><![endif]-->
<!--[if gt IE 9]><!--><html class="no-js"><!--<![endif]-->
    <head>
        <meta charset="UTF-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
	<meta name="viewport" content="width=device-width, initial-scale=1.0"> 
	<title>Index - OGZ</title>

	<!--<link rel="shortcut icon" href="../favicon.ico"> -->    
        
	<link rel="stylesheet" type="text/css" href="css/calendar.css" />
	<link rel="stylesheet" type="text/css" href="css/custom_calendar.css" />
        <link rel="stylesheet" type="text/css" href="css/index.css" />
	
        <script src="js/modernizr.custom.63321.js"></script>
        
        <%
            // Control de la sesión activa
            if(session.getAttribute("usuario") == null){
                //response.sendRedirect("login.jsp");
                request.setAttribute("mensaje", "Your session has expired");
                request.setAttribute("tipo_mensaje","false");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        %>
    </head>
    <body>
	<div class="container">	
            
            <!-- Codrops top bar
            <div class="codrops-top clearfix">
                <a href="http://tympanus.net/Development/Stapel/"><strong>&laquo; Previous Demo: </strong>Adaptive Thumbnail Pile Effect</a>
                <span class="right">
                    <a href="http://tympanus.net/codrops/?p=12416"><strong>Back to the Codrops Article</strong></a>
                </span>
            </div>
            <!--/ Codrops top bar -->
            
            <div class="custom-calendar-wrap custom-calendar-full">
                <div class="custom-header clearfix">
                    <h2>Orgamingzation</h2>
                    <br>
                    <div id="selector">
                        <h3>Teams</h3>
                        <form id="formulario_grupos" action="index" method="post">
                            <br>
                            <select class="select-style" name="select_grupos">
                                <%
                                    //Relleno la lista de grupos del usuario
                                    try {
                                        ArrayList<Grupo> grupos =  new ArrayList<Grupo>();
                                        
                                        HttpSession sesion = request.getSession();
                                        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
                                        grupos = usuario.getGrupos();
                                        
                                        if(usuario.getGrupos().size() != 0){
                                            for(int i=0; i<grupos.size(); i++){
                                                int codigo = grupos.get(i).getIdentificador();
                                                String nombre = grupos.get(i).getNombre();
                                                String codigo_formateado = String.format("%04d", codigo);

                                                out.println("<option value=\"" + codigo_formateado + "\">(#" + codigo_formateado + ") - " + nombre + "</option>");
                                            }
                                        }
                                        
                                    } catch(Exception e) {
                                        e.printStackTrace();
                                        System.err.println(e.getClass().getName()+": "+e.getMessage());
                                        System.exit(0);
                                    }
                                %>
                            </select>
                            <br>
                            <input type="text" class="entrada_texto" name="entrada_texto">  <button class="boton" id="unirse_grupo" type="input" title="Join this team!">Join</button>
                            <button class="boton" id="crear_grupo" type="input" name="crear_grupo" title="New team">New</button>
                            <button class="boton" id="abandonar_grupo" type="input" name="abandonar_grupo" title="Leave current team">Leave</button>
                            <br>
                        </form>
                    </div>
                    <h3 class="custom-month-year">
                        <span id="custom-month" class="custom-month"></span>
                        <span id="custom-year" class="custom-year"></span>
                        <nav>
                            <span id="custom-prev" class="custom-prev"></span>
                            <span id="custom-next" class="custom-next"></span>
                            <span id="custom-current" class="custom-current" title="Go to current date"></span>
                        </nav>
                    </h3>
                </div>
                <div id="calendar" class="fc-calendar-container"></div>
            </div>
	</div><!-- /container -->
    
        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
	<script type="text/javascript" src="js/jquery.calendario.js"></script>
	<script type="text/javascript" src="js/data.js"></script>
	<script type="text/javascript" src="js/calendario.js"></script>
        <script src="js/jquery.validate.js"></script>
        <script type="text/javascript" src="js/index.js"></script>
    </body>
</html>
