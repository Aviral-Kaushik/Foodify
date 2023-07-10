package com.aviral.foodify.retrofit

import com.aviral.foodify.models.Meal
import com.aviral.foodify.models.MealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {

    @GET("random.php")
    fun getRandomMeal(): Call<MealList>

    @GET("lookup.php?")
    fun getMealById(@Query("i") mealId: String): Call<MealList>

}