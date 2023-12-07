package com.sample.mealexplorer.data.network

import com.sample.mealexplorer.data.models.FilterResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(ApiConstants.ENDPOINT_FILTER)
   suspend fun getMealsFilter(@Query("c")  c:String): Response<FilterResponse>
}