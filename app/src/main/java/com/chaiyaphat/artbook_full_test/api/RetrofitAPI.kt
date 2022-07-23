package com.chaiyaphat.artbook_full_test.api

import com.chaiyaphat.artbook_full_test.model.ImageResponse
import com.chaiyaphat.artbook_full_test.util.Util.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitAPI {
    @GET("/api/")
    suspend fun imageSearch(
        @Query("q") search: String,
        @Query("key") apiKey: String = API_KEY,
    ): Response<ImageResponse>
}