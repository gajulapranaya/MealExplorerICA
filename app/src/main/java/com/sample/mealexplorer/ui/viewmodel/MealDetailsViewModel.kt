package com.sample.mealexplorer.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.sample.mealexplorer.data.models.CategoryUiModel
import com.sample.mealexplorer.data.repository.MealsRepository
import com.sample.mealexplorer.ui.navigations.ARG_MEAL
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MealDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    mealsRepository: MealsRepository
) : ViewModel() {

    private var _mealState = mutableStateOf<CategoryUiModel?>(null)
    val mealState get() = _mealState.value

    init {
        val mealId = savedStateHandle.get<String>(ARG_MEAL)
        _mealState.value = mealsRepository.getMeal(mealId)
    }

}