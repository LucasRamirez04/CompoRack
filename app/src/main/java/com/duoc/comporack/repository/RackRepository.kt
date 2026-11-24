package com.duoc.comporack.repository

import com.duoc.comporack.model.Racks
import com.duoc.comporack.remote.RetrofitInstance

class RackRepository {

    suspend fun obtenerRacks(): List<Racks> {
        return RetrofitInstance.api.getRacks()
    }

    suspend fun obtenerRackPorId(id: Int): Racks {
        return RetrofitInstance.api.getRackbyId(id)
    }
}