package com.duoc.comporack.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.duoc.comporack.model.MensajeError
import com.duoc.comporack.repository.LoginRepository

class LoginViewModel : ViewModel() {

    private val repository = LoginRepository()

    // Variables reactivas directas (sin objetos agrupados ni .copy)
    var usuario by mutableStateOf("")
        private set

    var contraseña by mutableStateOf("")
        private set

    var contraseñaVisible by mutableStateOf(false)
        private set

    var errorUsuario by mutableStateOf(MensajeError())
        private set

    var errorContraseña by mutableStateOf(MensajeError())
        private set

    // Actualizaciones directas con asignación '='
    fun actualizarUsuario(nuevoUsuario: String) {
        usuario = nuevoUsuario
        repository.actualizarUsuario(nuevoUsuario)
    }

    fun actualizarContraseña(nuevaContraseña: String) {
        contraseña = nuevaContraseña
        repository.actualizarContraseña(nuevaContraseña)
    }

    fun alternarVisibilidad() {
        contraseñaVisible = !contraseñaVisible
    }

    fun iniciarSesion(): Boolean {
        val esValido = repository.validarCredenciales()
        errorUsuario = repository.getErrorUsuario()
        errorContraseña = repository.getErrorContraseña()
        return esValido
    }
}