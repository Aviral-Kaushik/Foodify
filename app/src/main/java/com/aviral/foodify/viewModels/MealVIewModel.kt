package com.aviral.foodify.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aviral.foodify.dbUtils.MealDatabase
import com.aviral.foodify.models.Meal
import com.aviral.foodify.models.MealList
import com.aviral.foodify.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealVIewModel(
    private val mealDatabase: MealDatabase
) : ViewModel() {

    private val tag = "AviralApi"

    private var mealDataLiveData = MutableLiveData<Meal>()

    fun getMealByID(mealId: String) {

        RetrofitInstance.api.getMealById(mealId).enqueue(object : Callback<MealList> {

            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {

                if (response.body() != null) {

                    mealDataLiveData.value = response.body()!!.meals[0]

                } else {
                    Log.d(tag, "onResponse: response is null")
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {

                Log.d(tag, "onFailure: Got error message while getting response ${t.message}")

            }
        })

    }

    fun observerMealLiveData(): LiveData<Meal> {
        return mealDataLiveData
    }

    fun insertMeal(meal: Meal) {
        viewModelScope.launch {
            mealDatabase.mealDao().upsertMeal(meal)
        }
    }

}