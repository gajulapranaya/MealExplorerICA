package com.sample.mealexplorer.data.network

import com.sample.mealexplorer.data.models.FilterResponse
import com.sample.mealexplorer.data.models.MealsResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MealsApi {

    @GET(ApiConstants.ENDPOINT_CATEGORIES)
    fun getMealsCategories(): Call<MealsResponse>

    @GET(ApiConstants.ENDPOINT_FILTER)
    fun getMealsFilter(@Query("c")  c:String): Response<FilterResponse>

}