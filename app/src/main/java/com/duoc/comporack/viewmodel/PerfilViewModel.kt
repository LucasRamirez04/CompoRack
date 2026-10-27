package com.duoc.comporack.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.duoc.comporack.model.MensajeError
import com.duoc.comporack.model.PerfilModel
import com.duoc.comporack.repository.PerfilRepository

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

    // Recibe el usuario guardado en la sesión y arma el perfil
    fun cargarPerfil(usuario: String) {
        perfil = repository.obtenerPerfil(usuario)
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