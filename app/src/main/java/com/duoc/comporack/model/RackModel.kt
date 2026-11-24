package com.duoc.comporack.model
data class RackUiState(
    val racks: List<Racks> = emptyList(),
    val busqueda: String = "",
    val filtroUbicacion: String = "Todos",
    val errorBusqueda: String? = null,
    val mostrandoModalFiltro: Boolean = false,
    val cargando: Boolean = false,
    val error: String? = null
)