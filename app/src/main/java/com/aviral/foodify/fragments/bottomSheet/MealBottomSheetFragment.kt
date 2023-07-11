package com.aviral.foodify.fragments.bottomSheet

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aviral.foodify.R
import com.aviral.foodify.activities.MainActivity
import com.aviral.foodify.activities.MealActivity
import com.aviral.foodify.databinding.FragmentMealBottomSheetBinding
import com.aviral.foodify.viewModels.MainViewModel
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


private const val MEAL_ID = "param1"

class MealBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentMealBottomSheetBinding

    private lateinit var viewModel: MainViewModel

    private var mealId: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mealId = it.getString(MEAL_ID)
        }

        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMealBottomSheetBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mealId?.let {
            viewModel.getMealByID(mealId!!)
            observeMealData()
        }

    }

    private fun observeMealData() {

        viewModel.observerMealLiveData().observe(requireActivity()) { value ->

            Glide.with(requireContext())
                .load(value.strMealThumb)
                .into(binding.imgCategory)

            binding.tvMealNameInBottomSheet.text = value.strMeal
            binding.tvMealCountry.text = value.strArea
            binding.tvMealCategory.text = value.strCategory

            binding.rootLayout.setOnClickListener {
                Intent(requireActivity(), MealActivity::class.java).also {
                    it.putExtra(getString(R.string.meal_id), value.idMeal)
                    it.putExtra(getString(R.string.meal_name), value.strMeal)
                    it.putExtra(getString(R.string.meal_thumb), value.strMealThumb)
                    startActivity(it)
                }
            }

        }

    }

    companion  object {
        @JvmStatic
        fun newInstance(param1: String) =
            MealBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putString(MEAL_ID, param1)
                }
            }
    }
}