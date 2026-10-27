package com.duoc.comporack.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.duoc.comporack.model.EstadoRevision
import com.duoc.comporack.viewmodel.ResumenViewModel

@Composable
fun ResumenScreen(
    navController: NavController,
    viewModel: ResumenViewModel = viewModel()
) {
    val registro = viewModel.registro

    // Fondo oscuro uniforme de toda la app
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0A1128))
            .padding(24.dp)
    ) {
        // --- CABECERA SUPERIOR (Boton Volver + Titulo) ---
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Volver",
                    tint = Color.White
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Resumen de Inspección",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // ==========================================
        // CARD 1: DATOS DEL RACK
        // ==========================================
        Text(
            text = "DATOS DEL RACK",
            color = Color(0xFF3B82F6),
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp
        )
        Spacer(modifier = Modifier.height(8.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Rack: ${registro.rack}",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Estado: ${registro.estado}",
                    color = Color(0xFF10B981),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Fecha: ${registro.fecha} (${registro.horario})",
                    color = Color(0xFF94A3B8),
                    fontSize = 13.sp
                )
                Text(
                    text = "Técnico a cargo: ${registro.tecnico}",
                    color = Color(0xFF94A3B8),
                    fontSize = 13.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // ==========================================
        // CARD 2: CONTENEDOR COMPLETO DE COMPONENTES
        // ==========================================
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "COMPONENTES IDENTIFICADOS",
                color = Color(0xFF3B82F6),
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp
            )
            Text(
                text = "${registro.totalComponente} registrados",
                color = Color(0xFF94A3B8),
                fontSize = 12.sp
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Una sola Card que envuelve la lista
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B))
        ) {
            // LazyColumn contenido dentro de la Card para hacer scroll interno
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                items(registro.componentes) { componente ->
                    val colorTexto = when (componente.estadoRevision) {
                        is EstadoRevision.Validado -> Color(0xFF10B981)
                        is EstadoRevision.ConFalla -> Color(0xFFEF4444)
                        is EstadoRevision.Pendiente -> Color(0xFFF59E0B)
                    }

                    // Fila de cada componente
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = componente.nombre,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "${componente.categoria} • ID: ${componente.id}",
                                color = Color(0xFF94A3B8),
                                fontSize = 11.sp
                            )
                        }

                        Text(
                            text = componente.estadoRevision.estado,
                            color = colorTexto,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }

                    // Linea divisoria sutil entre filas
                    HorizontalDivider(color = Color(0xFF334155), thickness = 0.5.dp)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --- BOTON VOLVER ---
        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2563EB)
            )
        ) {
            Text(
                text = "Volver a Racks",
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

// --- PREVIEW ---
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun VistaResumen() {
    ResumenScreen(navController = rememberNavController())
}