<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login - OGZ</title>
  
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/meyer-reset/2.0/reset.min.css">
    <link rel='stylesheet prefetch' href='http://fonts.googleapis.com/css?family=Roboto:400,100,300,500,700,900|RobotoDraft:400,100,300,500,700,900'>
    <link rel='stylesheet prefetch' href='http://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css'>
    <link rel="stylesheet" href="css/login.min.css">
    <link rel="stylesheet" href="css/login.css">
    
    <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
    <script src="js/jquery.validate.js"></script>
    
    <%
        //Si existe una sesión cuando accedes al login la elimino
        HttpSession sesion = request.getSession();
        if(sesion != null){
            sesion.invalidate();
            //sesion.setAttribute("usuario", "-1");
        }
    %>
</head>

<body>
  
    <div class="pen-title">
        <h1>Orgamingzation</h1>
    </div>

    <!-- Form Module-->
    <div class="module form-module">
        <div class="toggle">
            <i class="fa fa-times fa-pencil"></i>
            <!--<div class="tooltip">Click Me</div>-->
        </div>
        <div class="form">
            <h2>Login to OGZ</h2>
            <form id="formulario_login" action="login" method="post">
                <input type="text" name="nombreusu" placeholder="Username"/>
                <input type="password" name="contrasena" placeholder="Password"/>
                <button id="boton_login">Login</button>
            </form>
        </div>
        <div class="form">
            <h2>Register</h2>
            <form id="formulario_registro" action="Register" method="post">
                <input type="email" name="correoreg" placeholder="Email Address"/>
                <input type="text" name="nombreusureg" placeholder="Username"/>
                <input id="contrasenareg" type="password" name="contrasenareg" placeholder="Password"/>
                <input type="password" name="contrasenaverreg" placeholder="Retype password"/>
                <button id="boton_registro">Register</button>
            </form>
        </div>
        <!--
        <div class="cta">
            <a href="#">Forgot your password?</a>
        </div>
        -->
        <div 
            <%
                // Aquí pinto los mensajes devueltos desde los servlets
                String mensaje = "";
                
                if(request.getAttribute("mensaje") == null){ // Si no hay mensaje no pinto nada
                    
                    out.print("hidden=\"true\"");
                    
                }else { // Si hay mensaje lo pinto y pongo un color u otro dependiendo del tipo de mensaje
                    mensaje = request.getAttribute("mensaje").toString();
                    
                    if(Boolean.parseBoolean(request.getAttribute("tipo_mensaje").toString())){
                        
                        out.print("class=\"cta mensaje malo\"");
                        
                    } else {
                        
                        out.print("class=\"cta mensaje bueno\"");
                    }
                };
            %>
        >
            <div><% out.print(mensaje); %></div>
        </div>
    </div>
    
    <script src="js/style.js"></script>
    <script src="js/login.js"></script>
</body>
</html>
