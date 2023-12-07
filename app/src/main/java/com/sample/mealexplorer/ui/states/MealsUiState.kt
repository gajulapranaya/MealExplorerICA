package com.sample.mealexplorer.ui.states

import com.sample.mealexplorer.data.models.CategoryUiModel
import com.sample.mealexplorer.data.models.MealUiModel

data class MealsUiState(
    val state: State = State.None,
    val data: List<CategoryUiModel> = emptyList(),
    val error: Throwable? = Throwable(),
) {

    sealed class State {
        object None : State()
        object Loading : State()
        object Success : State()
        object Empty : State()
        object Error : State()
    }

}
data class FilterUiState(
    val state: State = State.None,
    val data: List<MealUiModel> = emptyList(),
    val error: Throwable? = Throwable(),
) {

    sealed class State {
        object None : State()
        object Loading : State()
        object Success : State()
        object Empty : State()
        object Error : State()
    }

}