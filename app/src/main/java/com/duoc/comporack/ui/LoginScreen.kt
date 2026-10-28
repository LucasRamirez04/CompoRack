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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import com.duoc.comporack.data.SessionManager
import com.duoc.comporack.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    navController: NavController
) {
    //Intanciamos el LoginViewModel
    val viewModel: LoginViewModel = viewModel()
    //Instanciamos el SessionManager
    val context = LocalContext.current
    val sessionManager : SessionManager = remember {SessionManager(context)}

    // Fondo oscuro que abarca toda la pantalla
    Column(
        modifier = Modifier.fillMaxSize().background(Color(0xFF0A1128)).padding(24.dp),
        horizontalAlignment = Alignment.Start
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
                    style = MaterialTheme.typography.titleLarge,
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


        Spacer(modifier = Modifier.weight(1f))

        //Titulo y subtitulo
        Text(
            text = "BIENVENIDO A COMPORACK",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            lineHeight = 34.sp,
        )
        Text(
            text = "Inicia Sesion Con Tus Credenciales Institucionales",
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF94A3B8),
            fontWeight = FontWeight.Bold,
            lineHeight = 40.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        //Input Usuario
        OutlinedTextField(
            value = viewModel.usuario,
            onValueChange = { viewModel.actualizarUsuario(it)},
            label = {Text("Usuario")},
            isError = viewModel.errorUsuario.tieneError,
            //Mensaje que sale abajo del input si hay error
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
                unfocusedLabelColor = Color(0xFF94A3B8),
                cursorColor = Color.White,
                errorTextColor = Color.White
            )
        )

        //Input Contraseña
        OutlinedTextField(
            value = viewModel.contraseña,
            onValueChange = { viewModel.actualizarContraseña(it)},
            label = {Text("Contraseña")},
            isError = viewModel.errorContraseña.tieneError,
            //Mensaje que sale abajo del input si hay error
            supportingText = {
                if (viewModel.errorContraseña.tieneError) {
                    Text(
                        text = viewModel.errorContraseña.mensaje,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            singleLine = true,
            //Transforma el como el usuario ve la contraseña (el dato guardado no cambia)
            visualTransformation = if (viewModel.contraseñaVisible) {
                //Muestra la contraseña como es
                VisualTransformation.None
            } else{
                //Muesta la contraseña con puntos o asteriscos
                PasswordVisualTransformation()
            },
            //Le diste al sistema como se comporta el teclado
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            //Agrega un elemento interactivo al lado derecho del inputs
            trailingIcon = {
                IconButton(onClick = {viewModel.alternarVisibilidad()}) {
                    Icon(
                        imageVector = if (viewModel.contraseñaVisible){
                            //Icono ojo abierto
                            Icons.Filled.Visibility
                        }else{
                            //Icono ojo tachado
                            Icons.Filled.VisibilityOff
                        },
                        contentDescription = "Alternar Visibilidad",
                        tint = Color(0xFF94A3B8)
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedBorderColor = Color(0xFF3B82F6),
                unfocusedBorderColor = Color(0xFF334155),
                focusedLabelColor = Color(0xFF3B82F6),
                unfocusedLabelColor = Color(0xFF94A3B8),
                cursorColor = Color.White,
                errorTextColor = Color.White
            )
        )

        Spacer(modifier = Modifier.height(24.dp))
        //Boton Inciar Sesion
        Button(
            onClick = {
            if (viewModel.iniciarSesion()){
                sessionManager.guardarSesion(viewModel.usuario)
                navController.navigate("racks"){
                    popUpTo("login"){ inclusive = true} //se elimina login del historial para el boton volver
                }
            }
        },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2563EB)
            )
            ) {
            Text(
                text = "Iniciar Sesion",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.weight(1f))
    }//Llave cierre Column Principal
}



// --- PREVIEW PARA ANDROID STUDIO ---
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun VistaLogin() {
    LoginScreen(navController = rememberNavController())
}