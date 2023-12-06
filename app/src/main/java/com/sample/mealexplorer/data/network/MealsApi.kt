package com.sample.mealexplorer.data.network

import com.sample.mealexplorer.data.models.MealsResponse
import retrofit2.Call
import retrofit2.http.GET

interface MealsApi {

    @GET(ApiConstants.ENDPOINT_CATEGORIES)
    fun getMealsCategories(): Call<MealsResponse>

}