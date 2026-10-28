package com.duoc.comporack.ui

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.duoc.comporack.viewmodel.NuevoRegistroViewModel

@Composable
fun NuevoRegistroScreen(
    navController: NavController
) {
    val viewModel: NuevoRegistroViewModel = viewModel()
    val context = LocalContext.current

    // Lanzador que muestra el cuadro de permiso de cámara y guarda la respuesta
    val lanzadorPermiso = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { concedido ->
        viewModel.actualizarPermisoCamara(concedido)
    }

    // Al abrir la vista si ya hay permiso lo usa, si no lo solicita
    LaunchedEffect(Unit) {
        val yaConcedido = ContextCompat.checkSelfPermission(
            context, Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED

        if (yaConcedido) {
            viewModel.actualizarPermisoCamara(true)
        } else {
            lanzadorPermiso.launch(Manifest.permission.CAMERA)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0A1128))
            .statusBarsPadding()
    ) {
        if (viewModel.paso == 1) {
            EncabezadoRegistro(
                subtitulo = "VALIDACION DE EXISTENCIA",
                onVolver = { navController.popBackStack() }
            )
            IndicadorPaso(pasoActual = 1, titulo = "Confirmar ubicación física")
            PasoPresencia(
                permisoCamara = viewModel.permisoCamara,
                mensajeError = viewModel.errorLectura.mensaje,
                hayError = viewModel.errorLectura.tieneError,
                onPedirPermiso = { lanzadorPermiso.launch(Manifest.permission.CAMERA) },
                onSimularQr = { viewModel.simularLecturaQr() }
            )
        } else {
            EncabezadoRegistro(
                subtitulo = "${viewModel.rack} · UBICACIÓN VALIDADA",
                onVolver = { navController.popBackStack() }
            )
            IndicadorPaso(pasoActual = 2, titulo = "Escanear componente")
            PasoComponente(
                permisoCamara = viewModel.permisoCamara,
                cantidadConfirmados = viewModel.cantidadConfirmados,
                totalEsperado = viewModel.totalEsperado,
                mensajeError = viewModel.errorLectura.mensaje,
                hayError = viewModel.errorLectura.tieneError,
                onPedirPermiso = { lanzadorPermiso.launch(Manifest.permission.CAMERA) },
                onLeerCodigo = { viewModel.simularLecturaCodigo() },
                onIngresarManual = { viewModel.abrirManual() },
                onFinalizar = { viewModel.abrirFinalizar() }
            )
        }
    }

    // Modal 1 resultado del escaneo exitoso
    val componente = viewModel.componenteDetectado
    if (componente != null) {
        AlertDialog(
            onDismissRequest = { viewModel.descartarComponente() },
            containerColor = Color(0xFF111C3A),
            title = {
                Text(
                    text = "✔ COMPONENTE DETECTADO",
                    color = Color(0xFF22C55E),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column {
                    Text(
                        text = componente.categoria,
                        color = Color(0xFF3B82F6),
                        fontSize = 12.sp,
                        letterSpacing = 1.2.sp
                    )
                    Text(
                        text = componente.nombre,
                        color = Color.White,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "S/N: ${componente.serie}",
                        color = Color(0xFF94A3B8),
                        fontSize = 13.sp
                    )
                    Text(
                        text = "${componente.coincidencia}% de coincidencia",
                        color = Color(0xFF22C55E),
                        fontSize = 13.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = { viewModel.confirmarComponente() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2563EB))
                ) {
                    Text("Confirmar Componente")
                }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.descartarComponente() }) {
                    Text("Descartar", color = Color(0xFF94A3B8))
                }
            }
        )
    }

    // Modal 2 formulario de ingreso manual del código
    if (viewModel.mostrarManual) {
        AlertDialog(
            onDismissRequest = { viewModel.cerrarManual() },
            containerColor = Color(0xFF111C3A),
            title = {
                Text(text = "Ingresar código manualmente", color = Color.White)
            },
            text = {
                OutlinedTextField(
                    value = viewModel.codigoManual,
                    onValueChange = { viewModel.actualizarCodigoManual(it) },
                    label = { Text("Código del componente") },
                    isError = viewModel.errorManual.tieneError,
                    supportingText = {
                        if (viewModel.errorManual.tieneError) {
                            Text(
                                text = viewModel.errorManual.mensaje,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = coloresCampo()
                )
            },
            confirmButton = {
                Button(
                    onClick = { viewModel.enviarCodigoManual() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2563EB))
                ) {
                    Text("Validar")
                }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.cerrarManual() }) {
                    Text("Cancelar", color = Color(0xFF94A3B8))
                }
            }
        )
    }

    // Modal 3 confirmación antes de finalizar el registro
    if (viewModel.mostrarFinalizar) {
        AlertDialog(
            onDismissRequest = { viewModel.cerrarFinalizar() },
            containerColor = Color(0xFF111C3A),
            title = {
                Text(text = "Finalizar registro", color = Color.White)
            },
            text = {
                Text(
                    text = "Has confirmado ${viewModel.cantidadConfirmados} de ${viewModel.totalEsperado} componentes. ¿Deseas finalizar el registro?",
                    color = Color(0xFF94A3B8)
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.cerrarFinalizar()
                        navController.popBackStack()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2563EB))
                ) {
                    Text("Finalizar")
                }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.cerrarFinalizar() }) {
                    Text("Seguir escaneando", color = Color(0xFF94A3B8))
                }
            }
        )
    }
}

