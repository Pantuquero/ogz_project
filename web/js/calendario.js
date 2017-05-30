$(function () {
    
    // Recojo el grupo actual seleccionado
    var selector = document.getElementById("select_grupos");
    var grupo_seleccionado = selector.options[selector.selectedIndex].value;
    
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
            for (var key in dateProperties) {
                console.log(key + ' = ' + dateProperties[ key ]);
            }
            //alert(dateProperties["day"]);
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

    // Muestra un alert con la info del evento seleccionado
    function anadirListenersEventos(){
        $('.evento').on('click', function() {
            //alert($(this).text());
            alert($(this).attr('hora') + "\n" +
                  $(this).attr('juego') + "\n" +
                  $(this).attr('participantes'));

            detenerListeners();
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
    
    /*
    function getCookie(cname) {
        var name = cname + "=";
        var decodedCookie = decodeURIComponent(document.cookie);
        var ca = decodedCookie.split(';');
        
        for(var i = 0; i < ca.length; i++) {
        
            var c = ca[i];
            
            while (c.charAt(0) == ' ') {
        
                c = c.substring(1);
            }

            if (c.indexOf(name) == 0) {
        
                return c.substring(name.length, c.length);
            }
        }

        return "";
    }
    */
   
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