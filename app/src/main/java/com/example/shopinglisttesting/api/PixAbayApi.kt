package com.example.shopinglisttesting.api

import com.ResponseImage
import com.example.shopinglisttesting.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PixAbayApi {

    @GET("/api/")
    suspend fun getPixImage(
        @Query("q")
        everyPixImage: String,
        @Query("key")
        apiKey: String = BuildConfig.API_KEY
    ): Response<ResponseImage>

}