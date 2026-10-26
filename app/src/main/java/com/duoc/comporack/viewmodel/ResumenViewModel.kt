package com.duoc.comporack.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.duoc.comporack.model.RegistroAnterior
import com.duoc.comporack.repository.ResumenRepository

class ResumenViewModel : ViewModel() {
    private val repository =  ResumenRepository()

    var registro by mutableStateOf(repository.obtenerRegistroAnterior())
        private set

}