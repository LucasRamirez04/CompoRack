package com.duoc.comporack.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duoc.comporack.model.MensajeError
import com.duoc.comporack.repository.LoginRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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

    // true mientras se muestra el circulo de carga
    var cargando by mutableStateOf(false)
        private set

    // true cuando termino la carga y se debe mostrar el mensaje de ingreso exitoso
    var ingresoExitoso by mutableStateOf(false)
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

    // Valida los campos y, si estan bien, simula 2 segundos de carga antes de ingresar
    fun ingresar() {
        if (!iniciarSesion()) return

        viewModelScope.launch {
            cargando = true
            delay(2000)
            cargando = false
            ingresoExitoso = true
        }
    }
}