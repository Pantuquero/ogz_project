// Listeners
document.getElementById("unirse_grupo").addEventListener("click", validarGrupo);
document.getElementById("crear_grupo").addEventListener("click", validarGrupo);
document.getElementById("recargar").addEventListener("click", recargar);
document.getElementById("salir").addEventListener("click", salir);
document.getElementById("crear_evento").addEventListener("click", validarEvento);
document.getElementById("select_grupos").addEventListener("change",cambioGrupo);
document.getElementById("desde_hora").addEventListener("change", validarHoraHasta);
document.getElementById("hasta_hora").addEventListener("change", validarHoraDesde);
//document.getElementsByClassName("evento").addEventListener("click",infoEvento);
//document.getElementById("abandonar_grupo").addEventListener("click", validar);

// Al iniciar

$(document).ready(function(){
    
    //Cargo el grupo en el hidden del formulario 2
    var selector = document.getElementById("select_grupos");
    var valor = selector.options[0].value;
    document.getElementById("grupo_oculto").value = valor;
});

// Funciones____________________________________________________________________

function validarHoraHasta() {
    var hora_desde = $(this).val();
    var numero_desde = parseInt(hora_desde.substring(0,2));
    
    $( ".hora_hasta" ).each(function() {
        hora_hasta = $(this).val();
        numero_hasta = parseInt(hora_hasta.substring(0,2));
        
        if(numero_desde > numero_hasta){
            $(this).attr("hidden","true");
        }else {
            $(this).removeAttr("hidden");
        }
    });
}

function validarHoraDesde() {
    var hora_hasta = $(this).val();
    var numero_hasta = parseInt(hora_hasta.substring(0,2));
    
    $( ".hora_desde" ).each(function() {
        hora_desde = $(this).val();
        numero_desde = parseInt(hora_desde.substring(0,2));
        
        if(numero_hasta < numero_desde){
            $(this).attr("hidden","true");
        }else {
            $(this).removeAttr("hidden");
        }
    });
}

$.validator.addMethod("valueNotEquals", function(value, element, arg){
    return arg != value;
}, "Value must not equal arg.");

function validarGrupo() {
    
    $("#formulario_grupos").validate({
        rules: {
            entrada_texto: {
                required: true,
                minlength: 4,
                maxlength: 15
            }
        },
        errorPlacement: function(error, element) {
            element.attr("placeholder",error.text());
        }
    });
}

function validarEvento() {
    
    $("#formulario_eventos").validate({
        rules: {
            fecha_evento: {
                required: true,
                valueNotEquals: "dd/mm/aaaa"
            },
            desde_hora: {
                required: true,
                valueNotEquals: "From"
            },
            hasta_hora: {
                required: true,
                valueNotEquals: "To"
            },
            juego: {
                required: true
            }
        },
        messages: {
            fecha_evento: {
                required: "",
                valueNotEquals: ""
            },
            desde_hora: {
                required: "",
                valueNotEquals: ""
            },
            hasta_hora: {
                required: "",
                valueNotEquals: ""
            },
            juego: {
                required: ""
            }
        }
    });
}

function cambioGrupo() {
    // Cada vez que se modifica el grupo lo guardo en el hidden del formulario dos
    var selector = document.getElementById("select_grupos");
    var valor = selector.options[selector.selectedIndex].value;
    document.getElementById("grupo_oculto").value = valor;
    
    // También establezco la galleta para guardar el grupo actual
    document.cookie = "grupo_seleccionado=" + valor;
    
    recargar();
}

function recargar() {
    location.reload();
}

function salir () {
    window.location.replace("login.jsp");
}