package com.duoc.comporack.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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
            RackScreen(navController = navController)
        }
        //Ruta 3: Resumen
        composable(
            route = "resumen/{rackId}",
            arguments = listOf(navArgument("rackId") { type = NavType.IntType })
        ) { backStackEntry ->
            val idRecibido = backStackEntry.arguments?.getInt("rackId") ?: 1
            ResumenScreen(
                navController = navController,
                rackId = idRecibido
            )
        }
        //Ruta 4: Detalle Rack
        composable(
            route = "detalle_rack/{rackId}",
            arguments = listOf(navArgument("rackId") { type = NavType.IntType })
        ) { backStackEntry ->
            val rackId = backStackEntry.arguments?.getInt("rackId") ?: 0
            DetalleRackScreen(navController = navController, rackId = rackId)
        }
        //Ruta 5: Perfil
        composable(route = "perfil") {
            PerfilScreen(navController = navController)
        }
        //Ruta 6: Nuevo registro
        composable(route = "nuevo_registro") {
            NuevoRegistroScreen(navController = navController)
        }
    }
}