package com.duoc.comporack.repository

import com.duoc.comporack.model.MensajeError
import com.duoc.comporack.model.PerfilModel
import com.duoc.comporack.remote.RetrofitInstance

class PerfilRepository {
    private var peticion = ""
    private var errorPeticion = MensajeError()

    // GET usuario/{id} trae el usuario desde Xano y arma el perfil con su rol (id de ejemplo)
    suspend fun obtenerPerfil(idUsuario: Int): PerfilModel {
        val usuarioApi = RetrofitInstance.api.getUsuario(idUsuario)
        return PerfilModel(
            usuario = usuarioApi.nom_usuario,
            rol = usuarioApi.rol,
            idEmpleado = "CMC-OT-0842"
        )
    }

    fun getErrorPeticion(): MensajeError = errorPeticion

    fun actualizarPeticion(nuevaPeticion: String) {
        peticion = nuevaPeticion
    }

    // Validación del formulario del modal
    fun validarPeticion(): Boolean {
        if (peticion.isBlank()) {
            errorPeticion = MensajeError(true, "La petición no puede estar vacía")
            return false
        }
        if (peticion.length < 10) {
            errorPeticion = MensajeError(true, "Describe el cambio con al menos 10 caracteres")
            return false
        }
        errorPeticion = MensajeError()
        return true
    }
}