package com.duoc.comporack.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.duoc.comporack.model.RackItem
import com.duoc.comporack.model.RackUiState
import com.duoc.comporack.repository.RackRepository

class RackViewModel(
    private val repository: RackRepository = RackRepository()
) : ViewModel() {

    var uiState by mutableStateOf(RackUiState())
        private set

    private var listaBaseRacks = emptyList<RackItem>()

    init {
        cargarRacks()
    }

    private fun cargarRacks() {
        listaBaseRacks = repository.obtenerRacks()
        uiState = uiState.copy(racks = listaBaseRacks)
    }

    fun actualizarBusqueda(nuevoTexto: String) {
        val error = if (nuevoTexto.contains(Regex("[^a-zA-Z0-9\\s-]"))) {
            "No se permiten caracteres especiales"
        } else {
            null
        }

        uiState = uiState.copy(busqueda = nuevoTexto, errorBusqueda = error)

        if (error == null) {
            filtrarRacks()
        }
    }

    fun alternarModalFiltro(mostrar: Boolean) {
        uiState = uiState.copy(mostrandoModalFiltro = mostrar)
    }

    fun aplicarFiltroUbicacion(ubicacion: String) {
        uiState = uiState.copy(filtroUbicacion = ubicacion, mostrandoModalFiltro = false)
        filtrarRacks()
    }

    private fun filtrarRacks() {
        val resultados = listaBaseRacks.filter { rack ->
            val coincideTexto = rack.id.contains(uiState.busqueda, ignoreCase = true) ||
                    rack.ubicacion.contains(uiState.busqueda, ignoreCase = true)

            val coincideUbicacion = if (uiState.filtroUbicacion == "Todos") {
                true
            } else {
                rack.ubicacion.contains(uiState.filtroUbicacion, ignoreCase = true)
            }

            coincideTexto && coincideUbicacion
        }

        uiState = uiState.copy(racks = resultados)
    }
}