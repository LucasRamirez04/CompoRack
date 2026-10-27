package com.duoc.comporack.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.duoc.comporack.R
import com.duoc.comporack.model.RackItem
import com.duoc.comporack.viewmodel.RackViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RackScreen(
    navController: NavController,
    viewModel: RackViewModel = viewModel()
) {
    val state = viewModel.uiState

    Scaffold(
        bottomBar = { NavegacionInferiorCompoRack(navController) },
        containerColor = Color(0xFF0A1128)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            SeccionEncabezado()

            Spacer(modifier = Modifier.height(20.dp))

            SeccionBusquedaYFiltro(
                totalRacks = state.racks.size,
                busquedaText = state.busqueda,
                errorBusqueda = state.errorBusqueda,
                onBusquedaChange = { viewModel.actualizarBusqueda(it) },
                onAbrirFiltroModal = { viewModel.alternarModalFiltro(true) },
                ubicacionSeleccionada = state.filtroUbicacion
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(state.racks, key = { it.id }) { rack ->
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn() + slideInVertically(initialOffsetY = { 40 })
                    ) {
                        TarjetaRack(
                            rack = rack,
                            onClick = { navController.navigate("rack_detail/${rack.id}") }
                        )
                    }
                }
            }
        }

        if (state.mostrandoModalFiltro) {
            ModalBottomSheet(
                onDismissRequest = { viewModel.alternarModalFiltro(false) },
                containerColor = Color(0xFF0F172A)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                ) {
                    Text(
                        text = "Filtrar por Ubicación",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    listOf("Todos", "Fila A", "Fila B").forEach { fila ->
                        Button(
                            onClick = { viewModel.aplicarFiltroUbicacion(fila) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (state.filtroUbicacion == fila) Color(0xFF2563EB) else Color(0xFF1E293B)
                            )
                        ) {
                            Text(text = fila, color = Color.White)
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}

@Composable
fun SeccionEncabezado() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.compo_rack_logo),
                contentDescription = "Logo CompoRack",
                modifier = Modifier.size(44.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = "Infraestructura",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "DC MADRID · SALA PRINCIPAL",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color(0xFF64748B)
                )
            }
        }
        Box(
            modifier = Modifier
                .size(40.dp)
                .border(1.dp, Color(0xFF334155), RoundedCornerShape(8.dp))
                .background(Color(0xFF0F172A)),
            contentAlignment = Alignment.Center
        ) {
            Text("JP", color = Color(0xFF3B82F6), fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun SeccionBusquedaYFiltro(
    totalRacks: Int,
    busquedaText: String,
    errorBusqueda: String?,
    onBusquedaChange: (String) -> Unit,
    onAbrirFiltroModal: () -> Unit,
    ubicacionSeleccionada: String
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "INVENTARIO ACTIVO",
            color = Color(0xFF3B82F6),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Racks",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
            Box(
                modifier = Modifier
                    .border(1.dp, Color(0xFF334155), RoundedCornerShape(8.dp))
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Text(
                    text = totalRacks.toString().padStart(2, '0'),
                    color = Color(0xFF94A3B8),
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = busquedaText,
                onValueChange = onBusquedaChange,
                placeholder = { Text("Buscar rack...", color = Color(0xFF64748B)) },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "Buscar", tint = Color(0xFF64748B))
                },
                isError = errorBusqueda != null,
                supportingText = {
                    if (errorBusqueda != null) {
                        Text(text = errorBusqueda, color = MaterialTheme.colorScheme.error)
                    }
                },
                singleLine = true,
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = Color(0xFF3B82F6),
                    unfocusedBorderColor = Color(0xFF334155),
                    focusedContainerColor = Color(0xFF0F172A),
                    unfocusedContainerColor = Color(0xFF0F172A)
                )
            )
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(
                onClick = onAbrirFiltroModal,
                modifier = Modifier
                    .size(52.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFF1E293B))
            ) {
                Icon(
                    imageVector = Icons.Default.FilterList,
                    contentDescription = "Filtrar",
                    tint = Color(0xFF3B82F6)
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Ubicación: $ubicacionSeleccionada",
                color = Color(0xFF94A3B8),
                fontSize = 12.sp
            )
            Text(
                text = "Orden: ID ↑",
                color = Color(0xFF3B82F6),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun TarjetaRack(rack: RackItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A)),
        shape = RoundedCornerShape(16.dp),
        border = CardDefaults.outlinedCardBorder(true).copy(
            brush = androidx.compose.ui.graphics.SolidColor(Color(0xFF1E293B))
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.width(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Storage,
                    contentDescription = "Icono Rack",
                    tint = Color(0xFF475569),
                    modifier = Modifier.size(36.dp)
                )
                Text(
                    text = rack.capacidad,
                    color = Color(0xFF64748B),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = rack.id,
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Surface(
                        color = if (rack.esOperativo) Color(0xFF064E3B) else Color(0xFF451A03),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = rack.estado,
                            color = if (rack.esOperativo) Color(0xFF34D399) else Color(0xFFFBBF24),
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = rack.ubicacion, color = Color(0xFF94A3B8), fontSize = 12.sp)
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "ÚLTIMA INSPECCIÓN  ${rack.ultimaInspeccion}",
                    color = Color(0xFF64748B),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun NavegacionInferiorCompoRack(navController: NavController) {
    NavigationBar(
        containerColor = Color(0xFF0A1128),
        tonalElevation = 8.dp,
        modifier = Modifier
            .border(1.dp, Color(0xFF1E293B), RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
            .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.GridView, contentDescription = "Racks") },
            label = { Text("Racks") },
            selected = true,
            onClick = { },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF3B82F6),
                selectedTextColor = Color(0xFF3B82F6),
                indicatorColor = Color.Transparent,
                unselectedIconColor = Color(0xFF64748B),
                unselectedTextColor = Color(0xFF64748B)
            )
        )
        Box(
            modifier = Modifier
                .padding(top = 8.dp)
                .size(54.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFF2563EB))
                .clickable { navController.navigate("nuevo_registro/escaneo") },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.QrCodeScanner,
                contentDescription = "Escáner",
                tint = Color.White,
                modifier = Modifier.size(28.dp)
            )
        }
        NavigationBarItem(
            icon = { Icon(Icons.Default.PersonOutline, contentDescription = "Perfil") },
            label = { Text("Perfil") },
            selected = false,
            onClick = { navController.navigate("perfil") },
            colors = NavigationBarItemDefaults.colors(
                unselectedIconColor = Color(0xFF64748B),
                unselectedTextColor = Color(0xFF64748B)
            )
        )
    }
}