package com.duoc.comporack.repository

import com.duoc.comporack.model.RackItem

class RackRepository {

    fun obtenerRacks(): List<RackItem> {
        return listOf(
            RackItem(
                id = "RACK-A01",
                estado = "OPERATIVO",
                ubicacion = "Sala Principal - Fila A",
                ultimaInspeccion = "03 SEP 2026",
                capacidad = "39U",
                esOperativo = true
            ),
            RackItem(
                id = "RACK-A02",
                estado = "REVISIÓN",
                ubicacion = "Sala Principal - Fila A",
                ultimaInspeccion = "01 SEP 2026",
                capacidad = "36U",
                esOperativo = false
            ),
            RackItem(
                id = "RACK-B04",
                estado = "OPERATIVO",
                ubicacion = "Sala Principal - Fila B",
                ultimaInspeccion = "28 AGO 2026",
                capacidad = "41U",
                esOperativo = true
            ),
            RackItem(
                id = "RACK-B05",
                estado = "OPERATIVO",
                ubicacion = "Sala Principal - Fila B",
                ultimaInspeccion = "24 AGO 2026",
                capacidad = "34U",
                esOperativo = true
            )
        )
    }
}