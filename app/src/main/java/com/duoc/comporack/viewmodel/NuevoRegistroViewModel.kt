package com.duoc.comporack.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duoc.comporack.model.ComponenteRack
import com.duoc.comporack.model.MensajeError
import com.duoc.comporack.repository.NuevoRegistroRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

// Mensaje que se muestra cuando no hay internet o el servidor no responde
private const val MENSAJE_SIN_CONEXION = "Sin conexión. Revisa tu internet e intenta de nuevo"

class NuevoRegistroViewModel : ViewModel() {

    private val repository = NuevoRegistroRepository()

    // Se llena cuando se carga la lista de componentes desde Xano
    var totalEsperado by mutableStateOf(0)
        private set

    // Paso actual del registro: 1 = validar existencia, 2 = escanear componente
    var paso by mutableStateOf(1)
        private set

    var rack by mutableStateOf("")
        private set

    var permisoCamara by mutableStateOf(false)
        private set

    var errorLectura by mutableStateOf(MensajeError())
        private set

    // true mientras se espera la respuesta de la API
    var cargando by mutableStateOf(false)
        private set

    // Componente leido mientras no sea null se muestra el modal de confirmacion
    var componenteDetectado by mutableStateOf<ComponenteRack?>(null)
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

    var errorFinalizar by mutableStateOf(MensajeError())
        private set

    // true cuando el PATCH del rack fue exitoso (la pantalla muestra la notificacion)
    var registroGuardado by mutableStateOf(false)
        private set

    fun actualizarPermisoCamara(concedido: Boolean) {
        permisoCamara = concedido
    }

    // Paso 1
    // Simula escanear QR: busca el rack en Xano y si existe carga los componentes y avanza al paso 2
    fun simularLecturaQr() {
        if (cargando) return   // Evita enviar dos consultas si se toca el boton dos veces

        viewModelScope.launch {
            cargando = true
            try {
                val idLeido = repository.leerQrRack()
                val rackEncontrado = repository.validarRack(idLeido)
                repository.cargarCatalogo()
                totalEsperado = repository.totalEsperado()
                rack = "Rack ${rackEncontrado.id}"
                errorLectura = MensajeError()
                paso = 2
            } catch (e: IOException) {
                errorLectura = MensajeError(true, MENSAJE_SIN_CONEXION)
            } catch (e: HttpException) {
                errorLectura = MensajeError(true, "El código leído no corresponde a un rack (código ${e.code()})")
            } finally {
                cargando = false
            }
        }
    }

    // Paso 2

    // Simula escanear el codigo de barras de un componente
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

    // Primero valida el formato y luego consulta a la API si el componente existe
    fun enviarCodigoManual() {
        if (cargando) return
        if (cantidadConfirmados >= totalEsperado) {
            errorManual = MensajeError(true, "Ya se registraron todos los componentes esperados")
            return
        }
        errorManual = repository.validarCodigoManual(codigoManual)
        if (errorManual.tieneError) return

        viewModelScope.launch {
            cargando = true
            try {
                componenteDetectado = repository.validarComponenteManual(codigoManual)
                cerrarManual()
            } catch (e: IOException) {
                errorManual = MensajeError(true, MENSAJE_SIN_CONEXION)
            } catch (e: HttpException) {
                errorManual = if (e.code() == 404) {
                    MensajeError(true, "El código no existe en la base de datos")
                } else {
                    MensajeError(true, "Error del servidor (código ${e.code()})")
                }
            } finally {
                cargando = false
            }
        }
    }

    // Finalizar

    fun abrirFinalizar() {
        mostrarFinalizar = true
    }

    fun cerrarFinalizar() {
        mostrarFinalizar = false
        errorFinalizar = MensajeError()
    }

    // Envia el PATCH del rack. Si sale bien, registroGuardado = true y la pantalla muestra la notificacion
    fun finalizarRegistro() {
        if (cargando) return

        viewModelScope.launch {
            cargando = true
            errorFinalizar = MensajeError()
            try {
                repository.finalizarRegistro()
                registroGuardado = true
            } catch (e: IOException) {
                errorFinalizar = MensajeError(true, MENSAJE_SIN_CONEXION)
            } catch (e: HttpException) {
                errorFinalizar = MensajeError(true, "No se pudo guardar el registro (código ${e.code()})")
            } finally {
                cargando = false
            }
        }
    }
}