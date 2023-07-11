package com.aviral.foodify.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.aviral.foodify.R
import com.aviral.foodify.activities.MainActivity
import com.aviral.foodify.adapters.FavoritesAdapter
import com.aviral.foodify.databinding.FragmentFavouritesBinding
import com.aviral.foodify.models.Meal
import com.aviral.foodify.viewModels.MainViewModel
import com.google.android.material.snackbar.Snackbar


class FavouritesFragment : Fragment() {

    private lateinit var binding: FragmentFavouritesBinding
    private lateinit var viewModel: MainViewModel

    private lateinit var favoritesAdapter: FavoritesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentFavouritesBinding.inflate(layoutInflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeFavouriteMeals()

        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                val meal = favoritesAdapter.differ.currentList[viewHolder.adapterPosition]

                viewModel.deleteMeal(favoritesAdapter.differ.currentList[viewHolder.adapterPosition])

                Snackbar.make(
                    binding.rootLayout,
                    "Meal deleted from favourites",
                    Snackbar.LENGTH_SHORT
                ).setAction(
                    "Undo"
                ) {
                    viewModel.insertMeal(meal)
                }.show()
            }

        }

        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.recyclerViewFavourite)
    }

    private fun observeFavouriteMeals() {

        viewModel.observeFavouriteMeal().observe(requireActivity()
        ) { value ->
            binding.recyclerViewFavourite.apply {
                layoutManager = GridLayoutManager(
                    requireContext(), 2, GridLayoutManager.VERTICAL, false
                )

                favoritesAdapter = FavoritesAdapter(requireContext())

                favoritesAdapter.differ.submitList(value)

                adapter = favoritesAdapter
            }
        }

    }


}