package com.aviral.foodify.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aviral.foodify.dbUtils.MealDatabase

class MainViewModelFactory(
    private val mealDatabase: MealDatabase
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(mealDatabase) as T
    }
}