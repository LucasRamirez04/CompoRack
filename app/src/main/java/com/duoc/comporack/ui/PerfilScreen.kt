package com.duoc.comporack.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.duoc.comporack.R
import com.duoc.comporack.data.SessionManager
import com.duoc.comporack.viewmodel.PerfilViewModel

@Composable
fun PerfilScreen(
    navController: NavController
) {
    // Instanciamos el PerfilViewModel y el SessionManager (igual que en el Login)
    val viewModel: PerfilViewModel = viewModel()
    val context = LocalContext.current
    val sessionManager: SessionManager = remember { SessionManager(context) }
    val perfil = viewModel.perfil

    // Al abrir la vista, carga el usuario que se guardó al iniciar sesión
    LaunchedEffect(Unit) {
        viewModel.cargarPerfil(sessionManager.obtenerUsuario())
    }

    Scaffold(
        containerColor = Color(0xFF0A1128),
        bottomBar = { BarraNavegacion(navController = navController, rutaActual = "perfil") }
    ) { paddingInterno ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingInterno)
                .padding(24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Encabezado: logo + titulo
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.compo_rack_logo),
                    contentDescription = "Logo CompoRack",
                    modifier = Modifier.size(56.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "Mi Perfil",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "CUENTA DE INSPECTOR",
                        style = MaterialTheme.typography.titleSmall,
                        color = Color(0xFF64748B),
                        letterSpacing = 1.2.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Tarjeta con avatar, usuario y rol
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF111C3A))
            ) {
                Row(
                    modifier = Modifier.padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = "Avatar",
                        tint = Color(0xFF3B82F6),
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = "INSPECTOR CERTIFICADO",
                            color = Color(0xFF3B82F6),
                            fontSize = 12.sp,
                            letterSpacing = 1.2.sp
                        )
                        Text(
                            text = perfil.usuario,
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = perfil.rol,
                            color = Color(0xFF94A3B8),
                            fontSize = 13.sp
                        )
                        if (perfil.cuentaActiva) {
                            Text(
                                text = "● CUENTA ACTIVA",
                                color = Color(0xFF22C55E),
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "INFORMACIÓN PROFESIONAL",
                color = Color(0xFF64748B),
                fontSize = 12.sp,
                letterSpacing = 1.2.sp
            )
            Spacer(modifier = Modifier.height(12.dp))

            // Campos de solo lectura
            CampoSoloLectura(etiqueta = "Usuario", valor = perfil.usuario)
            Spacer(modifier = Modifier.height(12.dp))
            CampoSoloLectura(etiqueta = "Rol", valor = perfil.rol)
            Spacer(modifier = Modifier.height(12.dp))
            CampoSoloLectura(etiqueta = "ID Empleado", valor = perfil.idEmpleado)

            Spacer(modifier = Modifier.height(24.dp))

            // Boton que abre el modal
            Button(
                onClick = { viewModel.abrirModal() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E293B))
            ) {
                Text(
                    text = "Hacer petición para cambiar datos",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            // Animacion al enviar la peticion
            AnimatedVisibility(visible = viewModel.peticionEnviada) {
                Text(
                    text = "Petición enviada. Será revisada por un administrador.",
                    color = Color(0xFF22C55E),
                    modifier = Modifier.padding(top = 12.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Cierra la sesion guardada y vuelve al login
            OutlinedButton(
                onClick = {
                    sessionManager.cerrarSesion()
                    navController.navigate("login") {
                        popUpTo("racks") { inclusive = true }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(
                    text = "Cerrar sesión",
                    color = Color(0xFFEF4444),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }

    // Modal con el formulario de peticion
    if (viewModel.mostrarModal) {
        AlertDialog(
            onDismissRequest = { viewModel.cerrarModal() },
            containerColor = Color(0xFF111C3A),
            title = {
                Text(text = "Solicitar cambio de datos", color = Color.White)
            },
            text = {
                OutlinedTextField(
                    value = viewModel.peticion,
                    onValueChange = { viewModel.actualizarPeticion(it) },
                    label = { Text("¿Qué dato quieres cambiar?") },
                    isError = viewModel.errorPeticion.tieneError,
                    supportingText = {
                        if (viewModel.errorPeticion.tieneError) {
                            Text(
                                text = viewModel.errorPeticion.mensaje,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = coloresCampo()
                )
            },
            confirmButton = {
                Button(
                    onClick = { viewModel.enviarPeticion() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2563EB))
                ) {
                    Text("Enviar")
                }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.cerrarModal() }) {
                    Text("Cancelar", color = Color(0xFF94A3B8))
                }
            }
        )
    }
}

// Campo de texto que solo muestra el dato (con candado)
@Composable
private fun CampoSoloLectura(etiqueta: String, valor: String) {
    OutlinedTextField(
        value = valor,
        onValueChange = {},
        readOnly = true,
        label = { Text(etiqueta) },
        singleLine = true,
        trailingIcon = {
            Icon(
                imageVector = Icons.Filled.Lock,
                contentDescription = "Solo lectura",
                tint = Color(0xFF94A3B8)
            )
        },
        modifier = Modifier.fillMaxWidth(),
        colors = coloresCampo()
    )
}

// Paleta colores
@Composable
fun coloresCampo() = OutlinedTextFieldDefaults.colors(
    focusedTextColor = Color.White,
    unfocusedTextColor = Color.White,
    focusedBorderColor = Color(0xFF3B82F6),
    unfocusedBorderColor = Color(0xFF334155),
    focusedLabelColor = Color(0xFF3B82F6),
    unfocusedLabelColor = Color(0xFF94A3B8),
    cursorColor = Color.White,
    errorTextColor = Color.White
)

// --- PREVIEW PARA ANDROID STUDIO ---
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun VistaPerfil() {
    PerfilScreen(navController = rememberNavController())
}