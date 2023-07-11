package com.aviral.foodify.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.aviral.foodify.R
import com.aviral.foodify.databinding.ActivityMainBinding
import com.aviral.foodify.dbUtils.MealDatabase
import com.aviral.foodify.viewModels.MainViewModel
import com.aviral.foodify.viewModels.MainViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    val viewModel: MainViewModel by lazy {
        val mealDatabase = MealDatabase.getInstance(this)
        val factory = MainViewModelFactory(mealDatabase)

        ViewModelProvider(this, factory)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = Navigation.findNavController(this, R.id.host_fragment)

        NavigationUI.setupWithNavController(binding.bottomNav, navController)

    }
}