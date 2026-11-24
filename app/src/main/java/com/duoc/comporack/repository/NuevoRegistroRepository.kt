package com.duoc.comporack.repository

import com.duoc.comporack.model.ComponenteRack
import com.duoc.comporack.model.MensajeError
import com.duoc.comporack.model.Racks
import com.duoc.comporack.remote.RetrofitInstance

// Id del rack que "lee" el QR simulado
private const val ID_RACK_SIMULADO = 1

class NuevoRegistroRepository {
    // Rack validado en el paso 1 (de Xano)
    private var rackActual: Racks? = null

    // Componentes de Xano (reemplaza catalogo fijo)
    private var catalogo: List<ComponenteRack> = emptyList()
    private val componentesConfirmados = mutableListOf<ComponenteRack>()

    fun totalEsperado(): Int = catalogo.size

    // Paso 1: validación de que exista
    // Simula la lectura del QR pegado en el rack: entrega el id del rack
    fun leerQrRack(): Int = ID_RACK_SIMULADO

    // GET racks/{id}: si el rack no existe, Retrofit lanza HttpException
    suspend fun validarRack(id: Int): Racks {
        val rackEncontrado = RetrofitInstance.api.getRackbyId(id)
        rackActual = rackEncontrado
        return rackEncontrado
    }

    // GET componentes_racks: trae la lista de componentes de la base de datos
    suspend fun cargarCatalogo() {
        catalogo = RetrofitInstance.api.getComponentes()
    }

    // Paso 2: componentes
    // Simula la lectura codigo y entrega el siguiente componente de la lista
    // Devuelve null si ya no quedan componentes
    fun leerComponente(): ComponenteRack? = catalogo.getOrNull(componentesConfirmados.size)

    // GET componentes_racks/validar/{num_serie}: valida codigo escrito a mano
    suspend fun validarComponenteManual(codigo: String): ComponenteRack =
        RetrofitInstance.api.validarComponente(codigo.uppercase())

    // Valida el formato del código antes de consultar a la API
    fun validarCodigoManual(codigo: String): MensajeError {
        if (codigo.isBlank()) {
            return MensajeError(true, "El código no puede estar vacío")
        }
        if (codigo.length < 5) {
            return MensajeError(true, "El código debe tener al menos 5 caracteres")
        }
        if (!codigo.all { it.isLetterOrDigit() || it == '-' }) {
            return MensajeError(true, "Solo se permiten letras, números y guiones")
        }
        return MensajeError()
    }

    fun confirmarComponente(componente: ComponenteRack) {
        componentesConfirmados.add(componente)
    }

    fun cantidadConfirmados(): Int = componentesConfirmados.size

    // PATCH racks/{id}: actualiza la fecha de ultima inspeccion del rack
    suspend fun finalizarRegistro() {
        val rack = rackActual ?: return
        val rackActualizado = rack.copy(ultimaInspeccion = System.currentTimeMillis())
        RetrofitInstance.api.actualizarRack(rack.id, rackActualizado)
    }
}