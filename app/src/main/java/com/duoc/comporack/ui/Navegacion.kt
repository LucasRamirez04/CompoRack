package com.duoc.comporack.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.duoc.comporack.data.SessionManager

@Composable
fun Navegacion(){
    val navController = rememberNavController()

    //Configuramos la ruta inicial segun este iniciada la sesion o no
    val sessionManager : SessionManager = SessionManager(LocalContext.current)
    val rutaInicial: String = if (sessionManager.estaIniciada()) "racks" else "login"

    //Declaramos el NavHost con la ruta inicial
    NavHost(
        navController = navController,
        startDestination = rutaInicial
    ) {
        //Ruta 1: Login
        composable(route = "login") {
            LoginScreen(navController = navController)
        }
        //Ruta 2: Racks
        composable(route = "racks"){
            RacksScreen(navController = navController)
        }
        //Ruta 3: Resumen
        composable(route = "resumen"){
            ResumenScreen(navController = navController)
        }
    }
}