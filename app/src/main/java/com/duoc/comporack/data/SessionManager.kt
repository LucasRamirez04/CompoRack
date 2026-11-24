package com.duoc.comporack.data

import android.content.Context
import android.content.SharedPreferences

class SessionManager (context: Context){
    //Inicializar SharedPreferences para implementar la persistencia
    private val doc: SharedPreferences = context.getSharedPreferences("sesion-comporack", Context.MODE_PRIVATE)


    //Funcion para agregar los datos usuario y estado de inicio de sesion al archivo XML
    fun guardarSesion(id: Int, nombre: String, rol: String){
        doc.edit().apply {
            putBoolean("SesionIniciada",true)
            putInt("UsuarioId", id)
            putString("NombreUsuario",nombre)
            putString("Rol",rol)
            apply()
        }
    }

    //Funcion para comprobar si la sesion esta iniciada
    fun estaIniciada(): Boolean{
        return doc.getBoolean("SesionIniciada",false)
    }

    //Funcion para obtener el idUsuario y mostrarlo en el perfil
    fun obtenerIdUsuario(): Int{
        return doc.getInt("UsuarioId",-1)
    }

    //Funcion para obtener el nombre y mostrarlo en el perfil
    fun obtenerUsuario(): String{
        return doc.getString("NombreUsuario","") ?: ""
    }

    //Funcion para obtener el rol y mostrarlo en el perfil
    fun obtenerRol(): String{
        return doc.getString("Rol","") ?: ""
    }

    //Funcion para cerrar la sesion al apretar el boton "cerrar sesion (vista perfil)"
    fun cerrarSesion(){
        doc.edit().apply {
            putBoolean("SesionIniciada",false)
            putInt("UsuarioId",-1)
            putString("NombreUsuario","")
            putString("Rol","")
            apply()
        }
    }

}