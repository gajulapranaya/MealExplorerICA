package com.sample.mealexplorer.data.models

import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("idCategory")
    val id: String,
    @SerializedName("strCategory")
    val name: String,
    @SerializedName("strCategoryDescription")
    val description: String,
    @SerializedName("strCategoryThumb")
    val imageUrl: String,
) {

    fun toUiModel() = CategoryUiModel(id, name, description, imageUrl)

}
