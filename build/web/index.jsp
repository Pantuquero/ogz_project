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
	<link rel="stylesheet" type="text/css" href="css/custom_1.css" />
	
        <script src="js/modernizr.custom.63321.js"></script>
        
        <%
            if(session.getAttribute("usuario") == null){
                response.sendRedirect("login.jsp");
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
                    <h2>Orgamingzation
                        <span>
                            <span>Group 1</span> <!--| <a href="index2.html">Demo 2</a>-->
                        </span>
                    </h2>
                    <h3 class="custom-month-year">
                        <span id="custom-month" class="custom-month"></span>
                        <span id="custom-year" class="custom-year"></span>
                        <nav>
                            <span id="custom-prev" class="custom-prev"></span>
                            <span id="custom-next" class="custom-next"></span>
                            <span id="custom-current" class="custom-current" title="Got to current date"></span>
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
    </body>
</html>
