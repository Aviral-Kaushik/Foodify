package com.aviral.foodify.retrofit

import com.aviral.foodify.models.CategoryList
import com.aviral.foodify.models.MealsByCategoryList
import com.aviral.foodify.models.MealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {

    @GET("random.php")
    fun getRandomMeal(): Call<MealList>

    @GET("lookup.php")
    fun getMealById(@Query("i") mealId: String): Call<MealList>

    @GET("filter.php")
    fun getMealByCategory(@Query("c") mealCategory: String): Call<MealsByCategoryList>

    @GET("categories.php")
    fun getCategories(): Call<CategoryList>

    @GET("search.php")
    fun searchMeals(@Query("s") search: String): Call<MealList>

}