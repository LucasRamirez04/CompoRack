package com.duoc.comporack.data

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat

// Objeto unico no se crean instancias
object NotificacionHelper {

    // Identificador del canal, siempre el mismo texto para que Android agrupe las notificaciones
    private const val ID_CANAL = "canal_registros_alta"

    // Numero que identifica la notificacion. Evita acumular si se envia otra con el mismo numero
    private const val ID_NOTIFICACION_REGISTRO = 1

    // Comprueba si la app puede mostrar notificaciones
    // Antes de Android 13 (TIRAMISU) las notificaciones no pedian permiso, por eso devuelve true
    fun tienePermiso(context: Context): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return true
        return ContextCompat.checkSelfPermission(
            context, Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
    }

    // Un canal es una categoria de notificaciones que puede silenciar o desactivar
    private fun crearCanal(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // IMPORTANCE_DEFAULT suena y aparece en la barra de estado sin interrumpir la pantalla
            val canal = NotificationChannel(
                ID_CANAL,
                "Registros de racks",
                NotificationManager.IMPORTANCE_HIGH
            )
            canal.description = "Avisos cuando se guarda un registro"

            // getSystemService entrega el administrador de notificaciones del sistema
            // Si el canal ya existe volver a crearlo no hace nada
            val administrador = context.getSystemService(NotificationManager::class.java)
            administrador.createNotificationChannel(canal)
        }
    }

    // @SuppressLint indica al compilador que ignore la advertencia de permiso faltante,
    // porque el permiso ya se comprueba con tienePermiso() en la primera línea de la función
    @SuppressLint("MissingPermission")
    fun mostrarRegistroGuardado(context: Context, rack: String, cantidadComponentes: Int) {
        // Sin permiso no se muestra nada (en ese caso la funcion termina aqui)
        if (!tienePermiso(context)) return

        crearCanal(context)

        // Builder va armando la notificacion paso a paso
        val notificacion = NotificationCompat.Builder(context, ID_CANAL)
            .setSmallIcon(android.R.drawable.ic_dialog_info) // Ícono pequeño obligatorio (ícono del sistema)
            .setContentTitle("Registro guardado")
            .setContentText("$rack: $cantidadComponentes componentes registrados")
            .setPriority(NotificationCompat.PRIORITY_HIGH) // Prioridad alta en Android 7 o inferior
            .setAutoCancel(true) // Desaparece al tocarla
            .build()

        // notify() muestra la notificación en el teléfono
        NotificationManagerCompat.from(context).notify(ID_NOTIFICACION_REGISTRO, notificacion)
    }
}