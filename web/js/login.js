// Listeners
document.getElementById("boton_login").addEventListener("click", validarLogin);
document.getElementById("boton_registro").addEventListener("click", validarRegistro);

// Funciones____________________________________________________________________

function validarLogin() {
    
    $("#formulario_login").validate({
        rules: {
            nombreusu: {
                required: true,
                minlength: 5,
                maxlength: 12
            },
            contrasena: {
                required: true,
                minlength: 8,
                maxlength: 15
            }
        },
        messages: {
            nombreusu: {
                required: "",
                minlength: "",
                maxlength: ""
            },
            contrasena: {
                required: "",
                minlength: "",
                maxlength: ""
            }
        }
    });
}

function validarRegistro() {
    
    $("#formulario_registro").validate({
        rules: {
            correoreg: {
                required: true,
                email: true,
                maxlength: 50
            },
            nombreusureg: {
                required: true,
                minlength: 5,
                maxlength: 12
            },
            contrasenareg: {
                required: true,
                minlength: 8,
                maxlength: 15
            },
            contrasenaverreg: {
                required:true,
                minlength: 8,
                maxlength: 15,
                equalTo: "#contrasenareg"
            }
        }
    });
    
    comprobarCampos;
}