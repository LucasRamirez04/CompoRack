package com.duoc.comporack.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duoc.comporack.model.MensajeError
import com.duoc.comporack.repository.LoginRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import com.duoc.comporack.model.Usuario

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

    var usuarioAutenticado by mutableStateOf<Usuario?>(null)

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
        val errUser = repository.getErrorUsuario()
        val errPass = repository.getErrorContraseña()
        errorUsuario = MensajeError(tieneError = errUser.tieneError, mensaje = errUser.mensaje)
        errorContraseña = MensajeError(tieneError = errPass.tieneError, mensaje = errPass.mensaje)
        return esValido
    }

    // Valida los campos
    fun ingresar() {
        if (!iniciarSesion()) return

        viewModelScope.launch {
            cargando = true
            try {
                val userXano = repository.iniciarSesionApi()
                usuarioAutenticado = userXano
                ingresoExitoso = true
            } catch (e: HttpException) {
                // Xano lanzó el Throw Error (400 Bad Request) por credenciales inválidas
                repository.mostrarErrorCredenciales("Usuario o contraseña incorrectos")
                errorContraseña = MensajeError(tieneError = true, mensaje = "Usuario o contraseña incorrectos")
            } catch (e: Exception) {
                // Error de red (sin internet o URL de Xano incorrecta)
                repository.mostrarErrorCredenciales("Error de conexión con el servidor")
                errorContraseña = MensajeError(tieneError = true, mensaje = "Error de conexión con el servidor")
            } finally {
                cargando = false
            }
        }
    }
}