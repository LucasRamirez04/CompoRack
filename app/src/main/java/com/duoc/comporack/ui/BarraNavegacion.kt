package com.duoc.comporack.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController

// Barra inferior compartida
// rutaActual indica qué ítem se muestra seleccionado
@Composable
fun BarraNavegacion(
    navController: NavController,
    rutaActual: String
) {
    NavigationBar(containerColor = Color(0xFF0F172A)) {
        NavigationBarItem(
            selected = rutaActual == "racks",
            onClick = { irARuta(navController, "racks") },
            icon = { Icon(Icons.Filled.Storage, contentDescription = "Racks") },
            label = { Text("Racks") },
            colors = coloresItemBarra()
        )
        NavigationBarItem(
            selected = rutaActual == "nuevo_registro",
            onClick = { irARuta(navController, "nuevo_registro") },
            icon = { Icon(Icons.Filled.QrCodeScanner, contentDescription = "Escáner") },
            label = { Text("Escáner") },
            colors = coloresItemBarra()
        )
        NavigationBarItem(
            selected = rutaActual == "perfil",
            onClick = { irARuta(navController, "perfil") },
            icon = { Icon(Icons.Filled.Person, contentDescription = "Perfil") },
            label = { Text("Perfil") },
            colors = coloresItemBarra()
        )
    }
}

// Navega a la ruta dejando "racks" como base del historial para que no se acumulen pantallas
private fun irARuta(navController: NavController, ruta: String) {
    navController.navigate(ruta) {
        popUpTo("racks")
        launchSingleTop = true
    }
}

// Paleta colores
@Composable
private fun coloresItemBarra() = NavigationBarItemDefaults.colors(
    selectedIconColor = Color.White,
    selectedTextColor = Color(0xFF3B82F6),
    indicatorColor = Color(0xFF2563EB),
    unselectedIconColor = Color(0xFF64748B),
    unselectedTextColor = Color(0xFF64748B)
)