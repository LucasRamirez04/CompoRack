package com.duoc.comporack.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.duoc.comporack.model.Racks
import com.duoc.comporack.viewmodel.DetalleRackViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun DetalleRackScreen(
    navController: NavController,
    rackId: Int,
    viewModel: DetalleRackViewModel = viewModel()
) {
    val state = viewModel.uiState

    // Se ejecuta automáticamente al entrar a la pantalla con el ID del rack tocado
    LaunchedEffect(rackId) {
        viewModel.cargarRack(rackId)
    }

    Scaffold(
        containerColor = Color(0xFF0A1128)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            TopBarDetalle(
                navController = navController,
                subtitulo = state.rack?.ubicacion?.uppercase() ?: "CARGANDO..."
            )
            Spacer(modifier = Modifier.height(24.dp))

            when {
                state.cargando -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = Color(0xFF3B82F6))
                    }
                }

                state.error != null -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = state.error, color = Color(0xFFEF4444), fontSize = 14.sp)
                            Spacer(modifier = Modifier.height(12.dp))
                            Button(
                                onClick = { viewModel.cargarRack(rackId) },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2563EB))
                            ) {
                                Text("Reintentar", color = Color.White)
                            }
                        }
                    }
                }

                state.rack != null -> {
                    TarjetaRackSeleccionado(rack = state.rack)
                    Spacer(modifier = Modifier.height(16.dp))
                    BotonesAccion(navController = navController)
                    Spacer(modifier = Modifier.height(24.dp))
                    SeccionEstadoActual(rack = state.rack)
                }
            }
        }
    }
}

@Composable
fun TopBarDetalle(navController: NavController, subtitulo: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF0F172A))
                .border(1.dp, Color(0xFF1E293B), RoundedCornerShape(12.dp))
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = "Volver",
                tint = Color.White,
                modifier = Modifier.size(18.dp)
            )
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Detalle del Rack",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = subtitulo,
                color = Color(0xFF64748B),
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
        }
        Spacer(modifier = Modifier.size(44.dp))
    }
}

@Composable
fun TarjetaRackSeleccionado(rack: Racks) {
    val colorEstado = if (rack.esOperativo) Color(0xFF10B981) else Color(0xFFFBBF24)

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A)),
        shape = RoundedCornerShape(16.dp),
        border = CardDefaults.outlinedCardBorder(true).copy(
            brush = androidx.compose.ui.graphics.SolidColor(Color(0xFF1E293B))
        )
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        "RACK SELECCIONADO",
                        color = Color(0xFF3B82F6),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "RACK-${rack.id}",
                        color = Color.White,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(rack.ubicacion, color = Color(0xFF94A3B8), fontSize = 12.sp)
                }
                Icon(
                    Icons.Default.Storage,
                    contentDescription = "Rack Icon",
                    tint = Color(0xFF334155),
                    modifier = Modifier.size(64.dp)
                )
            }
            HorizontalDivider(color = Color(0xFF1E293B), thickness = 1.dp)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(colorEstado)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        rack.estado.uppercase(),
                        color = colorEstado,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                }
                Text(
                    "CAPACIDAD ${rack.capacidad}U",
                    color = Color(0xFF64748B),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
            }
        }
    }
}

@Composable
fun BotonesAccion(navController: NavController) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Button(
            onClick = { navController.navigate("nuevo_registro") },
            modifier = Modifier
                .weight(1f)
                .height(80.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2563EB)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.QrCodeScanner, contentDescription = "Escanear", tint = Color.White)
                    Icon(Icons.Default.ArrowForward, contentDescription = "Ir", tint = Color.White, modifier = Modifier.size(16.dp))
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text("Nuevo Registro", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
            }
        }

        Card(
            modifier = Modifier
                .weight(1f)
                .height(80.dp)
                .clickable { navController.navigate("resumen") },
            colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A)),
            shape = RoundedCornerShape(16.dp),
            border = CardDefaults.outlinedCardBorder(true).copy(
                brush = androidx.compose.ui.graphics.SolidColor(Color(0xFF1E293B))
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(Icons.Default.GridView, contentDescription = "Resumen", tint = Color.White, modifier = Modifier.size(24.dp))
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Resumen Registro Anterior",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 14.sp
                )
            }
        }
    }
}

