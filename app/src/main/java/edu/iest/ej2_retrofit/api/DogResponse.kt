package edu.iest.ej2_retrofit.api

import com.google.gson.annotations.SerializedName

data class DogResponse(
    @SerializedName("message")
    val imageUrl: String
)
