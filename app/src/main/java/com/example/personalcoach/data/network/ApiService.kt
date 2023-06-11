package com.example.personalcoach.data.network

import com.example.personalcoach.domain.model.item.ItemResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    companion object {
        const val BASE_URL = "http://10.0.2.2:8080/"
    }

    @GET("api/items/filter")
    suspend fun getFilterItems(
        @Query("name") name: String = "",
        @Query("brandName") brandName: String = "",
        @Query("typeName") typeName: String = ""
    ):List<ItemResponse>
}