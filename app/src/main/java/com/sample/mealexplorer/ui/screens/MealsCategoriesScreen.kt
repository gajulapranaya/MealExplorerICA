package com.sample.mealexplorer.ui.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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


@Composable
fun MealsCategoriesScreen(navController: NavHostController? = null) {

    val viewModel = hiltViewModel<MealsCategoriesViewModel>()
    val uiState by viewModel.uiState.collectAsState()
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
        Spacer(modifier = Modifier.height(10.dp))
    when (uiState.state) {
        MealsUiState.State.None,
        MealsUiState.State.Loading -> ProgressErrorView(isProgress = true)

        MealsUiState.State.Empty -> ProgressErrorView(message = stringResource(R.string.empty_categories))
        MealsUiState.State.Error -> ProgressErrorView(message = uiState.error?.localizedMessage.toString())
        MealsUiState.State.Success -> {
            var data: ArrayList<CategoryUiModel> = ArrayList()
            uiState.data.forEach {
                if ("1" != it.id) {
                    data.add(it)
                }
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(dimensionResource(R.dimen.lazy_column_padding))
            ) {
                items(data) { category ->


                    MealCategory(category, onExpanded = viewModel::onCategoryExpanded) { id ->

                        val route = "${MealsScreens.DETAILS_SCREEN.name}/$id"
                        navController?.navigate(route)
                    }

                }
            }
        }
    }
    }


}


@Composable
fun MealCategory(
    category: CategoryUiModel,
    onExpanded: (CategoryUiModel) -> Unit,
    navigationCallBack: (String) -> Unit,
) {

    var isExpanded by rememberSaveable {
        mutableStateOf(category.isExpanded)
    }

    var lineCount by rememberSaveable {
        mutableStateOf(3)
    }

    Card(
        shape = RoundedCornerShape(dimensionResource(R.dimen.category_card_corner_size)),
        elevation = dimensionResource(R.dimen.category_card_elevation),
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .clickable {
                navigationCallBack(category.id)
            }

    ) {
        Column(
            Modifier
                .animateContentSize()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = rememberAsyncImagePainter(category.imageUrl),
                contentDescription = "Meals Category imgae",
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.categroy_image_size))
                    .padding(dimensionResource(R.dimen.category_image_padding))
            )

            Text(text = category.name, style = MaterialTheme.typography.subtitle1)

        }
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MealzAppTheme {
        MealsCategoriesScreen()
    }
}