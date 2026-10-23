package com.duoc.comporack.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.duoc.comporack.R
import com.duoc.comporack.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = viewModel()
) {
    // Fondo oscuro que abarca toda la pantalla
    Column(
        modifier = Modifier.fillMaxSize().background(Color(0xFF0A1128)).padding(24.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        //Fila para el logo + nombre app
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            //Imagen App
            Image(
                painter = painterResource(id = R.drawable.compo_rack_logo),
                contentDescription = "Logo CompoRack",
                modifier = Modifier.size(80.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            //Columna de Nombre App + Descripcion
            Column{
                Text(
                    text = "CompoRack",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Control de Infraestructura",
                    style = MaterialTheme.typography.titleSmall,
                    color = Color(0xFF64748B),
                    letterSpacing = 1.2.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(36.dp))

        //Titulo y subtitulo
        Text(
            text = "Inicio de Sesion",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            lineHeight = 34.sp
        )
        Text(
            text = "Inicia Sesion Con Tus Credenciales Institucionales",
            style = MaterialTheme.typography.bodySmall,
            color = Color(0xFF94A3B8),
            fontWeight = FontWeight.Bold,
            lineHeight = 34.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        //Input Usuario
        OutlinedTextField(
            value = viewModel.login.usuario,
            onValueChange = { viewModel.actualizarUsuario(it)},
            label = {Text("Usuario")},
            isError = viewModel.errorUsuario.tieneError,
            supportingText = {
                if (viewModel.errorUsuario.tieneError) {
                    Text(
                        text = viewModel.errorUsuario.mensaje,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedBorderColor = Color(0xFF3B82F6),
                unfocusedBorderColor = Color(0xFF334155),
                focusedLabelColor = Color(0xFF3B82F6),
                unfocusedLabelColor = Color(0xFF94A3B8)
            )
        )






    }
}



// --- PREVIEW PARA ANDROID STUDIO ---
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun VistaLogin() {
    LoginScreen(navController = rememberNavController())
}