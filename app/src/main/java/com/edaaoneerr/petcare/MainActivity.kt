package com.edaaoneerr.petcare

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.edaaoneerr.petcare.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        setupBottomNavMenu(navController)


        findViewById<NavigationView>(R.id.navigation_drawer)
            .setupWithNavController(navController)

        binding.topAppBar.setNavigationOnClickListener {
            binding.drawerLayout.open()

        }

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.messages -> {
                    navController.navigate(R.id.messageFragment)
                    true
                }
                R.id.notifications -> {
                    navController.navigate(R.id.notificationFragment)
                    true
                }

                else -> false
            }
        }


        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home_menu -> {
                    navController.navigate(R.id.home_menu)
                }
                R.id.services_menu -> {
                    navController.navigate(R.id.services_menu)
                }
                R.id.cart_menu -> {
                    navController.navigate(R.id.cart_menu)

                }
                R.id.pet_menu -> {
                    navController.navigate(R.id.pet_menu)
                }
            }
            false

        }


        navController.addOnDestinationChangedListener { _, destination, _ ->

            if (destination.id == R.id.loginFragment || destination.id == R.id.signUpFragment) {
                binding.appBarLayout.visibility = View.GONE
                binding.bottomNavigation.visibility = View.GONE

            } else {
                binding.appBarLayout.visibility = View.GONE
                binding.bottomNavigation.visibility = View.VISIBLE

            }

        }


    }


    private fun setupBottomNavMenu(navController: NavController) {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav?.setupWithNavController(navController)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(findNavController(R.id.fragmentContainerView)) || super.onOptionsItemSelected(
            item
        )
    }


}