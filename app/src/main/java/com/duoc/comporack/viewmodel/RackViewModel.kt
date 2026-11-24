package com.duoc.comporack.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duoc.comporack.model.RackUiState
import com.duoc.comporack.model.Racks
import com.duoc.comporack.repository.RackRepository
import kotlinx.coroutines.launch

class RackViewModel(
    private val repository: RackRepository = RackRepository()
) : ViewModel() {

    var uiState by mutableStateOf(RackUiState())
        private set

    private var listaOriginal: List<Racks> = emptyList()

    init {
        cargarRacks()
    }

    fun cargarRacks() {
        viewModelScope.launch {
            uiState = uiState.copy(cargando = true, error = null)
            try {
                val respuesta = repository.obtenerRacks()
                listaOriginal = respuesta
                uiState = uiState.copy(
                    racks = respuesta,
                    cargando = false
                )
            } catch (e: Exception) {
                uiState = uiState.copy(
                    cargando = false,
                    error = "Error al conectar con Xano: ${e.localizedMessage}"
                )
            }
        }
    }

    fun actualizarBusqueda(texto: String) {
        uiState = uiState.copy(busqueda = texto, errorBusqueda = null)
        aplicarFiltros()
    }

    fun alternarModalFiltro(mostrar: Boolean) {
        uiState = uiState.copy(mostrandoModalFiltro = mostrar)
    }

    fun aplicarFiltroUbicacion(ubicacion: String) {
        uiState = uiState.copy(
            filtroUbicacion = ubicacion,
            mostrandoModalFiltro = false
        )
        aplicarFiltros()
    }

    private fun aplicarFiltros() {
        val filtrados = listaOriginal.filter { rack ->
            val coincideTexto = uiState.busqueda.isBlank() ||
                    "RACK-${rack.id}".contains(uiState.busqueda, ignoreCase = true) ||
                    rack.ubicacion.contains(uiState.busqueda, ignoreCase = true)

            val coincideUbicacion = uiState.filtroUbicacion == "Todos" ||
                    rack.ubicacion.contains(uiState.filtroUbicacion, ignoreCase = true)

            coincideTexto && coincideUbicacion
        }
        uiState = uiState.copy(racks = filtrados)
    }
}