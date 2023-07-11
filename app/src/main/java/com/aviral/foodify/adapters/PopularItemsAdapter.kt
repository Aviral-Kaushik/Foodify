package com.aviral.foodify.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.aviral.foodify.R
import com.aviral.foodify.activities.MealActivity
import com.aviral.foodify.fragments.bottomSheet.MealBottomSheetFragment
import com.aviral.foodify.models.MealsByCategory
import com.bumptech.glide.Glide

class PopularItemsAdapter(
    private val context: Context,
    private var mealList: List<MealsByCategory>,
    private var childFragmentManager: FragmentManager
): RecyclerView.Adapter<PopularItemsAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.layout_popular_items, parent, false
        )

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Glide.with(context)
            .load(mealList[position].strMealThumb)
            .into(holder.mealImage)

        holder.itemView.setOnClickListener {
            Intent(context, MealActivity::class.java).also {
                it.putExtra(context.getString(R.string.meal_id), mealList[position].idMeal)
                it.putExtra(context.getString(R.string.meal_name), mealList[position].strMeal)
                it.putExtra(context.getString(R.string.meal_thumb), mealList[position].strMealThumb)
                context.startActivity(it)
            }
        }


        holder.itemView.setOnLongClickListener {
            val mealBottomSheetFragment = MealBottomSheetFragment.newInstance(mealList[position].idMeal)
            mealBottomSheetFragment.show(childFragmentManager, "Meal Info")
            true
        }

    }

    override fun getItemCount(): Int {
        return mealList.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val mealImage: ImageView = itemView.findViewById(R.id.image_popular_meal_item)

    }



}