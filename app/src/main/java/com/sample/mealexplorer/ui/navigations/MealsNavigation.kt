@file:OptIn(ExperimentalAnimationApi::class)

package com.sample.mealexplorer.ui.navigations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.sample.mealexplorer.ui.screens.LoginScreen
import com.sample.mealexplorer.ui.screens.MealDetailsScreen
import com.sample.mealexplorer.ui.screens.MealsCategoriesScreen
import com.sample.mealexplorer.ui.screens.MealsFilterScreen
import com.sample.mealexplorer.ui.screens.SignUp
import com.sample.mealexplorer.ui.screens.SplashScreen
import com.sample.mealexplorer.ui.viewmodel.MealDetailsViewModel

const val ARG_MEAL = "MEAL_CATEGORY_ID"
const val ARG_MEAL_ID = "MEAL_ID"

enum class MealsScreens {
    SPLASH_SCREEN,
    LIST_SCREEN,
    FILTER_SCREEN,
    DETAILS_SCREEN,
    LOGIN_SCREEN,
    SIGNUP_SCREEN
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MealsNavigation() {

    val navController = rememberAnimatedNavController()

    AnimatedNavHost(
        navController = navController,
        startDestination = MealsScreens.SPLASH_SCREEN.name
    ) {

        composable(route = MealsScreens.SPLASH_SCREEN.name) {
            SplashScreen(navController)
        }

        composable(route = MealsScreens.LOGIN_SCREEN.name) {
            LoginScreen(navController = navController)
        }

        composable(route = MealsScreens.SIGNUP_SCREEN.name) {
            SignUp(navController = navController)
        }



        composable(route = MealsScreens.LIST_SCREEN.name) {
            MealsCategoriesScreen(navController)

        }
        val route1 = "${MealsScreens.FILTER_SCREEN.name}/{$ARG_MEAL}"
        composable(route = route1, arguments = listOf(navArgument(ARG_MEAL) {
            type = NavType.StringType
        })) {
            val viewModel = hiltViewModel<MealDetailsViewModel>()
            viewModel.mealState?.let { category -> MealsFilterScreen(category, navController) }
        }

        val route = DestinationScreen.MainScreenDest.route
        composable(
            route = route,
            arguments = listOf(navArgument(ID_KEY) {
                type = NavType.StringType
            })
        ) {
                navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getString(ID_KEY)
            if (id != null) {
                MealDetailsScreen(id, navController)
            }
        }
        }

    }

const val ID_KEY = "ID_key"
const val TEXT_KEY = "TEXT_key"
sealed class DestinationScreen(val route: String) {
    object SplashScreenDest : DestinationScreen(route = "splash_screen")
    object MainScreenDest : DestinationScreen(route = "main_screen"+ "/{$ID_KEY}"){
        fun getFullRoute(id: String): String {
            return "main_screen" + "/$id"
        }
    }
    object HomeScreenDest : DestinationScreen(route = "home_screen")
    object LoginScreenDest : DestinationScreen(route = "login_screen")
    object SignupScreenDest : DestinationScreen(route = "signup_screen")
}

