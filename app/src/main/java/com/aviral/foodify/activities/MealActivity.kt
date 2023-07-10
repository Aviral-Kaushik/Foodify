package com.aviral.foodify.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.aviral.foodify.R
import com.aviral.foodify.databinding.ActivityMealBinding
import com.aviral.foodify.models.Meal
import com.aviral.foodify.viewModels.MealVIewModel
import com.bumptech.glide.Glide

class MealActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMealBinding
    private lateinit var mealViewModel: MealVIewModel

    private lateinit var mealId: String
    private lateinit var mealName: String
    private lateinit var mealThumb: String
    private lateinit var mealVideoUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mealViewModel = ViewModelProvider(this)[MealVIewModel::class.java]

        getMealInformationFromIntent()

        setupViews()

        mealViewModel.getMealByID(mealId)
        observeMealLiveData()

        binding.imgYoutube.setOnClickListener {
            if (mealVideoUrl.isNotEmpty()) {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(mealVideoUrl)))
            }
        }

    }

    private fun observeMealLiveData() {

        mealViewModel.observerMealLiveData().observe(this
        ) { value ->
            binding.tvCategory.text = "Category: ${value.strCategory}"
            binding.tvArea.text = "Area : ${value.strArea}"
            binding.tvContent.text = value.strInstructions

            mealVideoUrl = value.strYoutube

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