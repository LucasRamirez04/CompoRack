package com.duoc.comporack.repository

import com.duoc.comporack.model.Componente
import com.duoc.comporack.model.EstadoRevision
import com.duoc.comporack.model.RegistroAnterior
import com.duoc.comporack.remote.RetrofitInstance
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ResumenRepository {

    suspend fun obtenerRegistroAnterior(rackId: Int, nombreTecnico: String): RegistroAnterior {
        //traer datos del rack
        val rackXano = RetrofitInstance.api.getRackbyId(rackId)
        //traer componentes reales desde xano
        val componentesXano = RetrofitInstance.api.getComponentes()

        //formatear dto de componente a componente de la Screen
        val listaComponentes = componentesXano.mapIndexed { index, comp ->
            Componente(
                id = comp.num_serie,
                numero = String.format("%02d", index + 1),
                nombre = comp.nombre,
                categoria = comp.categoria,
                estadoRevision = EstadoRevision.Validado
            )
        }
        // 4. Formatear el timestamp de ultimaInspeccion de Xano
        val fechaFormateada = formatearFecha(rackXano.ultimaInspeccion)
        val horaFormateada = formatearHora(rackXano.ultimaInspeccion)

        return RegistroAnterior(
            estado = rackXano.estado.uppercase(),
            verificado = rackXano.esOperativo,
            fecha = fechaFormateada,
            horario = horaFormateada,
            tecnico = nombreTecnico.ifBlank { "Técnico asignado" },
            rack = "RACK-${rackXano.id}",
            componentes = listaComponentes
        )
    }

    private fun formatearFecha(timestamp: Long?): String {
        if (timestamp == null) return "Sin fecha registrada"
        val sdf = SimpleDateFormat("dd MMM yyyy", Locale("es", "CL"))
        return sdf.format(Date(timestamp)).uppercase()
    }

    private fun formatearHora(timestamp: Long?): String {
        if (timestamp == null) return "--:--"
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        return sdf.format(Date(timestamp))
    }

}