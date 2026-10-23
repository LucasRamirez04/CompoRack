package com.duoc.comporack.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.duoc.comporack.model.LoginModel
import com.duoc.comporack.model.MensajeError
import com.duoc.comporack.repository.LoginRepository

class LoginViewModel : ViewModel() {

    //Instanciar Repository
    private val repository = LoginRepository()

    //Declarar Variables "reactivas"
    var login: LoginModel by mutableStateOf(repository.getLogin())
    var errorUsuario : MensajeError by mutableStateOf(repository.getErrorUsuario())
    var errorContraseña : MensajeError by mutableStateOf(repository.getErrorContraseña())

    //Funciones para actualizar variables
    fun actualizarUsuario(nuevoUsuario : String){
        repository.actualizarUsuario(nuevoUsuario)
        login = repository.getLogin().copy()
    }

    fun actualizarContraseña(nuevaContraseña : String){
        repository.actualizarContraseña(nuevaContraseña)
        login = repository.getLogin().copy()
    }

    fun alternarVisibilidad (){
        repository.alternarVisibilidad()
        login = repository.getLogin().copy()
    }

    fun iniciarSesion() : Boolean{
        val validacion = repository.validarCredenciales()
        errorUsuario = repository.getErrorUsuario().copy()
        errorContraseña = repository.getErrorContraseña().copy()
        return validacion
    }
}