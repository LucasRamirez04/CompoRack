package com.duoc.comporack.model

//.kt para traer los datos de los endpoints (estilo DTO)

//TABLA USUARIO

data class Usuario(
    val id: Int = 0,
    val nom_usuario: String = "",
    val contrasena: String = "",
    val rol: String = "",
)