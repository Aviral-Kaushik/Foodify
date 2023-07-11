package com.aviral.foodify.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.GridLayoutManager
import com.aviral.foodify.R
import com.aviral.foodify.adapters.CategoryMealsAdapter
import com.aviral.foodify.databinding.ActivityCategoryMealBinding
import com.aviral.foodify.viewModels.CategoryViewModel

class CategoryMealActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoryMealBinding
    private lateinit var categoryViewModel: CategoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        categoryViewModel = ViewModelProvider(this)[CategoryViewModel::class.java]

        categoryViewModel.getMealsByCategory( intent.getStringExtra(getString(R.string.category_name))!!)
        observeCategoryMealsLiveData()

    }

    private fun observeCategoryMealsLiveData() {
        categoryViewModel.observeMealsByCategoryLiveData()
            .observe(this) { value ->

                binding.tvCategory.text = "${value.size}"

                binding.recyclerViewMeals.apply {
                    layoutManager = GridLayoutManager(
                        this@CategoryMealActivity, 2,
                        GridLayoutManager.VERTICAL, false
                    )

                    val categoryMealsAdapter = CategoryMealsAdapter(
                        this@CategoryMealActivity, value)

                    adapter = categoryMealsAdapter
                }

            }
    }
}