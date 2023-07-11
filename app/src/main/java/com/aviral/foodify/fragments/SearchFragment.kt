package com.aviral.foodify.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.aviral.foodify.activities.MainActivity
import com.aviral.foodify.adapters.FavoritesAdapter
import com.aviral.foodify.databinding.FragmentSearchBinding
import com.aviral.foodify.viewModels.MainViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSearch.setOnClickListener {
            searchMeals()
        }

        var searchJob: Job? = null

        binding.search.addTextChangedListener { searchQuery ->
            searchJob?.cancel()

            searchJob = lifecycleScope.launch {

                delay(500)
                viewModel.searchMeals(searchQuery.toString())
                observeSearchedMealLiveData()

            }
        }

    }

    private fun searchMeals() {

        if (binding.search.text.toString().isNotEmpty()) {
            viewModel.searchMeals(binding.search.text.toString())

            observeSearchedMealLiveData()
        } else {
            Snackbar.make(
                binding.searchLayout,
                "Please enter something to serach",
                Snackbar.LENGTH_SHORT
            ).show()
        }

    }

    private fun observeSearchedMealLiveData() {

        viewModel.observeSearchMealLiveData().observe(requireActivity()) {values ->

            binding.searchRecyclerView.apply {
                layoutManager = GridLayoutManager(
                    requireContext(), 2, GridLayoutManager.VERTICAL, false
                )

                val favoritesAdapter = FavoritesAdapter(context)
                favoritesAdapter.differ.submitList(values)

                adapter = favoritesAdapter
            }

        }

    }
}