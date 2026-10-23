package com.duoc.comporack.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.duoc.comporack.viewmodel.LoginViewModel
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.duoc.comporack.R

@Composable
fun LoginScreen(
    //variables de la vista
    navController: NavController,
    viewModel: LoginViewModel = viewModel()
){
    //columna principal del login
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween // Empuja los extremos
    ) {
        // 1. Imagen a la izquierda (Logo)
        Image(
            painter = painterResource(id = R.drawable.compo_rack_logo), // Reemplaza por tu recurso en res/drawable
            contentDescription = "Logo CompoRack",
            modifier = Modifier.size(150.dp)
        )

        // 2. Columna con los textos a la derecha
        Column(
            horizontalAlignment = Alignment.CenterHorizontally // Alinea los textos hacia la derecha
        ) {
            Text(
                text = "CompoRack",
                style = MaterialTheme.typography.headlineMedium,
                color = Color(0xFF2563EB)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Control De Infraestructura",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    // Aquí invocamos la pantalla
    // Al ser una previsualización, podemos usar un NavController simulado
    LoginScreen(
        navController = androidx.navigation.compose.rememberNavController()
    )
}