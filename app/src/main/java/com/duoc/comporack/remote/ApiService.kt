package com.duoc.comporack.remote

import com.duoc.comporack.model.ComponenteRack
import com.duoc.comporack.model.Racks
import com.duoc.comporack.model.Usuario
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    //Endpoint 1 --> Validar el login
    @POST("auth/login")
    suspend fun login(@Body datos: Usuario): Usuario

    //Endpoint 2 --> Obtener usuario por id
    @GET("usuario/{usuario_id}")
    suspend fun getUsuario(@Path("usuario_id") id: Int): Usuario

    //Endpoint 3 --> Llama a todos los racks
    @GET("racks")
    suspend fun getRacks(): List<Racks>

    //Endpoint 4 --> Obtener Rack por Id
    @GET("racks/{racks_id}")
    suspend fun getRackbyId(@Path("racks_id") id: Int): Racks

    //Endpoint 5 --> Actualizar rack luego de registro
    @PATCH("racks/{racks_id}")
    suspend fun actualizarRack(@Path("racks_id") id: Int, @Body rack: Racks) : Racks

    //Endpoint 6 --> Traer todos los componentes registrados
    @GET("componentes_racks")
    suspend fun getComponentes(): List<ComponenteRack>

    //Endpoint 7 --> Validar codigo del componente de forma manual
    @GET("componentes_racks/validar/{num_serie}")
    suspend fun validarComponente(@Path("num_serie") num_serie: String): ComponenteRack
}