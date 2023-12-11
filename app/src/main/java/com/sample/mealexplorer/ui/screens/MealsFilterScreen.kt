package com.sample.mealexplorer.ui.screens

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.auth.FirebaseAuth
import com.sample.mealzapp.R
import com.sample.mealexplorer.data.models.CategoryUiModel
import com.sample.mealexplorer.ui.components.ProgressErrorView
import com.sample.mealexplorer.ui.navigations.MealsScreens
import com.sample.mealexplorer.ui.states.MealsUiState
import com.sample.mealexplorer.ui.theme.MealzAppTheme
import com.sample.mealexplorer.ui.viewmodel.MealsCategoriesViewModel
import androidx.compose.runtime.MutableState
import com.sample.mealexplorer.data.models.FilterResponse
import com.sample.mealexplorer.data.models.Meal
import com.sample.mealexplorer.data.models.MealUiModel
import com.sample.mealexplorer.data.models.MealsResponse
import com.sample.mealexplorer.data.network.ApiService
import com.sample.mealexplorer.data.network.MealsApi
import com.sample.mealexplorer.ui.navigations.DestinationScreen
import com.sample.mealexplorer.ui.states.FilterUiState
import com.sample.mealexplorer.ui.viewmodel.MealsFilterViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale

object EmptyData{
    val filterResponse=FilterResponse(arrayListOf(
           ))

}
@Composable
fun getData(category:String){
//    var mealsResponse by remember { mutableStateOf<FilterResponse?>(emptyData.filterResponse) }

//    CoroutineScope(Dispatchers.IO).launch {
////        val mealsApi = ApiClient.getInstance().create(ApiService::class.java)
////        val result = mealsApi.getMealsFilter(category)
////        Log.d("ayush: ", result.body().toString())
////        Log.d("ayush error: ", result.errorBody().toString().toString())
////        if (result.body() != null && result.isSuccessful) {
////            mealsResponse = result.body()!!
////
////            Log.d("ayush: ", result.body()!!.meals.toString())
////
////        }
//    }
}
@Composable
fun MealsFilterScreen(category: CategoryUiModel, navController: NavHostController) {

    var emptyData = EmptyData;
//    val viewModel = hiltViewModel<MealsFilterViewModel>()
//    viewModel.getMealsFilter(category.name)
    var mealsResponse by remember { mutableStateOf<FilterResponse?>(emptyData.filterResponse) }
    CoroutineScope(Dispatchers.IO).launch {
        val mealsApi = ApiClient.getInstance().create(ApiService::class.java)
        val result = mealsApi.getMealsFilter(category.name)
        Log.d("ayush: ", result.body().toString())
        Log.d("ayush error: ", result.errorBody().toString().toString())
        if (result.body() != null && result.isSuccessful) {
            mealsResponse = result.body()!!

            Log.d("ayush: ", result.body()!!.meals.toString())

        }
    }


//    val uiState by viewModel.uiState1.collectAsState()
    val textState = remember { mutableStateOf(TextFieldValue("")) }
    Log.d("data value", textState.value.text)
    Column {
        TopAppBar(
            title = {
                Text(text = "MealExplorer", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            },
            navigationIcon = {
                IconButton(onClick = {/* Do Something*/ }) {
//                Icon(Icons.Filled.Home, null)
                    Image(
                        painter = painterResource(id = R.mipmap.meal_logo_png),
                        contentDescription = null,
                        Modifier.size(24.dp)
                    )
                }
            },
            actions = {
                Button(
                    onClick = {
                        val authHelper = FirebaseAuth.getInstance()
                        authHelper.signOut()
                        navController!!.navigate(MealsScreens.LOGIN_SCREEN.name) {
                            popUpTo(route = MealsScreens.LIST_SCREEN.name) {
                                inclusive = true
                            }
                        }


                    }, colors = ButtonDefaults.buttonColors(Color.Red)
                ) {
                    Text(text = "Logout", color = Color.White)
                }
            },
        )

        SearchView(state = textState)

        Spacer(modifier = Modifier.height(10.dp))
//        when (uiState.state) {
//            FilterUiState.State.None,
//            FilterUiState.State.Loading -> ProgressErrorView(isProgress = true)
//
//            FilterUiState.State.Empty -> ProgressErrorView(message = stringResource(R.string.empty_categories))
//            FilterUiState.State.Error -> ProgressErrorView(message = uiState.error?.localizedMessage.toString())
//            FilterUiState.State.Success -> {
////                var data: ArrayList<CategoryUiModel> = ArrayList()
////                uiState.data.forEach {
////                    if ("1" != it.id) {
////                        data.add(it)
////                    }
////                }


                var filteredData: ArrayList<Meal> = ArrayList()


                LazyColumn(
//                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(dimensionResource(R.dimen.lazy_column_padding))
                ) {
                    val searchedText = textState.value.text
                    if (searchedText.isEmpty()) {
                        mealsResponse?.meals?.let { filteredData.addAll(it) }
                    } else {
                        val resultList = ArrayList<Meal>()
                        for (i in filteredData) {
                            if (i.strMeal.lowercase(Locale.getDefault())
                                    .contains(searchedText.lowercase(Locale.getDefault()))
                            ) {
                                resultList.add(i)
                            }
                        }
                        filteredData.addAll(resultList)

                    }
                    items(filteredData) { category ->


                        FilterCategory(category) { id ->

                            navController.navigate(
                                route = DestinationScreen.MainScreenDest.getFullRoute(
                                    category.idMeal)
                            )
//
//                            val route = "${MealsScreens.DETAILS_SCREEN.name}/$id"
//                            navController?.navigate(route)
                        }

                    }

//                }
            }
        }


}


@Composable
fun FilterCategory(
    category: Meal,
    navigationCallBack: (String) -> Unit,
) {



    Card(
        shape = RoundedCornerShape(dimensionResource(R.dimen.category_card_corner_size)),
        elevation = dimensionResource(R.dimen.category_card_elevation),
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .clickable {
                navigationCallBack(category.idMeal)
            }

    ) {
        Row(
            Modifier
                .animateContentSize()
                .fillMaxSize(),
//            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            Image(
                painter = rememberAsyncImagePainter(category.strMealThumb),
                contentDescription = "Meals Category imgae",
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.categroy_image_size))
                    .padding(dimensionResource(R.dimen.category_image_padding))
            )

            Text(text = category.strMeal, style = MaterialTheme.typography.subtitle1)

        }
    }

}



