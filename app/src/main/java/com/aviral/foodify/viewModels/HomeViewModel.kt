package com.aviral.foodify.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aviral.foodify.models.Meal
import com.aviral.foodify.models.MealList
import com.aviral.foodify.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private val tag = "AviralApi"

    private var randomMealLiveData = MutableLiveData<Meal>()

    fun getRandomMeal() {
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList> {

            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {

                if (response.body() != null) {

                    val randomMeal: Meal = response.body()!!.meals[0]

                    randomMealLiveData.value = randomMeal

                    Log.d(
                        tag,
                        "onResponse: randomMeal: ${randomMeal.idMeal} & ${randomMeal.strMeal}"
                    )


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

    fun observeRandomMealLiveData(): LiveData<Meal> {
        return randomMealLiveData
    }

}