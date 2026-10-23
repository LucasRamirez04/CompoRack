package com.duoc.comporack.repository

import com.duoc.comporack.model.LoginModel
import com.duoc.comporack.model.MensajeError

class LoginRepository {
    //Instanciar Models
    private var login = LoginModel()
    private var errorUsuario = MensajeError()
    private var errorContraseña = MensajeError()

    //--- Funciones Gets ---
    fun getLogin() : LoginModel = login

    fun getErrorUsuario() : MensajeError = errorUsuario

    fun getErrorContraseña() : MensajeError = errorContraseña

    //--- Funciones actualizar Campos ---
    fun actualizarUsuario(nuevoUsuario : String){
        login.usuario = nuevoUsuario
    }

    fun actualizarContraseña(nuevaContraseña : String){
        login.contraseña = nuevaContraseña
    }

    fun alternarVisibilidad(){
        login.contraseñaVisible = !login.contraseñaVisible
    }

    //--- Funciones Validaciones ---
    fun validarUsuario() : Boolean{
        if (login.usuario.isBlank()){
            errorUsuario.tieneError = true
            errorUsuario.mensaje = "El usuario no puede estar vacio"
            return false
        }else{
            errorUsuario.tieneError = false
            errorUsuario.mensaje = ""
            return true
        }
    }

    fun validarContraseña() : Boolean{
        if (login.contraseña.isBlank()){
            errorContraseña.tieneError = true
            errorContraseña.mensaje = "La contraseña no puede estar vacia"
            return false
        }else{
            errorContraseña.tieneError = false
            errorContraseña.mensaje = ""
            return true
        }
    }

    fun validarCredenciales() : Boolean{
        var usuarioValidado = validarUsuario()
        var contraseñaValidada = validarContraseña()
        return usuarioValidado && contraseñaValidada
    }
}