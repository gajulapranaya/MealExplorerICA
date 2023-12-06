package com.sample.mealexplorer.data.network

import com.sample.mealexplorer.data.models.Category
import com.sample.mealexplorer.data.wrappers.ResponseWrapper

interface MealsNetworkDataSource {
    suspend fun getMealsCategories(): ResponseWrapper<List<Category>>
}