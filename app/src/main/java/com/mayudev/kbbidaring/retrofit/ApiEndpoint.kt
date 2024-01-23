package com.mayudev.kbbidaring.retrofit

import com.mayudev.kbbidaring.model.MainModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiEndpoint {
    @GET("kbbi")
    fun getDataMean(@Query("text") text: String): Call<MainModel>
}