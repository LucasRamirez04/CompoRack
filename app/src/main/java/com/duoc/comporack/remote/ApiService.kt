package com.duoc.comporack.remote

import com.duoc.comporack.model.RackItem
import retrofit2.http.GET

interface ApiService {
    @GET("racks")
    suspend fun getRacks(): List<RackItem>
}