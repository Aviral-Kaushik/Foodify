package com.aviral.foodify.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aviral.foodify.R
import com.aviral.foodify.activities.CategoryMealActivity
import com.aviral.foodify.databinding.LayoutCategoryItemBinding
import com.aviral.foodify.models.Category
import com.bumptech.glide.Glide

class CategoryAdapter(
    private val context: Context,
    private var categories: List<Category>
): RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            LayoutCategoryItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Glide.with(holder.itemView.context)
            .load(categories[position].strCategoryThumb)
            .into(holder.binding.imgCategory)

        holder.binding.tvCategory.text = categories[position].strCategory

        holder.itemView.setOnClickListener {
            Intent(holder.itemView.context, CategoryMealActivity::class.java).also {
                it.putExtra(context.getString(R.string.category_name_extra), categories[position].strCategory)
                context.startActivity(it)
            }
        }

    }

    override fun getItemCount(): Int {
        return categories.size
    }

    inner class ViewHolder(val binding: LayoutCategoryItemBinding): RecyclerView.ViewHolder(binding.root)

}