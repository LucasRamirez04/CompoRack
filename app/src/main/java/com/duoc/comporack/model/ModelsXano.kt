package com.duoc.comporack.model

//.kt para traer los datos de los endpoints (estilo DTO)

//TABLA USUARIO

data class Usuario(
    val id: Int = 0,
    val nom_usuario: String = "",
    val contrasena: String = "",
    val rol: String = "",
)

data class Racks(
    val id: Int = 0,
    val estado: String = "",
    val ubicacion: String = "",
    val utlimaInspeccion: Long? = null,
    val capacidad: Int = 0,
    val esOperativo: Boolean = true



)