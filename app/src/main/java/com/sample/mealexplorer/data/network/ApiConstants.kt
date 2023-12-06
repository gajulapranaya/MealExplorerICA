package com.sample.mealexplorer.data.network

object ApiConstants {

    private const val BASE_URL = "https://www.themealdb.com/api/json"
    private const val API_VERSION = "v1"
    private const val API_KEY = "1"

    const val API_URL = "$BASE_URL/$API_VERSION/$API_KEY/"

    const val ENDPOINT_CATEGORIES = "categories.php"

}