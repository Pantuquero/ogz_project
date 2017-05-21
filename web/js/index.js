// Listeners
document.getElementById("unirse_grupo").addEventListener("click", validar);
document.getElementById("crear_grupo").addEventListener("click", validar);
//document.getElementById("abandonar_grupo").addEventListener("click", validar);

// Funciones____________________________________________________________________

function validar() {
    
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