package com.aviral.foodify.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.aviral.foodify.R
import com.aviral.foodify.activities.MainActivity
import com.aviral.foodify.activities.MealActivity
import com.aviral.foodify.adapters.CategoryAdapter
import com.aviral.foodify.adapters.PopularItemsAdapter
import com.aviral.foodify.databinding.FragmentHomeBinding
import com.aviral.foodify.models.Meal
import com.aviral.foodify.viewModels.MainViewModel
import com.bumptech.glide.Glide

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: MainViewModel

    private lateinit var randomMeal: Meal

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
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

        viewModel.getRandomMeal()
        observerRandomMeal()

        viewModel.getPopularMeals()
        observePopularMeals()

        viewModel.getCategoryList()
        observeCategoryLiveData()

        binding.imgRandomMeal.setOnClickListener {
            Intent(requireActivity(), MealActivity::class.java).also {
                it.putExtra(getString(R.string.meal_id), randomMeal.idMeal)
                it.putExtra(getString(R.string.meal_name), randomMeal.strMeal)
                it.putExtra(getString(R.string.meal_thumb), randomMeal.strMealThumb)
                startActivity(it)
            }
        }

        binding.ivSearch.setOnClickListener {
            findNavController().navigate(R.id.searchFragment)
        }


    }

    private fun observeCategoryLiveData() {
        viewModel.observeCategoryLiveData()
            .observe(requireActivity()) { value ->

                binding.recyclerViewCategory.apply {
                    layoutManager = GridLayoutManager(
                        requireContext(), 2, GridLayoutManager.VERTICAL, false
                    )

                    val categoryAdapter = CategoryAdapter(requireContext(), value)

                    adapter = categoryAdapter
                }

            }
    }

    private fun observePopularMeals() {
        viewModel.observePopularMealsLiveData()
            .observe(
                requireActivity()
            ) { value ->
                binding.recyclerViewMealsPopular.apply {
                    layoutManager = LinearLayoutManager(
                        requireContext(),
                        LinearLayoutManager.HORIZONTAL, false
                    )

                    val popularAdapter =
                        PopularItemsAdapter(requireContext(), value, childFragmentManager)

                    adapter = popularAdapter
                }
            }
    }

    private fun observerRandomMeal() {
        viewModel.observeRandomMealLiveData()
            .observe(
                requireActivity()
            ) { value ->
                Glide.with(this@HomeFragment)
                    .load(value.strMealThumb)
                    .into(binding.imgRandomMeal)

                this.randomMeal = value
            }
    }


}