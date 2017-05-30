<%@page import="java.util.Calendar"%>
<%@page import="clases.Evento"%>
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
                                <select class="select-style" name="select_grupos" id="select_grupos">
                                    <%
                                        HttpSession sesion = request.getSession();
                                        ArrayList<Grupo> grupos =  new ArrayList<Grupo>();
                                        ArrayList<Evento> eventos = new ArrayList<Evento>();
                                        int id_grupo_inicial = -1;

                                        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
                                        grupos = usuario.getGrupos();
                                        
                                        //Relleno la lista de grupos del usuario
                                        try {    
                                            
                                            //Recojo la galleta para establecer el grupo seleccionado
                                            Cookie[] galletas = request.getCookies();
                                            if(galletas != null){
                                                
                                                for(Cookie galleta : galletas){
                                                    
                                                    if(galleta.getName().equals("grupo_seleccionado")){
                                                        
                                                        id_grupo_inicial = Integer.parseInt(galleta.getValue());
                                                    }
                                                }
                                            }

                                            if(usuario.getGrupos().size() != 0){
                                                // Para la primera ejecución inicializo la id del grupo seleccionado en el grupo 0
                                                if(id_grupo_inicial == -1){
                                                    id_grupo_inicial = grupos.get(0).getIdentificador();
                                                }
                                                
                                                for(int i=0; i<grupos.size(); i++){
                                                    
                                                    int codigo = grupos.get(i).getIdentificador();
                                                    String nombre = grupos.get(i).getNombre();
                                                    String codigo_formateado = String.format("%04d", codigo);
                                                    
                                                    if(codigo != id_grupo_inicial){
                                                        
                                                        out.println("<option class=\"\" value=\"" + codigo_formateado + "\">(#" + codigo_formateado + ") - " + nombre + "</option>");
                                                        
                                                    }else {
                                                        
                                                        out.println("<option class=\"\" value=\"" + codigo_formateado + "\" selected>(#" + codigo_formateado + ") - " + nombre + "</option>");
                                                        
                                                        //Recojo el array de eventos para cargar los eventos más abajo
                                                        for(Evento evento : grupos.get(i).getEventos()){
                                                            eventos.add(evento);
                                                        }
                                                    }
                                                }
                                                
                                                //id_grupo_inicial = usuario.getGrupos().get(0).getIdentificador();
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
                                    <option value="00:00">00:00</option>
                                    <option value="01:00">01:00</option>
                                    <option value="02:00">02:00</option>
                                    <option value="03:00">03:00</option>
                                    <option value="04:00">04:00</option>
                                    <option value="05:00">05:00</option>
                                    <option value="06:00">06:00</option>
                                    <option value="07:00">07:00</option>
                                    <option value="08:00">08:00</option>
                                    <option value="09:00">09:00</option>
                                    <option value="10:00">10:00</option>
                                    <option value="11:00">11:00</option>
                                    <option value="12:00">12:00</option>
                                    <option value="13:00">13:00</option>
                                    <option value="14:00">14:00</option>
                                    <option value="15:00">15:00</option>
                                    <option value="16:00">16:00</option>
                                    <option value="17:00">17:00</option>
                                    <option value="18:00">18:00</option>
                                    <option value="19:00">19:00</option>
                                    <option value="20:00">20:00</option>
                                    <option value="21:00">21:00</option>
                                    <option value="22:00">22:00</option>
                                    <option value="23:00">23:00</option>
                                </select>
                                <select class="select-style hour" id="hasta_hora" name="hasta_hora">
                                    <option style="display: none;" selected>To</option>
                                    <option value="00:00">00:00</option>
                                    <option value="01:00">01:00</option>
                                    <option value="02:00">02:00</option>
                                    <option value="03:00">03:00</option>
                                    <option value="04:00">04:00</option>
                                    <option value="05:00">05:00</option>
                                    <option value="06:00">06:00</option>
                                    <option value="07:00">07:00</option>
                                    <option value="08:00">08:00</option>
                                    <option value="09:00">09:00</option>
                                    <option value="10:00">10:00</option>
                                    <option value="11:00">11:00</option>
                                    <option value="12:00">12:00</option>
                                    <option value="13:00">13:00</option>
                                    <option value="14:00">14:00</option>
                                    <option value="15:00">15:00</option>
                                    <option value="16:00">16:00</option>
                                    <option value="17:00">17:00</option>
                                    <option value="18:00">18:00</option>
                                    <option value="19:00">19:00</option>
                                    <option value="20:00">20:00</option>
                                    <option value="21:00">21:00</option>
                                    <option value="22:00">22:00</option>
                                    <option value="23:00">23:00</option>
                                </select>
                                <br>
                                <input type="text" class="entrada_texto" id="juego" name="juego" list="juegos" placeholder="Choose your game!">
                                <datalist id="juegos" name="juegos">
                                    <%
                                        //Relleno la lista de juegos
                                        try {
                                            ArrayList<String> juegos =  new ArrayList<String>();

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
                                    <input id="grupo_oculto" name="grupo_oculto" type="hidden" value="">
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
        <script type="text/javascript">
            var codropsEvents = {};
            
            <%
                if(eventos.size() != 0){
                    // En el primer evento la fecha siempre será igual
                    String fecha_anterior = formatearFechaEvento(eventos.get(0).getFechaInicio());
                    Boolean primer_evento_en_fecha = true;
                    
                    //Primera fecha
                    out.print("codropsEvents['" + fecha_anterior + "'] = '");
                    
                    for(Evento evento : eventos){
                        String fecha_evento = formatearFechaEvento(evento.getFechaInicio());
                        String cadena_evento = "<span class=\"evento\" hora=\"" + formatearHoraEvento(evento.getFechaInicio()) + " - " + formatearHoraEvento(evento.getFechaFin()) + "\" juego=\"" + evento.getJuego() + "\" participantes=\"" + formatearAsistentes(evento.getAsistentes()) + "\">" +
                                                "<b><u>" + formatearHoraEvento(evento.getFechaInicio()) + " - " + formatearHoraEvento(evento.getFechaFin()) + "</b></u><br>" +
                                                "<b><i>" + evento.getJuego() + "</b></i><br>" +
                                                formatearAsistentes(evento.getAsistentes()) + "</span>";
                        
                        // Si el evento es el mismo día debo añadirlo en la misma fecha
                        if(fecha_evento.equals(fecha_anterior)){
                            
                            // Si es el primer evento de esta misma fecha no deberá llevar coma
                            if(primer_evento_en_fecha){
                                
                                out.print(cadena_evento);
                                primer_evento_en_fecha = false;
                                
                            }else{
                                
                                out.print("," + cadena_evento);
                            }
                        // En el momento en el que la fecha cambia hay que crear un nuevo evento
                        } else {
                            // Comienzo cerrando el evento anterior, con el println además salto de línea
                            out.println(";");
                            
                            // Restablezco la fecha inicial y el primer evento en fecha
                            fecha_anterior = formatearFechaEvento(evento.getFechaInicio());
                            primer_evento_en_fecha = true;
                            
                            // Pinto el evento entero y lo dejo abierto
                            out.print("codropsEvents['" + fecha_evento + "'] = '" + cadena_evento);
                        }
                    }
                    
                    // Cierro el último evento
                    out.println("';");
                }
                
                //out.println("codropsEvents['05-04-2017'] = '<span>Fullscreen Background Image Slideshow with CSS3</span>'");
            %>
            //codropsEvents['05-04-2017'] = '<span>Fullscreen Background Image Slideshow with CSS3</span>';
            
        </script>
	<script type="text/javascript" src="js/jquery.calendario.js"></script>
	<script type="text/javascript" src="js/calendario.js"></script>
        <script src="js/jquery.validate.js"></script>
        <script type="text/javascript" src="js/index.js"></script>
        
        <!-- Script  -->
        <script type="text/javascript">
            
            <% // Pinto los eventos del grupo, la función cal.setData no admite parámetros correctamente
                /*
                if(grupos.size() != 0){

                    Grupo grupo = null;

                    // Inicio funcion
                    out.println("cal.setData( {");

                    // Introduccion de eventos
                    for(int i=0;i<grupos.size();i++){

                        if(grupos.get(i).getIdentificador() == id_grupo_inicial){
                        System.out.println(grupos.get(i).getIdentificador() + " - " + id_grupo_inicial);    
                            if(grupos.get(i).getEventos().size() != 0){

                                for(Evento evento : grupos.get(i).getEventos()){

                                    //out.println("'05-01-2017' : '<span>Fullscreen Background Image Slideshow with CSS3</span>'");
                                    out.println("'" + formatearFechaEvento(evento.getFechaInicio()) + "' : " +
                                                "'<span class=\"evento\" hora=\"" + formatearHoraEvento(evento.getFechaInicio()) + " - " + formatearHoraEvento(evento.getFechaFin()) + "\" juego=\"" + evento.getJuego() + "\" participantes=\"" + formatearAsistentes(evento.getAsistentes()) + "\">" +
                                                "<b><u>" + formatearHoraEvento(evento.getFechaInicio()) + " - " + formatearHoraEvento(evento.getFechaFin()) + "</b></u><br>" +
                                                "<b><i>" + evento.getJuego() + "</b></i><br>" +
                                                formatearAsistentes(evento.getAsistentes()) + "</span>'");
                                }
                                break;
                            }
                        }
                    }

                    // Fin funcion
                    out.println("} );");
                }*/
            %>
            <%!
                private String formatearFechaEvento(Calendar fecha){
                    String cadena_fecha = "";

                    cadena_fecha = String.format("%02d", fecha.get(Calendar.MONTH)+1) + "-" + String.format("%02d", fecha.get(Calendar.DAY_OF_MONTH)) + "-" + fecha.get(Calendar.YEAR);

                    return cadena_fecha;
                }

                private String formatearHoraEvento(Calendar fecha){
                    String cadena_hora = "";

                    cadena_hora = String.format("%02d", fecha.get(Calendar.HOUR)) + ":" + String.format("%02d", fecha.get(Calendar.MINUTE));

                    return cadena_hora;
                }

                private String formatearAsistentes(ArrayList<String> asistentes){
                    String cadena_asistentes = "";

                    for(int i=0;i<asistentes.size();i++){

                        if(i == asistentes.size()){

                            cadena_asistentes += "@" + asistentes.get(i);

                        }else{

                            cadena_asistentes += "@" + asistentes.get(i) + ",";
                        }
                    }

                    return cadena_asistentes;
                }
            %>
        </script>
    </body>
</html>