// Contenido del paso 1 camara + tarjeta inferior con el boton de lectura
@Composable
private fun ColumnScope.PasoPresencia(
    permisoCamara: Boolean,
    mensajeError: String,
    hayError: Boolean,
    onPedirPermiso: () -> Unit,
    onSimularQr: () -> Unit
) {
    InstruccionCamara(texto = "Escanea el QR físico del Rack para bloquear la ubicación")

    MarcoCamara(
        modifier = Modifier.weight(1f),
        permisoCamara = permisoCamara,
        onPedirPermiso = onPedirPermiso
    )

    // Tarjeta inferior
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF111C3A))
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.VerifiedUser,
                    contentDescription = "Validación",
                    tint = Color(0xFF3B82F6),
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "Validación de presencia requerida",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "El registro solo puede iniciarse junto al rack físico.",
                        color = Color(0xFF64748B),
                        fontSize = 12.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onSimularQr,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2563EB))
            ) {
                Text(
                    text = "Simular lectura QR de RACK-A01",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            if (hayError) {
                Text(
                    text = mensajeError,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}

// Contenido del paso 2 camara + tarjeta inferior con lectura, ingreso manual, contador y finalizar
@Composable
private fun ColumnScope.PasoComponente(
    permisoCamara: Boolean,
    cantidadConfirmados: Int,
    totalEsperado: Int,
    mensajeError: String,
    hayError: Boolean,
    onPedirPermiso: () -> Unit,
    onLeerCodigo: () -> Unit,
    onIngresarManual: () -> Unit,
    onFinalizar: () -> Unit
) {
    InstruccionCamara(texto = "Centra el código de barras dentro del marco")

    MarcoCamara(
        modifier = Modifier.weight(1f),
        permisoCamara = permisoCamara,
        onPedirPermiso = onPedirPermiso
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF111C3A))
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Button(
                onClick = onLeerCodigo,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2563EB))
            ) {
                Text(
                    text = "Simular lectura de código de barras",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            if (hayError) {
                Text(
                    text = mensajeError,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            // Enlace para el ingreso manual
            TextButton(
                onClick = onIngresarManual,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = "¿Fallo de lectura? Ingresar código manualmente",
                    color = Color(0xFF3B82F6),
                    fontSize = 13.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Contador de componentes + boton finalizar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "COMPONENTES AGREGADOS",
                        color = Color(0xFF64748B),
                        fontSize = 11.sp,
                        letterSpacing = 1.2.sp
                    )
                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(
                            text = "$cantidadConfirmados",
                            color = Color.White,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = " / $totalEsperado esperados",
                            color = Color(0xFF64748B),
                            fontSize = 12.sp,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                    }
                }
                Button(
                    onClick = onFinalizar,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E293B))
                ) {
                    Text(
                        text = "Finalizar Registro",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

// Texto con icono que explica que hay que escanear en ambos pasos
@Composable
private fun InstruccionCamara(texto: String) {
    Row(
        modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Filled.QrCodeScanner,
            contentDescription = "Escáner",
            tint = Color(0xFF3B82F6)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = texto,
            color = Color(0xFF94A3B8),
            fontSize = 14.sp
        )
    }
}

// Marco azul con la camara o el aviso si no hay permiso
@Composable
private fun MarcoCamara(
    modifier: Modifier,
    permisoCamara: Boolean,
    onPedirPermiso: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .clip(RoundedCornerShape(16.dp))
            .border(2.dp, Color(0xFF3B82F6), RoundedCornerShape(16.dp)),
        contentAlignment = Alignment.Center
    ) {
        if (permisoCamara) {
            VistaCamara(modifier = Modifier.fillMaxSize())
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(24.dp)
            ) {
                Text(
                    text = "Se necesita permiso de cámara para escanear",
                    color = Color(0xFF94A3B8),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = onPedirPermiso,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2563EB))
                ) {
                    Text("Permitir cámara")
                }
            }
        }
    }
}

// Encabezado con flecha de volver y titulo para los dos pasos
@Composable
private fun EncabezadoRegistro(subtitulo: String, onVolver: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onVolver) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Volver",
                tint = Color.White
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = "Nuevo Registro",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = subtitulo,
                color = Color(0xFF64748B),
                fontSize = 12.sp,
                letterSpacing = 1.2.sp
            )
        }
    }
}

// Indicador "paso 1 de 2" con los dos circulos
@Composable
private fun IndicadorPaso(pasoActual: Int, titulo: String) {
    Row(
        modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CirculoPaso(numero = 1, activo = pasoActual >= 1)
        Box(
            modifier = Modifier
                .width(24.dp)
                .height(2.dp)
                .background(Color(0xFF334155))
        )
        CirculoPaso(numero = 2, activo = pasoActual >= 2)
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = "PASO $pasoActual DE 2",
                color = Color(0xFF64748B),
                fontSize = 11.sp,
                letterSpacing = 1.2.sp
            )
            Text(
                text = titulo,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun CirculoPaso(numero: Int, activo: Boolean) {
    Box(
        modifier = Modifier
            .size(28.dp)
            .clip(CircleShape)
            .background(if (activo) Color(0xFF2563EB) else Color(0xFF1E293B)),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "$numero", color = Color.White, fontSize = 12.sp)
    }
}

// --- PREVIEW PARA ANDROID STUDIO ---
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun VistaNuevoRegistro() {
    NuevoRegistroScreen(navController = rememberNavController())
}