@Composable
fun SeccionEstadoActual(rack: Racks) {
    Column {
        Text(
            "ESTADO ACTUAL",
            color = Color(0xFF3B82F6),
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Resumen de capacidad", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text(
                formatearFechaDetalle(rack.ultimaInspeccion),
                color = Color(0xFF64748B),
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            TarjetaInformativa(
                modifier = Modifier.weight(1f),
                icono = Icons.Default.GridView,
                valorGrande = rack.capacidad.toString(),
                valorSufijo = "U",
                descripcion = "Capacidad total",
                badge = "ID #${rack.id}",
                iconoFondoColor = Color(0xFF1E293B),
                iconoIconoColor = Color(0xFF3B82F6),
                badgeColor = Color(0xFF3B82F6)
            )
            TarjetaInformativa(
                modifier = Modifier.weight(1f),
                icono = Icons.Default.Check,
                valorGrande = if (rack.esOperativo) "OK" else "REV",
                valorSufijo = "",
                descripcion = "Estado operativo",
                badge = rack.estado.uppercase(),
                iconoFondoColor = if (rack.esOperativo) Color(0xFF064E3B) else Color(0xFF451A03),
                iconoIconoColor = if (rack.esOperativo) Color(0xFF34D399) else Color(0xFFFBBF24),
                badgeColor = if (rack.esOperativo) Color(0xFF34D399) else Color(0xFFFBBF24)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A)),
            shape = RoundedCornerShape(16.dp),
            border = CardDefaults.outlinedCardBorder(true).copy(
                brush = androidx.compose.ui.graphics.SolidColor(Color(0xFF1E293B))
            )
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Ubicación asignada", color = Color(0xFF94A3B8), fontSize = 12.sp)
                    Text(rack.ubicacion, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(12.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Última inspección", color = Color(0xFF94A3B8), fontSize = 12.sp)
                    Text(
                        formatearFechaDetalle(rack.ultimaInspeccion),
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun TarjetaInformativa(
    modifier: Modifier,
    icono: androidx.compose.ui.graphics.vector.ImageVector,
    valorGrande: String,
    valorSufijo: String,
    descripcion: String,
    badge: String,
    iconoFondoColor: Color,
    iconoIconoColor: Color,
    badgeColor: Color
) {
    Card(
        modifier = modifier.height(110.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A)),
        shape = RoundedCornerShape(16.dp),
        border = CardDefaults.outlinedCardBorder(true).copy(
            brush = androidx.compose.ui.graphics.SolidColor(Color(0xFF1E293B))
        )
    ) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.SpaceBetween) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Top) {
                Box(modifier = Modifier.size(32.dp).clip(RoundedCornerShape(8.dp)).background(iconoFondoColor), contentAlignment = Alignment.Center) {
                    Icon(icono, contentDescription = null, tint = iconoIconoColor, modifier = Modifier.size(18.dp))
                }
                Text(badge, color = badgeColor, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            }
            Column {
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(valorGrande, color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    if (valorSufijo.isNotEmpty()) {
                        Text(valorSufijo, color = Color(0xFF94A3B8), fontSize = 14.sp, modifier = Modifier.padding(bottom = 2.dp, start = 2.dp))
                    }
                }
                Text(descripcion, color = Color(0xFF64748B), fontSize = 10.sp)
            }
        }
    }
}

private fun formatearFechaDetalle(timestamp: Long?): String {
    if (timestamp == null || timestamp == 0L) return "Sin registro"
    return try {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        sdf.format(Date(timestamp))
    } catch (e: Exception) {
        "Sin registro"
    }
}