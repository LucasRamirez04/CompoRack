package com.duoc.comporack.model

data class LoginModel (
    var usuario : String = "",
    var contraseña : String = "",
    var contraseñaVisibler : Boolean = false
){
}