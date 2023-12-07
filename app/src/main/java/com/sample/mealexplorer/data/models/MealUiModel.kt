package com.sample.mealexplorer.data.models

data class MealUiModel(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String,
    var isExpanded: Boolean = false,
)