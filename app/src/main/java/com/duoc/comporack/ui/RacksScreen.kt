package com.duoc.comporack.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun RacksScreen(
    navController: NavController
){
    Column(
        modifier = Modifier.fillMaxSize().background(Color(0xFF0A1128)).padding(24.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "RACKS",
            color = Color.White
        )
    }

}

// --- PREVIEW PARA ANDROID STUDIO ---
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun VistaRacks() {
    RacksScreen(navController = rememberNavController())
}