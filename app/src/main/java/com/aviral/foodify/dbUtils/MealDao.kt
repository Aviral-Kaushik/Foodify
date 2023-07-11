package com.aviral.foodify.dbUtils

import androidx.lifecycle.LiveData
import androidx.room.*
import com.aviral.foodify.models.Meal

@Dao
interface MealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertMeal(meal: Meal)

    @Delete
    suspend fun deleteMeal(meal: Meal)

    @Query("SELECT * FROM meals")
    fun getAllMeals(): LiveData<List<Meal>>

}