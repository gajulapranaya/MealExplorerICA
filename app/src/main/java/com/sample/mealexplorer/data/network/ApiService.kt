package com.sample.mealexplorer.data.network

import com.sample.mealexplorer.data.models.FilterResponse
import com.sample.mealexplorer.data.models.GetMealByIdResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(ApiConstants.ENDPOINT_FILTER)
   suspend fun getMealsFilter(@Query("c")  c:String): Response<FilterResponse>

   @GET(ApiConstants.ENDPOINT_LOOK_ID)
   suspend fun getMealsById(@Query("i")  i:String): Response<GetMealByIdResponse>
}