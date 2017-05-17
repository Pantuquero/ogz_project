$(function () {
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
    
    
    anadirEventos();
    /**
     * Función para cargar los eventos de la BDD
     * @returns {undefined}
     */
    function anadirEventos(){
        cal.setData( {
            '05-25-2017' : 
                '<span title="19:30 - 23:00" class="evento" juego="Tabletop simulator">\n\
                    <b><u>19:30 - 23:00</u></b><br>\n\
                    <b><i>Tabletop simulator</i></b><br>\n\
                    @KgsRocks,@Mike\n\
                </span>'
        } );
    }
    
    //Botones parte superior derecha
    $('#custom-next').on('click', function () {
        cal.gotoNextMonth(updateMonthYear);
    });
    $('#custom-prev').on('click', function () {
        cal.gotoPreviousMonth(updateMonthYear);
    });
    $('#custom-current').on('click', function () {
        cal.gotoNow(updateMonthYear);
        //$.session.set("prueba","no yay");
        //alert('hola');
        //alert($.session.get("prueba"));
    });

    // Información del evento seleccionado
    $('.evento').on('click', function() {
        //alert($(this).text());
        alert($(this).attr('juego'));
        
        detenerListeners();
    });
    
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