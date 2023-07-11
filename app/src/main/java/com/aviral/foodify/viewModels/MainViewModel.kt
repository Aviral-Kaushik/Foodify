package com.aviral.foodify.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aviral.foodify.dbUtils.MealDatabase
import com.aviral.foodify.models.*
import com.aviral.foodify.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(
    private val mealDatabase: MealDatabase
) : ViewModel() {

    private val tag = "AviralApi"

    private var randomMealLiveData = MutableLiveData<Meal>()
    private var mealDataLiveData = MutableLiveData<Meal>()
    private var popularMealsLiveData = MutableLiveData<List<MealsByCategory>>()
    private var categoryLiveData = MutableLiveData<List<Category>>()
    private var searchMealsLiveData = MutableLiveData<List<Meal>>()

    private val favouriteMealLiveData = mealDatabase.mealDao().getAllMeals()

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

    fun getPopularMeals() {
        RetrofitInstance.api.getMealByCategory("Seafood")
            .enqueue(object : Callback<MealsByCategoryList> {
                override fun onResponse(
                    call: Call<MealsByCategoryList>,
                    response: Response<MealsByCategoryList>
                ) {

                    if (response.body() != null) {

                        response.body()?.let {
                            popularMealsLiveData.postValue(it.meals)
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

    fun getCategoryList() {

        RetrofitInstance.api.getCategories().enqueue(object : Callback<CategoryList> {
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {

                if (response.body() != null) {

                    response.body()?.let { categoryList ->
                        categoryLiveData.postValue(categoryList.categories)
                    }


                }

            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.d(tag, "onFailure: Got error message while getting response ${t.message}")
            }
        })

    }

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


    fun searchMeals(searchString: String) {

        RetrofitInstance.api.searchMeals(searchString).enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {

                val mealList = response.body()?.meals

                mealList?.let {
                    searchMealsLiveData.postValue(it)
                }

            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d(tag, "onFailure: Got error message while getting response ${t.message}")
            }

        })

    }

    fun observeCategoryLiveData(): LiveData<List<Category>> {
        return categoryLiveData
    }

    fun observePopularMealsLiveData(): LiveData<List<MealsByCategory>> {
        return popularMealsLiveData
    }

    fun observeRandomMealLiveData(): LiveData<Meal> {
        return randomMealLiveData
    }

    fun observeFavouriteMeal(): LiveData<List<Meal>> {
        return favouriteMealLiveData
    }

    fun observerMealLiveData(): LiveData<Meal> {
        return mealDataLiveData
    }

    fun observeSearchMealLiveData(): LiveData<List<Meal>> {
        return searchMealsLiveData
    }

    fun insertMeal(meal: Meal) {
        viewModelScope.launch {
            mealDatabase.mealDao().upsertMeal(meal)
        }
    }

    fun deleteMeal(meal: Meal) {
        viewModelScope.launch {
            mealDatabase.mealDao().deleteMeal(meal)
        }
    }

}