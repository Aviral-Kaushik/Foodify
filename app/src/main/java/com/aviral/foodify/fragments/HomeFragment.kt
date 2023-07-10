package com.aviral.foodify.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.aviral.foodify.R
import com.aviral.foodify.activities.MealActivity
import com.aviral.foodify.databinding.FragmentHomeBinding
import com.aviral.foodify.models.Meal
import com.aviral.foodify.viewModels.HomeViewModel
import com.bumptech.glide.Glide

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel

    private lateinit var randomMeal: Meal

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel.getRandomMeal()
        observerRandomMeal()

        binding.imgRandomMeal.setOnClickListener {
            Intent(requireActivity(), MealActivity::class.java).also {
                it.putExtra(getString(R.string.meal_id), randomMeal.idMeal)
                it.putExtra(getString(R.string.meal_name), randomMeal.strMeal)
                it.putExtra(getString(R.string.meal_thumb), randomMeal.strMealThumb)
                startActivity(it)
            }
        }


    }

    private fun observerRandomMeal() {
        homeViewModel.observeRandomMealLiveData()
            .observe(viewLifecycleOwner
            ) { value ->
                Glide.with(this@HomeFragment)
                    .load(value.strMealThumb)
                    .into(binding.imgRandomMeal)

                this.randomMeal = value
            }
    }


}