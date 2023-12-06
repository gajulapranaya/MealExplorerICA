package com.sample.mealexplorer.ui.screens

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.sample.mealexplorer.data.models.CategoryUiModel
import com.sample.mealzapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MealDetailsScreen(category: CategoryUiModel, navController: NavHostController) {

    var profilePictureState by remember {
        mutableStateOf(ProfilePictureState.Normal)
    }

    val transition = updateTransition(targetState = profilePictureState, label = "")

    val imageSize: Dp by transition.animateDp(label = "") {
        it.size
    }

    val color by transition.animateColor(label = "") {
        it.color
    }

    val widthSize: Dp by transition.animateDp(label = "") {
        it.borderWidth
    }


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TopAppBar(
            title = {
                Text(
                    text = "Meal Details",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            },
            navigationIcon = {
                IconButton(onClick = {
                    navController.popBackStack()

                }) {
                    Icon(Icons.Filled.ArrowBack, null, tint = Color.White)

                }
            },

            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = Color(0xFF3F51B5),
                titleContentColor = Color.White,
            )
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.verticalScroll(
                rememberScrollState()
            )
        ) {
            Card(
                modifier = Modifier.padding(dimensionResource(R.dimen.details_card_padding)),
                shape = CircleShape,
//                border = BorderStroke(
//                    width = widthSize,
//                    color = color
//                )
            ) {
                AsyncImage(
                    model = category.imageUrl,
                    contentDescription = "Meal image",
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(imageSize)
                        .padding(dimensionResource(R.dimen.meal_image_padding))
                        .clickable {
                            profilePictureState =
                                if (profilePictureState == ProfilePictureState.Normal)
                                    ProfilePictureState.Expanded else ProfilePictureState.Normal
                        }
                )
            }
            Text(text = category.name, style = MaterialTheme.typography.h6)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = category.description,
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier.padding(10.dp),

            )
        }


    }
}

enum class ProfilePictureState(val color: Color, val size: Dp, val borderWidth: Dp) {
    Normal(Color.Magenta, 120.dp, 8.dp),
    Expanded(Color.Green, 200.dp, 24.dp)
}

@Preview
@Composable
fun PreviewMeal() {
//    MealDetailsScreen(
//        category = CategoryUiModel(
//            "1",
//            "Breakfast",
//            "The domestic goat or simply goat (Capra aegagrus hircus) is a subspecies of C. aegagrus domesticated from the wild goat of Southwest Asia and Eastern Europe. The goat is a member of the animal family Bovidae and the subfamily Caprinae, meaning it is closely related to the sheep. There are over 300 distinct breeds of goat. Goats are one of the oldest domesticated species of animal, and have been used for milk, meat, fur and skins across much of the world. Milk from goats is often turned into goat cheese.",
//            "https://www.themealdb.com/images/category/goat.png",
//            false
//        ),
//        navController = navController
//    )

}