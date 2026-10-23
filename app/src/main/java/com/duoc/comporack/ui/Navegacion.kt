package com.duoc.comporack.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Navegacion(){
    val navController = rememberNavController()

    //Declaramos el NavHost con la ruta inicial
    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        //Ruta 1: Login
        composable(route = "login") {
            LoginScreen(navController = navController)
        }
        //Ruta 2: Racks
        composable(route = "racks"){
            RacksScreen(navController = navController)
        }
    }
}