package com.duoc.comporack.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duoc.comporack.model.RegistroAnterior
import com.duoc.comporack.repository.ResumenRepository
import kotlinx.coroutines.launch

class ResumenViewModel (): ViewModel() {
    private val repository =  ResumenRepository()

    var registro by mutableStateOf(
        RegistroAnterior(
            fecha = "Cargando...",
            horario = "--:--",
            tecnico = "",
            rack = ""
        )
    )
        private set

    var cargando by mutableStateOf(false)
        private set

    var mensajeError by mutableStateOf<String?>(null)
    private set

    fun cargarResumen(rackId: Int = 1, nombreTecnico : String = ""){
        viewModelScope.launch {
            cargando = true
            mensajeError = null
            try {
                repository.obtenerRegistroAnterior(rackId, nombreTecnico)
            }catch (e: Exception){
                mensajeError = "No se pudo cargar el resumen desde el servidor"
            }finally {
                cargando = false
            }
        }
    }
}