package com.duoc.comporack.repository

import com.duoc.comporack.model.Componente
import com.duoc.comporack.model.EstadoRevision
import com.duoc.comporack.model.RegistroAnterior

class ResumenRepository {

    fun obtenerRegistroAnterior(): RegistroAnterior {
        val listaComponentes = listOf(
            Componente(
                id = "FCW2536L0AB",
                numero = "01",
                nombre = "Cisco Catalyst 9300",
                categoria = "SWITCH DE RED",
                estadoRevision = EstadoRevision.Validado
            ),
            Componente(
                id = "PP24-A01-038",
                numero = "02",
                nombre = "Patch Panel 24p",
                categoria = "CONECTIVIDAD",
                estadoRevision = EstadoRevision.Validado
            ),
            Componente(
                id = "8DJ4XM3",
                numero = "03",
                nombre = "Dell PowerEdge R750",
                categoria = "SERVIDOR",
                estadoRevision = EstadoRevision.Validado
            ),
            Componente(
                id = "ETN9PX-9281",
                numero = "04",
                nombre = "UPS Eaton 9PX",
                categoria = "ENERGÍA",
                estadoRevision = EstadoRevision.Validado
            ),
            Componente(
                id = "SRV-STR-0102",
                numero = "05",
                nombre = "Synology RackStation RS2423+",
                categoria = "ALMACENAMIENTO",
                estadoRevision = EstadoRevision.Pendiente
            ),
            Componente(
                id = "PDU-APC-8941",
                numero = "06",
                nombre = "APC Rack PDU 2G",
                categoria = "DISTRIBUCIÓN",
                estadoRevision = EstadoRevision.Validado
            ),
            Componente(
                id = "KVM-ATN-1708",
                numero = "07",
                nombre = "Aten LCD Console KVM",
                categoria = "MONITOREO",
                estadoRevision = EstadoRevision.ConFalla
            )
        )

        return RegistroAnterior(
            estado = "REGISTRO COMPLETADO",
            verificado = true,
            fecha = "03 SEP 2026",
            horario = "14:30 — 15:12",
            tecnico = "J. Pérez",
            rack = "RACK-A01",
            componentes = listaComponentes
        )
    }
}