package com.sample.mealexplorer.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.sample.mealexplorer.ui.navigations.MealsNavigation
import com.sample.mealexplorer.ui.theme.MealzAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MealzAppTheme {
                MealsNavigation()
            }
        }
    }

}