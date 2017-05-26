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
                
                return;
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
                    <div class="menu_discreto">
                        <label id="recargar">Reload</label> | <label id="salir">Logout</label>
                    </div>
                    <br>
                    <div id="div_entrada_datos">
                        <div class="cuadrito_entrada_datos">
                            <h3>Teams</h3>
                            <form id="formulario_grupos" action="index" method="post">
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
                                <input type="text" class="entrada_texto" name="entrada_texto">  <button class="boton" id="unirse_grupo" name="unirse_grupo" type="input" title="Join this team!">Join</button>
                                <button class="boton" id="crear_grupo" type="input" name="crear_grupo" title="New team">New</button>
                                <button class="boton" id="abandonar_grupo" type="input" name="abandonar_grupo" title="Leave current team">Leave</button>
                                <br>
                            </form>
                        </div>
                        <br>
                        <div class="cuadrito_entrada_datos">
                            <h3>New event</h3>
                            <form id="formulario_eventos" action="index" method="post">
                                <input type="date" id="fecha_evento" name="fecha_evento" class="date_style">
                                <br><br>
                                <select class="select-style hour" id="desde_hora" name="desde_hora">
                                    <option style="display: none;" selected>From</option>
                                    <option value="\"00:00\"">00:00</option>
                                    <option value="\"01:00\"">01:00</option>
                                    <option value="\"02:00\"">02:00</option>
                                    <option value="\"03:00\"">03:00</option>
                                    <option value="\"04:00\"">04:00</option>
                                    <option value="\"05:00\"">05:00</option>
                                    <option value="\"06:00\"">06:00</option>
                                    <option value="\"07:00\"">07:00</option>
                                    <option value="\"08:00\"">08:00</option>
                                    <option value="\"09:00\"">09:00</option>
                                    <option value="\"10:00\"">10:00</option>
                                    <option value="\"11:00\"">11:00</option>
                                    <option value="\"12:00\"">12:00</option>
                                    <option value="\"13:00\"">13:00</option>
                                    <option value="\"14:00\"">14:00</option>
                                    <option value="\"15:00\"">15:00</option>
                                    <option value="\"16:00\"">16:00</option>
                                    <option value="\"17:00\"">17:00</option>
                                    <option value="\"18:00\"">18:00</option>
                                    <option value="\"19:00\"">19:00</option>
                                    <option value="\"20:00\"">20:00</option>
                                    <option value="\"21:00\"">21:00</option>
                                    <option value="\"22:00\"">22:00</option>
                                    <option value="\"23:00\"">23:00</option>
                                </select>
                                <select class="select-style hour" id="hasta_hora" name="hasta_hora">
                                    <option style="display: none;" selected>To</option>
                                    <option value="\"00:00\"">00:00</option>
                                    <option value="\"01:00\"">01:00</option>
                                    <option value="\"02:00\"">02:00</option>
                                    <option value="\"03:00\"">03:00</option>
                                    <option value="\"04:00\"">04:00</option>
                                    <option value="\"05:00\"">05:00</option>
                                    <option value="\"06:00\"">06:00</option>
                                    <option value="\"07:00\"">07:00</option>
                                    <option value="\"08:00\"">08:00</option>
                                    <option value="\"09:00\"">09:00</option>
                                    <option value="\"10:00\"">10:00</option>
                                    <option value="\"11:00\"">11:00</option>
                                    <option value="\"12:00\"">12:00</option>
                                    <option value="\"13:00\"">13:00</option>
                                    <option value="\"14:00\"">14:00</option>
                                    <option value="\"15:00\"">15:00</option>
                                    <option value="\"16:00\"">16:00</option>
                                    <option value="\"17:00\"">17:00</option>
                                    <option value="\"18:00\"">18:00</option>
                                    <option value="\"19:00\"">19:00</option>
                                    <option value="\"20:00\"">20:00</option>
                                    <option value="\"21:00\"">21:00</option>
                                    <option value="\"22:00\"">22:00</option>
                                    <option value="\"23:00\"">23:00</option>
                                </select>
                                <br>
                                <input type="text" class="entrada_texto" id="juego" name="juego" list="juegos" placeholder="Choose your game!">
                                <datalist id="juegos" name="juegos">
                                    <%
                                        //Relleno la lista de grupos del usuario
                                        try {
                                            ArrayList<String> juegos =  new ArrayList<String>();

                                            HttpSession sesion = request.getSession();
                                            juegos = (ArrayList<String>) sesion.getAttribute("juegos");

                                            if(juegos.size() != 0){
                                                for(int i=0; i<juegos.size(); i++){
                                                    String nombre = juegos.get(i);

                                                    out.println("<option value=\"" + nombre + "\">");
                                                }
                                            }

                                        } catch(Exception e) {
                                            e.printStackTrace();
                                            System.err.println(e.getClass().getName()+": "+e.getMessage());
                                            System.exit(0);
                                        }
                                    %>
                                </datalist>
                                <button class="boton" id="crear_evento" type="input" name="crear_evento" title="New event">New</button>
                                <br>
                            </form>
                        </div>
                        <br>
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
