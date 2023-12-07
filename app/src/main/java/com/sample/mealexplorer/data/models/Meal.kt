package com.sample.mealexplorer.data.models

data class Meal(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String
){
    fun toUiModel() = MealUiModel(idMeal, strMeal, strMealThumb)
}

