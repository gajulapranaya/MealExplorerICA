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
class MealsFilterViewModel @Inject constructor(
    private val mealsRepository: MealsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MealsUiState())
    val uiState = _uiState.asStateFlow()
    private val _uiState1 = MutableStateFlow(FilterUiState())
    val uiState1 = _uiState1.asStateFlow()




//     fun getMealsFilter(category:String) {
//
//        _uiState1.update { it.copy(state = FilterUiState.State.Loading) }
//
//        viewModelScope.launch(Dispatchers.IO) {
//            when (val response = mealsRepository.getMealsFilter(category)) {
//                is ResponseWrapper.Success -> {
//                    _uiState1.update {
//                        if (response.data.isNullOrEmpty()) {
//                            it.copy(state = FilterUiState.State.Empty)
//                        } else {
//                            Log.d("filter data ", it.data.toString())
//                            it.copy(
//                                state = FilterUiState.State.Success,
//                                data = response.data
//                            )
//                        }
//                    }
//                }
//
//                is ResponseWrapper.Error -> _uiState1.update {
//                    it.copy(state = FilterUiState.State.Error)
//                }
//            }
//        }
//
//
//    }

    fun onCategoryExpanded(categoryUiModel: CategoryUiModel) {
        mealsRepository.setCategoryExpanded(categoryUiModel)
    }


}