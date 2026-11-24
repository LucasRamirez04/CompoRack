package com.duoc.comporack.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.duoc.comporack.model.MensajeError
import com.duoc.comporack.model.PerfilModel
import com.duoc.comporack.repository.PerfilRepository
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

// Mensaje que se muestra cuando no hay internet o el servidor no responde
private const val MENSAJE_SIN_CONEXION = "Sin conexión. Revisa tu internet e intenta de nuevo"

class PerfilViewModel : ViewModel() {

    private val repository = PerfilRepository()

    var perfil by mutableStateOf(PerfilModel())
        private set

    var peticion by mutableStateOf("")
        private set

    var errorPeticion by mutableStateOf(MensajeError())
        private set

    var mostrarModal by mutableStateOf(false)
        private set

    var peticionEnviada by mutableStateOf(false)
        private set

    // true mientras se espera la respuesta de la API
    var cargando by mutableStateOf(false)
        private set

    var mensajeError by mutableStateOf(MensajeError())
        private set

    // Recibe el usuario guardado en la sesion y arma el perfil
    // Pide el perfil a la API, si falla muestra solo el nombre guardado en la sesion
    fun cargarPerfil(usuarioSesion: String, idUsuario: Int) {
        if (cargando) return   // Evita dos consultas seguidas

        viewModelScope.launch {
            cargando = true
            mensajeError = MensajeError()
            try {
                perfil = repository.obtenerPerfil(idUsuario)
            } catch (e: IOException) {
                perfil = PerfilModel(usuario = usuarioSesion)
                mensajeError = MensajeError(true, MENSAJE_SIN_CONEXION)
            } catch (e: HttpException) {
                perfil = PerfilModel(usuario = usuarioSesion)
                mensajeError = MensajeError(true, "No se pudo cargar el perfil (código ${e.code()})")
            } finally {
                cargando = false
            }
        }
    }

    fun abrirModal() {
        mostrarModal = true
    }

    fun cerrarModal() {
        mostrarModal = false
        peticion = ""
        repository.actualizarPeticion("")
        errorPeticion = MensajeError()
    }

    fun actualizarPeticion(nuevaPeticion: String) {
        peticion = nuevaPeticion
        repository.actualizarPeticion(nuevaPeticion)
    }

    fun enviarPeticion() {
        if (repository.validarPeticion()) {
            peticionEnviada = true
            cerrarModal()
        } else {
            errorPeticion = repository.getErrorPeticion()
        }
    }
}