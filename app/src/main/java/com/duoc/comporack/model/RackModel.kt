package com.duoc.comporack.model

data class RackItem(
    val id: String,
    val estado: String,
    val ubicacion: String,
    val ultimaInspeccion: String,
    val capacidad: String,
    val esOperativo: Boolean
)

data class RackUiState(
    val racks: List<RackItem> = emptyList(),
    val busqueda: String = "",
    val filtroUbicacion: String = "Todos",
    val errorBusqueda: String? = null,
    val mostrandoModalFiltro: Boolean = false
)