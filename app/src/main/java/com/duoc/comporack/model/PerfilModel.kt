package com.duoc.comporack.model

data class PerfilModel(
    val nombreCompleto: String,
    val email: String,
    val rol: String,
    val idEmpleado: String,
    val cuentaActiva: Boolean = true
)