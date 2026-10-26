package com.duoc.comporack.data

import android.content.Context
import android.content.SharedPreferences

class SessionManager (context: Context){
    //Inicializar SharedPreferences para implementar la persistencia
    private val doc: SharedPreferences = context.getSharedPreferences("sesion-comporack", Context.MODE_PRIVATE)


    //Funcion para agregar los datos usuario y estado de inicio de sesion al archivo XML
    fun guardarSesion(nombre: String){
        doc.edit().apply {
            putBoolean("SesionIniciada",true)
            putString("Usuario",nombre)
            apply()
        }
    }

    //Funcion para comprobar si la sesion esta iniciada
    fun estaIniciada(): Boolean{
        return doc.getBoolean("SesionIniciada",false)
    }

    //Funcion para obtener el nombre y mostralo en el perfil
    fun obtenerUsuario(): String{
        //Funcion para cerrar la sesion al apretar el boton "cerrar sesion (vista perfil)"
        return doc.getString("Usuario","") ?: ""
    }

    //Funcion para cerrar la sesion al apretar el boton "cerrar sesion (vista perfil)"
    fun cerrarSesion(){
        doc.edit().apply {
            putBoolean("SesionIniciada",false)
            putString("Usuario","")
            apply()
        }
    }

}