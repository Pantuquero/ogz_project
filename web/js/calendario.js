$(function () {
    
    // Recojo el grupo actual seleccionado
    var selector = document.getElementById("select_grupos");
    
    if(selector.length > 0){
        var grupo_seleccionado = selector.options[selector.selectedIndex].value;
    }else {
        $('#cuadrito_eventos').hide();
    }
    
    //var codropsEvents = {};
    //codropsEvents['05-03-2017'] = '<span>Fullscreen Background Image Slideshow with CSS3</span>,<span>Fullscreen Background Image Slideshow with CSS3</span>';
    //codropsEvents['05-04-2017'] = '<span>Fullscreen Background Image Slideshow with CSS3</span>';
    
    //var grupo_seleccionado = getCookie();
    //var grupo_seleccionado = document.cookie;
    
    
    /*
    //var codropsEvents = {'05-03-2017' : '<span>Fullscreen Background Image Slideshow with CSS3</span>'};
    var codropsEvents = {};
    codropsEvents['05-03-2017'] = '<span>Fullscreen Background Image Slideshow with CSS3</span>';
    codropsEvents['05-04-2017'] = '<span>Fullscreen Background Image Slideshow with CSS3</span>';
    
    cal.setData(codropsEvents);
    
    */
    
    var cal = $('#calendar').calendario({onDayClick: function ($el, $contentEl, dateProperties) {
            /*for (var key in dateProperties) {
                console.log(key + ' = ' + dateProperties[ key ]);
            }*/
        },
        
        caldata: codropsEvents
    }),
    $month = $('#custom-month').html(cal.getMonthName()),
    $year = $('#custom-year').html(cal.getYear());
    
    //Botones parte superior derecha
    $('#custom-next').on('click', function () {
        cal.gotoNextMonth(updateMonthYear);
        anadirListenersEventos();
    });
    $('#custom-prev').on('click', function () {
        cal.gotoPreviousMonth(updateMonthYear);
        anadirListenersEventos();
    });
    $('#custom-current').on('click', function () {
        cal.gotoNow(updateMonthYear);
        anadirListenersEventos();
        //$.session.set("prueba","no yay");
    });

    anadirListenersEventos();

    // Muestra dialogo de confirmacion para unirte o abandonar el evento
    function anadirListenersEventos(){
        $('.evento').on('mousedown', function(event){
            var respuesta = false;
            var participantes = $(this).attr('participantes');
            var asistentes = participantes.split(",");
            var usuario = "@" + $("#etiqueta_usuario").attr("value").trim();
            var asiste = false;
            
            for(var i=0;i<asistentes.length;i++){
                
                if(asistentes[i] == usuario){
                    
                    asiste = true;
                    break;
                }else{
                    asiste = false;
                }
            }
            
            // Click izquierdo
            if(event.which == 1){
                
                // Compruebo si en los participantes está el usuario, si lo está no puede unirse de nuevo
                if(asiste){
                    
                    // Muestro la información del evento
                    alert($(this).attr('hora') + "\n" +
                          $(this).attr('juego') + "\n" +
                          $(this).attr('participantes'));
                    detenerListeners();
                    
                    return;
                }
                
                respuesta = confirm("JOIN this event?" + "\n\n" +
                                    $(this).attr('hora') + "\n" +
                                    $(this).attr('juego') + "\n" +
                                    $(this).attr('participantes'));
                
                if(respuesta){
                    var id_evento = $(this).attr("id");
                    var url = "index";
                    
                    var datos = {};
                    datos['unirse_evento'] = 'true';
                    datos['id_evento'] = id_evento;
                    
                    jQuery.post(url, datos, function(){
                        participantes += usuario + ",";
                        $(this).attr('participantes',participantes);
                        recargar();
                    });
                }
                
            // Click derecho
            }else if (event.which == 3){
                
                // Compruebo si en los participantes está el usuario, si no está no puede abandonar el grupo
                if(!asiste){
                    return;
                }
                
                respuesta = confirm("LEAVE this event?" + "\n\n" +
                                    $(this).attr('hora') + "\n" +
                                    $(this).attr('juego') + "\n" +
                                    $(this).attr('participantes'));
                            
                if(respuesta){
                    var id_evento = $(this).attr("id");
                    var url = "index";
                    
                    var datos= {};
                    datos['abandonar_evento'] = 'true';
                    datos['id_evento'] = id_evento;
                    
                    jQuery.post(url, datos, function(){
                        usuario += usuario + ",";
                        participantes.replace(usuario,'');
                        $(this).attr('participantes',participantes);
                        recargar();
                    });
                }
            }
        });
    }
    
    /**
     * Detiene el "Event bubbling" de los listeners
     * @returns
     */
    function detenerListeners() {
        if (!e) var e = window.event;
        e.cancelBubble = true;
        if (e.stopPropagation) e.stopPropagation();
    }
    
    function updateMonthYear() {
        $month.html(cal.getMonthName());
        $year.html(cal.getYear());
    }
   
    function getCookie(cname){
        var name = cname + "";
        var decodedCookie = decodeURIComponent(document.cookie);
        var ca = decodedCookie.split(';');
        
        for(var i = 0; i < ca.length; i++){
            var c = ca[i];
            
            while (c.charAt(0) == ' '){
                c = c.substring(1);
            }
            
            if(c.indexOf(name) == 0){
                return c.substring(name.length, c.length);
            }
        }
        
        return "";
    }
    
    function actualizarGalleta(){
        var selector = document.getElementById("select_grupos");

        if(selector.length > 0){
            var valor = selector.options[selector.selectedIndex].value;

            // También establezco la galleta para guardar el grupo actual
            document.cookie = "grupo_seleccionado=" + valor;
        }
    }
    
    function recargar() {

        actualizarGalleta();
        location.reload();
    }

    // you can also add more data later on. As an example:
    /*
     someElement.on( 'click', function() {
     cal.setData( {
     '03-01-2013' : '<a href="#">testing</a>',
     '03-10-2013' : '<a href="#">testing</a>',
     '03-12-2013' : '<a href="#">testing</a>'
     } );
     // goes to a specific month/year
     cal.goto( 3, 2013, updateMonthYear );
     } );
     */
});