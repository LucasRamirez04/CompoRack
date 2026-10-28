package com.duoc.comporack.repository

import com.duoc.comporack.model.ComponenteDetectado
import com.duoc.comporack.model.MensajeError
import com.duoc.comporack.model.RegistroModel

class NuevoRegistroRepository {
    private var registro = RegistroModel()
    private val componentesConfirmados = mutableListOf<ComponenteDetectado>()

    // Catálogo ficticio de componentes (datos de prueba)
    private val catalogo = listOf(
        ComponenteDetectado("Cisco Catalyst 9300", "SWITCH DE RED", "FCW2536L0AB", 96),
        ComponenteDetectado("Patch Panel 24p", "CONECTIVIDAD", "PP24-A01-038", 94),
        ComponenteDetectado("Dell PowerEdge R750", "SERVIDOR", "8DJ4XM3", 98),
        ComponenteDetectado("UPS Eaton 9PX", "ENERGÍA", "ETN9PX-9281", 97),
        ComponenteDetectado("APC Rack PDU 2G", "DISTRIBUCIÓN", "PDU-APC-8941", 95)
    )

    val totalEsperado: Int = catalogo.size

    // Paso 1: validación de que exista
    // Simula lectura del QR pegado en el rack
    fun leerQrRack(): String = "RACK-A01"

    // Valida que el código leído tenga formato de rack y lo guarda en el registro
    fun validarRack(codigo: String): Boolean {
        if (!codigo.startsWith("RACK-")) {
            registro.rackValidado = false
            return false
        }
        registro.rack = codigo
        registro.rackValidado = true
        return true
    }

    // Paso 2: componentes
    // Simula la lectura del codigo de barras: entrega el siguiente componente del catalogo
    fun leerComponente(): ComponenteDetectado =
        catalogo[componentesConfirmados.size % catalogo.size]

    // Igual que la lectura, pero usando el código escrito a mano
    fun leerComponenteManual(codigo: String): ComponenteDetectado =
        leerComponente().copy(serie = codigo.uppercase(), coincidencia = 100)

    // Valida el código ingresado manualmente
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

    fun confirmarComponente(componente: ComponenteDetectado) {
        componentesConfirmados.add(componente)
    }

    fun cantidadConfirmados(): Int = componentesConfirmados.size
}