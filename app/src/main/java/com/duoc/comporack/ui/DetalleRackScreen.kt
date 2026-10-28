package com.duoc.comporack.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun DetalleRackScreen(navController: NavController) {
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
            TopBarDetalle(navController)
            Spacer(modifier = Modifier.height(24.dp))
            TarjetaRackSeleccionado()
            Spacer(modifier = Modifier.height(16.dp))
            BotonesAccion(navController)
            Spacer(modifier = Modifier.height(24.dp))
            SeccionEstadoActual()
        }
    }
}

@Composable
fun TopBarDetalle(navController: NavController) {
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
            Icon(Icons.Default.ArrowBackIosNew, contentDescription = "Volver", tint = Color.White, modifier = Modifier.size(18.dp))
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Detalle del Rack", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text("SALA PRINCIPAL", color = Color(0xFF64748B), fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
        }

        IconButton(
            onClick = { },
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF0F172A))
                .border(1.dp, Color(0xFF1E293B), RoundedCornerShape(12.dp))
        ) {
            Icon(Icons.Default.MoreHoriz, contentDescription = "Opciones", tint = Color.White)
        }
    }
}

@Composable
fun TarjetaRackSeleccionado() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A)),
        shape = RoundedCornerShape(16.dp),
        border = CardDefaults.outlinedCardBorder(true).copy(brush = androidx.compose.ui.graphics.SolidColor(Color(0xFF1E293B)))
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth().padding(20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("RACK SELECCIONADO", color = Color(0xFF3B82F6), fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("RACK-A01", color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Data Center Sala Principal", color = Color(0xFF94A3B8), fontSize = 12.sp)
                }
                Icon(Icons.Default.Storage, contentDescription = "Rack Icon", tint = Color(0xFF334155), modifier = Modifier.size(64.dp))
            }
            HorizontalDivider(color = Color(0xFF1E293B), thickness = 1.dp)
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(8.dp).clip(androidx.compose.foundation.shape.CircleShape).background(Color(0xFF10B981)))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("OPERATIVO", color = Color(0xFF10B981), fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                }
                Text("FILA A - POS. 01", color = Color(0xFF64748B), fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
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
            modifier = Modifier.weight(1f).height(80.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2563EB)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.QrCodeScanner, contentDescription = "Escanear", tint = Color.White)
                    Icon(Icons.Default.ArrowForward, contentDescription = "Ir", tint = Color.White, modifier = Modifier.size(16.dp))
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text("Nuevo Registro", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
            }
        }

        Card(
            modifier = Modifier.weight(1f).height(80.dp).clickable { navController.navigate("resumen") },
            colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A)),
            shape = RoundedCornerShape(16.dp),
            border = CardDefaults.outlinedCardBorder(true).copy(brush = androidx.compose.ui.graphics.SolidColor(Color(0xFF1E293B)))
        ) {
            Column(
                modifier = Modifier.fillMaxSize().padding(12.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(Icons.Default.GridView, contentDescription = "Resumen", tint = Color.White, modifier = Modifier.size(24.dp))
                Spacer(modifier = Modifier.height(8.dp))
                Text("Resumen Registro Anterior", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 11.sp, textAlign = TextAlign.Center, lineHeight = 14.sp)
            }
        }
    }
}

@Composable
fun SeccionEstadoActual() {
    Column {
        Text("ESTADO ACTUAL", color = Color(0xFF3B82F6), fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text("Resumen de capacidad", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text("03 SEP", color = Color(0xFF64748B), fontSize = 10.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            TarjetaInformativa(
                modifier = Modifier.weight(1f), icono = Icons.Default.GridView, valorGrande = "39", valorSufijo = "U",
                descripcion = "Unidades instaladas", badge = "93%", iconoFondoColor = Color(0xFF1E293B),
                iconoIconoColor = Color(0xFF3B82F6), badgeColor = Color(0xFF10B981)
            )
            TarjetaInformativa(
                modifier = Modifier.weight(1f), icono = Icons.Default.Check, valorGrande = "24", valorSufijo = "",
                descripcion = "Componentes detectados", badge = "+2", iconoFondoColor = Color(0xFF064E3B),
                iconoIconoColor = Color(0xFF34D399), badgeColor = Color(0xFF34D399)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A)),
            shape = RoundedCornerShape(16.dp),
            border = CardDefaults.outlinedCardBorder(true).copy(brush = androidx.compose.ui.graphics.SolidColor(Color(0xFF1E293B)))
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Ocupación del rack", color = Color(0xFF94A3B8), fontSize = 12.sp)
                    Text("39 / 42U", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(12.dp))
                LinearProgressIndicator(
                    progress = { 39f / 42f },
                    modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp)),
                    color = Color(0xFF3B82F6),
                    trackColor = Color(0xFF1E293B)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text("3U disponibles", color = Color(0xFF64748B), fontSize = 10.sp)
            }
        }
    }
}

@Composable
fun TarjetaInformativa(
    modifier: Modifier, icono: androidx.compose.ui.graphics.vector.ImageVector, valorGrande: String,
    valorSufijo: String, descripcion: String, badge: String, iconoFondoColor: Color, iconoIconoColor: Color, badgeColor: Color
) {
    Card(
        modifier = modifier.height(110.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A)),
        shape = RoundedCornerShape(16.dp),
        border = CardDefaults.outlinedCardBorder(true).copy(brush = androidx.compose.ui.graphics.SolidColor(Color(0xFF1E293B)))
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
                    if (valorSufijo.isNotEmpty()) Text(valorSufijo, color = Color(0xFF94A3B8), fontSize = 14.sp, modifier = Modifier.padding(bottom = 2.dp, start = 2.dp))
                }
                Text(descripcion, color = Color(0xFF64748B), fontSize = 10.sp)
            }
        }
    }
}