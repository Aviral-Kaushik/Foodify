package com.aviral.foodify.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aviral.foodify.R
import com.aviral.foodify.activities.MealActivity
import com.aviral.foodify.databinding.LayoutCategoryMealItemBinding
import com.aviral.foodify.models.Meal
import com.bumptech.glide.Glide

class FavoritesAdapter(
    private val context: Context
) : RecyclerView.Adapter<FavoritesAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(LayoutCategoryMealItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Glide.with(context)
            .load(differ.currentList[position].strMealThumb)
            .into(holder.binding.imgMeal)

        holder.binding.tvMealName.text = differ.currentList[position].strMeal

        holder.itemView.setOnClickListener {
            Intent(context, MealActivity::class.java).also {
                it.putExtra(context.getString(R.string.meal_id), differ.currentList[position].idMeal)
                it.putExtra(context.getString(R.string.meal_name), differ.currentList[position].strMeal)
                it.putExtra(context.getString(R.string.meal_thumb), differ.currentList[position].strMealThumb)
                context.startActivity(it)
            }
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    inner class ViewHolder(val binding: LayoutCategoryMealItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object : DiffUtil.ItemCallback<Meal>() {
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffUtil)

}