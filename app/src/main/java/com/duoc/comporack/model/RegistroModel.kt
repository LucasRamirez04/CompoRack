package com.duoc.comporack.model

data class RegistroModel(
    var rack: String = "",
    var rackValidado: Boolean = false
)

// Componente de ejemplo que "detecta" el sistema al leer el código de barras
data class ComponenteDetectado(
    val nombre: String,
    val categoria: String,
    val serie: String,
    val coincidencia: Int
)