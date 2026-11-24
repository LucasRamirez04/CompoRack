package com.duoc.comporack.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duoc.comporack.model.Racks
import com.duoc.comporack.repository.RackRepository
import kotlinx.coroutines.launch

data class DetalleRackUiState(
    val rack: Racks? = null,
    val cargando: Boolean = false,
    val error: String? = null
)

class DetalleRackViewModel(
    private val repository: RackRepository = RackRepository()
) : ViewModel() {

    var uiState by mutableStateOf(DetalleRackUiState())
        private set

    fun cargarRack(rackId: Int) {
        viewModelScope.launch {
            uiState = uiState.copy(cargando = true, error = null)
            try {
                val rackObtenido = repository.obtenerRackPorId(rackId)
                uiState = uiState.copy(
                    rack = rackObtenido,
                    cargando = false
                )
            } catch (e: Exception) {
                uiState = uiState.copy(
                    cargando = false,
                    error = "Error al cargar el rack: ${e.localizedMessage}"
                )
            }
        }
    }
}