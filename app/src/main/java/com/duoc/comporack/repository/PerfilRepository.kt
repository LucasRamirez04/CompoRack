package com.duoc.comporack.repository

import com.duoc.comporack.model.MensajeError
import com.duoc.comporack.model.PerfilModel

class PerfilRepository {
    private var peticion = ""
    private var errorPeticion = MensajeError()

    // Solo el usuario cambia (viene del login). Rol e ID de empleado son datos ejemplo
    fun obtenerPerfil(usuario: String): PerfilModel = PerfilModel(
        usuario = usuario,
        rol = "Inspector Técnico OT / IT",
        idEmpleado = "CMC-OT-0842"
    )

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