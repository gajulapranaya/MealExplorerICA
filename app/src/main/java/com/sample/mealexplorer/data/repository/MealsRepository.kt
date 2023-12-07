package com.sample.mealexplorer.data.repository

import com.sample.mealexplorer.data.models.Category
import com.sample.mealexplorer.data.models.CategoryUiModel
import com.sample.mealexplorer.data.models.Meal
import com.sample.mealexplorer.data.models.MealUiModel
import com.sample.mealexplorer.data.network.MealsNetworkDataSource
import com.sample.mealexplorer.data.wrappers.ResponseWrapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MealsRepository @Inject constructor(
    private val mealsNetworkDataSource: MealsNetworkDataSource,
) {

    private var cachedMeals = listOf<CategoryUiModel>()
    private var cachedMeals1 = listOf<MealUiModel>()

    suspend fun getMealsCategories(): ResponseWrapper<List<CategoryUiModel>> {
        return when (val response = mealsNetworkDataSource.getMealsCategories()) {
            is ResponseWrapper.Error -> {
                ResponseWrapper.Error(response.exception)
            }

            is ResponseWrapper.Success -> {
                ResponseWrapper.Success(getCategoryUiModelList(response))
            }
        }
    }

//    suspend fun getMealsFilter(category:String): ResponseWrapper<List<MealUiModel>> {
//        return when (val response = mealsNetworkDataSource.getMealsFilter(category)) {
//            is ResponseWrapper.Error -> {
//                ResponseWrapper.Error(response.exception)
//            }
//
//            is ResponseWrapper.Success -> {
//                ResponseWrapper.Success(getFilterUiModelList(response))
//            }
//        }
//    }

    private fun getCategoryUiModelList(response: ResponseWrapper.Success<List<Category>>): List<CategoryUiModel> {
        val categoryUiModelList = response.data?.map { category ->
            val uiModel = category.toUiModel()
            val cachedUiModel = cachedMeals.firstOrNull { uiModel.id == it.id }
            uiModel.copy(isExpanded = cachedUiModel?.isExpanded ?: false)
        }
        cachedMeals = categoryUiModelList ?: cachedMeals
        return cachedMeals
    }

    private fun getFilterUiModelList(response: ResponseWrapper.Success<List<Meal>>): List<MealUiModel> {
        val categoryUiModelList = response.data?.map { category ->
            val uiModel = category.toUiModel()
            val cachedUiModel = cachedMeals1.firstOrNull { uiModel.idMeal == it.idMeal }
            uiModel.copy(isExpanded = cachedUiModel?.isExpanded ?: false)
        }
        cachedMeals1 = categoryUiModelList ?: cachedMeals1
        return cachedMeals1
    }

    fun getMeal(id: String?): CategoryUiModel? {
        return cachedMeals.firstOrNull {
            it.id == id
        }
    }

    fun setCategoryExpanded(categoryUiModel: CategoryUiModel) {
        cachedMeals = cachedMeals.map {
            if (it.id == categoryUiModel.id) {
                it.isExpanded = !it.isExpanded
            }
            it
        }
    }


}