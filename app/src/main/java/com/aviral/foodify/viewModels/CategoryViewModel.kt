package com.aviral.foodify.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aviral.foodify.models.MealsByCategory
import com.aviral.foodify.models.MealsByCategoryList
import com.aviral.foodify.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryViewModel: ViewModel() {

    private val tag = "AviralApi"

    private val mealsByCategoryLiveData = MutableLiveData<List<MealsByCategory>>()

    fun getMealsByCategory(category: String) {

        RetrofitInstance.api.getMealByCategory(category).enqueue(object : Callback<MealsByCategoryList>  {
            override fun onResponse(
                call: Call<MealsByCategoryList>,
                response: Response<MealsByCategoryList>
            ) {
                if (response.body() != null) {

                    response.body()?.let {mealsByCategoryList ->

                        mealsByCategoryLiveData.postValue(mealsByCategoryList.meals)

                    }


                } else {
                    Log.d(tag, "onResponse: response is null")
                    return
                }
            }

            override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                Log.d(tag, "onFailure: Got error message while getting response ${t.message}")
            }

        })

    }

    fun observeMealsByCategoryLiveData(): LiveData<List<MealsByCategory>> {
        return mealsByCategoryLiveData
    }

}