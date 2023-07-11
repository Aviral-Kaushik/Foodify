package com.aviral.foodify.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.aviral.foodify.activities.MainActivity
import com.aviral.foodify.adapters.CategoryAdapter
import com.aviral.foodify.databinding.FragmentCategoriesBinding
import com.aviral.foodify.viewModels.MainViewModel


class CategoriesFragment : Fragment() {

    private lateinit var binding: FragmentCategoriesBinding
    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoriesBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeCategoryLiveData()
    }

    private fun observeCategoryLiveData() {

        viewModel.observeCategoryLiveData().observe(requireActivity()
        ) { value ->
            binding.recyclerViewCategory.apply {
                layoutManager = GridLayoutManager(
                    requireContext(), 3, GridLayoutManager.VERTICAL, false
                )

                val categoryAdapter = CategoryAdapter(value)

                adapter = categoryAdapter
            }
        }

    }


}