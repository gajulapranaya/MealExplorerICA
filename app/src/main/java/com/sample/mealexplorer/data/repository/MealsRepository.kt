package com.sample.mealexplorer.data.repository

import com.sample.mealexplorer.data.models.Category
import com.sample.mealexplorer.data.models.CategoryUiModel
import com.sample.mealexplorer.data.network.MealsNetworkDataSource
import com.sample.mealexplorer.data.wrappers.ResponseWrapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MealsRepository @Inject constructor(
    private val mealsNetworkDataSource: MealsNetworkDataSource,
) {

    private var cachedMeals = listOf<CategoryUiModel>()

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

    private fun getCategoryUiModelList(response: ResponseWrapper.Success<List<Category>>): List<CategoryUiModel> {
        val categoryUiModelList = response.data?.map { category ->
            val uiModel = category.toUiModel()
            val cachedUiModel = cachedMeals.firstOrNull { uiModel.id == it.id }
            uiModel.copy(isExpanded = cachedUiModel?.isExpanded ?: false)
        }
        cachedMeals = categoryUiModelList ?: cachedMeals
        return cachedMeals
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