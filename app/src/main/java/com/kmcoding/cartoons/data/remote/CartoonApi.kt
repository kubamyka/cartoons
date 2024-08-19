package com.kmcoding.cartoons.data.remote

import com.kmcoding.cartoons.domain.model.Cartoon
import retrofit2.http.GET

interface CartoonApi {
    @GET("/cartoons/cartoons2D")
    suspend fun getCartoons(): List<Cartoon>
}
