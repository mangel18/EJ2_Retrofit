package edu.iest.ej2_retrofit.api

import edu.iest.ej2_retrofit.api.DogResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface DogApi {
    @GET("breeds/image/random")
    suspend fun getRandomDog(): DogResponse

    @GET("breed/{boxer}/images")
    suspend fun getRazas(@Path("breed") breed: String): ImagenesPorRazaResponse
}


