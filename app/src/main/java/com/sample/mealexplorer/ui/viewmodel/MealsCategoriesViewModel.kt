package com.sample.mealexplorer.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.mealexplorer.data.models.CategoryUiModel
import com.sample.mealexplorer.data.repository.MealsRepository
import com.sample.mealexplorer.data.wrappers.ResponseWrapper
import com.sample.mealexplorer.ui.states.FilterUiState
import com.sample.mealexplorer.ui.states.MealsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealsCategoriesViewModel @Inject constructor(
    private val mealsRepository: MealsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MealsUiState())
    val uiState = _uiState.asStateFlow()


    init {
        getMealsCategories()
//        getMealsFilter("seafood")
    }

    private fun getMealsCategories() {

        _uiState.update { it.copy(state = MealsUiState.State.Loading) }

        viewModelScope.launch(Dispatchers.IO) {
            when (val response = mealsRepository.getMealsCategories()) {
                is ResponseWrapper.Success -> {
                    _uiState.update {
                        if (response.data.isNullOrEmpty()) {
                            it.copy(state = MealsUiState.State.Empty)
                        } else {
                            it.copy(
                                state = MealsUiState.State.Success,
                                data = response.data
                            )
                        }
                    }
                }

                is ResponseWrapper.Error -> _uiState.update {
                    it.copy(state = MealsUiState.State.Error)
                }
            }
        }


    }


    fun onCategoryExpanded(categoryUiModel: CategoryUiModel) {
        mealsRepository.setCategoryExpanded(categoryUiModel)
    }


}