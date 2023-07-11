package com.aviral.foodify.adapters

import android.content.Context
import android.icu.util.ULocale.Category
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aviral.foodify.databinding.LayoutCategoryMealItemBinding
import com.aviral.foodify.models.MealsByCategory
import com.bumptech.glide.Glide

class CategoryMealsAdapter(
    private val context: Context,
    private val categoryMeal: List<MealsByCategory>
) : RecyclerView.Adapter<CategoryMealsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            LayoutCategoryMealItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Glide.with(context)
            .load(categoryMeal[position].strMealThumb)
            .into(holder.binding.imgMeal)

        holder.binding.tvMealName.text = categoryMeal[position].strMeal

    }

    override fun getItemCount(): Int {
        return categoryMeal.size
    }

    inner class ViewHolder(val binding: LayoutCategoryMealItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}