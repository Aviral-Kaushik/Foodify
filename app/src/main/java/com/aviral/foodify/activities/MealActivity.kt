package com.aviral.foodify.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.aviral.foodify.R
import com.aviral.foodify.databinding.ActivityMealBinding
import com.aviral.foodify.dbUtils.MealDatabase
import com.aviral.foodify.models.Meal
import com.aviral.foodify.viewModels.MealVIewModel
import com.aviral.foodify.viewModels.MealViewModelFactory
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar

class MealActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMealBinding
    private lateinit var mealViewModel: MealVIewModel

    private lateinit var mealId: String
    private lateinit var mealName: String
    private lateinit var mealThumb: String
    private lateinit var mealVideoUrl: String

    private var meal: Meal? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mealDatabase = MealDatabase.getInstance(context = this)
        val factory = MealViewModelFactory(mealDatabase)

        mealViewModel = ViewModelProvider(this, factory)[MealVIewModel::class.java]

        getMealInformationFromIntent()

        setupViews()

        mealViewModel.getMealByID(mealId)
        observeMealLiveData()

        binding.imgYoutube.setOnClickListener {
            if (mealVideoUrl.isNotEmpty()) {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(mealVideoUrl)))
            }
        }

        binding.btnSave.setOnClickListener {
            meal?.let {
                mealViewModel.insertMeal(it)
                Snackbar.make(
                    binding.rootLayout,
                    "Added to your favourites",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

    }

    private fun observeMealLiveData() {

        mealViewModel.observerMealLiveData().observe(
            this
        ) { value ->

            meal = value

            binding.tvCategory.text = "Category: ${value.strCategory}"
            binding.tvArea.text = "Area : ${value.strArea}"
            binding.tvContent.text = value.strInstructions

            mealVideoUrl = value.strYoutube.toString()

            toggleVisibilityOfView()
        }
    }

    private fun setupViews() {

        Glide.with(this)
            .load(mealThumb)
            .into(binding.imgMealDetail)

        binding.collapsingToolbarLayout.title = mealName
        binding.collapsingToolbarLayout.setCollapsedTitleTextColor(getColor(R.color.white))
        binding.collapsingToolbarLayout.setExpandedTitleColor(getColor(R.color.black))


    }

    private fun getMealInformationFromIntent() {

        mealId = intent.getStringExtra(getString(R.string.meal_id)).toString()
        mealName = intent.getStringExtra(getString(R.string.meal_name)).toString()
        mealThumb = intent.getStringExtra(getString(R.string.meal_thumb)).toString()

    }

    private fun toggleVisibilityOfView() {
        binding.progressBar.visibility = View.INVISIBLE
        binding.btnSave.visibility = View.VISIBLE
        binding.tvInstructions.visibility = View.VISIBLE
        binding.tvArea.visibility = View.VISIBLE
        binding.tvContent.visibility = View.VISIBLE
        binding.tvCategory.visibility = View.VISIBLE
    }
}