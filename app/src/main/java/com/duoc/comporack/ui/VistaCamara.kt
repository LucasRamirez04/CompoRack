package com.duoc.comporack.ui

import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner

// Muestra la vista previa de la cámara trasera (usa CameraX)
// Solo debe mostrarse cuando el permiso de cámara ya fue concedido
@Composable
fun VistaCamara(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    // La Activity es la dueña del ciclo de vida de la cámara
    val lifecycleOwner = context as LifecycleOwner

    AndroidView(
        modifier = modifier,
        factory = { contexto ->
            val vistaPrevia = PreviewView(contexto)
            val proveedorFuturo = ProcessCameraProvider.getInstance(contexto)

            proveedorFuturo.addListener({
                val proveedor = proveedorFuturo.get()
                val previa = Preview.Builder().build()
                previa.setSurfaceProvider(vistaPrevia.surfaceProvider)

                // Se libera cualquier uso anterior y se conecta la cámara trasera
                proveedor.unbindAll()
                proveedor.bindToLifecycle(
                    lifecycleOwner,
                    CameraSelector.DEFAULT_BACK_CAMERA,
                    previa
                )
            }, ContextCompat.getMainExecutor(contexto))

            vistaPrevia
        }
    )
}