package com.duoc.comporack.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.duoc.comporack.model.ComponenteDetectado
import com.duoc.comporack.model.MensajeError
import com.duoc.comporack.repository.NuevoRegistroRepository

class NuevoRegistroViewModel : ViewModel() {

    private val repository = NuevoRegistroRepository()

    val totalEsperado = repository.totalEsperado

    // Paso actual del registro: 1 = validar existencia, 2 = escanear componente
    var paso by mutableStateOf(1)
        private set

    var rack by mutableStateOf("")
        private set

    var permisoCamara by mutableStateOf(false)
        private set

    var errorLectura by mutableStateOf(MensajeError())
        private set

    // Componente leido mientras no sea null se muestra el modal de confirmacion
    var componenteDetectado by mutableStateOf<ComponenteDetectado?>(null)
        private set

    var cantidadConfirmados by mutableStateOf(0)
        private set

    // Modal de ingreso manual
    var mostrarManual by mutableStateOf(false)
        private set

    var codigoManual by mutableStateOf("")
        private set

    var errorManual by mutableStateOf(MensajeError())
        private set

    // Modal de confirmación al finalizar
    var mostrarFinalizar by mutableStateOf(false)
        private set

    fun actualizarPermisoCamara(concedido: Boolean) {
        permisoCamara = concedido
    }

    // Paso 1
    // Simula escanear el QR del rack si es válido avanza al paso 2
    fun simularLecturaQr() {
        val codigoLeido = repository.leerQrRack()
        if (repository.validarRack(codigoLeido)) {
            rack = codigoLeido
            errorLectura = MensajeError()
            paso = 2
        } else {
            errorLectura = MensajeError(true, "El código leído no corresponde a un rack")
        }
    }

    // Paso 2

    // Simula escanear el código de barras de un componente
    fun simularLecturaCodigo() {
        if (cantidadConfirmados >= totalEsperado) {
            errorLectura = MensajeError(true, "Ya se registraron todos los componentes esperados")
        } else {
            errorLectura = MensajeError()
            componenteDetectado = repository.leerComponente()
        }
    }

    fun confirmarComponente() {
        componenteDetectado?.let { repository.confirmarComponente(it) }
        cantidadConfirmados = repository.cantidadConfirmados()
        componenteDetectado = null
    }

    fun descartarComponente() {
        componenteDetectado = null
    }

    // Ingreso manual

    fun abrirManual() {
        mostrarManual = true
    }

    fun cerrarManual() {
        mostrarManual = false
        codigoManual = ""
        errorManual = MensajeError()
    }

    fun actualizarCodigoManual(nuevoCodigo: String) {
        codigoManual = nuevoCodigo
    }

    fun enviarCodigoManual() {
        if (cantidadConfirmados >= totalEsperado) {
            errorManual = MensajeError(true, "Ya se registraron todos los componentes esperados")
            return
        }
        errorManual = repository.validarCodigoManual(codigoManual)
        if (!errorManual.tieneError) {
            componenteDetectado = repository.leerComponenteManual(codigoManual)
            cerrarManual()
        }
    }

    // Finalizar

    fun abrirFinalizar() {
        mostrarFinalizar = true
    }

    fun cerrarFinalizar() {
        mostrarFinalizar = false
    }
}