package com.edaaoneerr.petcare

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.edaaoneerr.petcare.databinding.ActivityMainBinding
import com.edaaoneerr.petcare.viewmodel.LoginViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private var navController: NavController? = null

    val loginViewModel: LoginViewModel by viewModels()
    var isLoggedIn = false


    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        val sharedPreferences =
            this.getSharedPreferences("Authentication", Context.MODE_PRIVATE)

        isLoggedIn = sharedPreferences.getBoolean("Authentication", false)


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as? NavHostFragment
        navController = navHostFragment?.navController
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        if (!isLoggedIn) {
            /*supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, LoginFragment())
                .commit()*/
            val navHost =
                supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
            navHost.navController.navigate(R.id.loginFragment)
        }


        navController?.let {
            findViewById<NavigationView>(R.id.navigation_drawer)
                .setupWithNavController(it)
            setupBottomNavMenu(it)
        }

        val navHost =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        binding?.toolbar?.setNavigationOnClickListener {
            binding?.drawerLayout?.open()

        }
        binding?.toolbar?.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.messages -> {
                    navHost.navController.navigate(R.id.messageFragment)
                    true
                }
                R.id.notifications -> {
                    navHost.navController.navigate(R.id.notificationFragment)
                    true
                }

                else -> false
            }
        }
        binding?.bottomNavigation?.setOnItemSelectedListener {


            when (it.itemId) {
                R.id.home_menu -> {
                    navHost.navController.navigate(R.id.home_menu)

                }
                R.id.cart_menu -> {
                    navHost.navController.navigate(R.id.cart_menu)
                }
                R.id.animal_house_menu -> {
                    navHost.navController.navigate(R.id.animal_house_menu)

                }
                R.id.shop_menu -> {
                    navHost.navController.navigate(R.id.shop_menu)

                }
                R.id.pet_menu -> {
                    navHost.navController.navigate(R.id.pet_menu)
                }
            }
            false

        }
        navHost.navController.addOnDestinationChangedListener { _, destination, _ ->

            when (destination.id) {
                R.id.loginFragment, R.id.signUpFragment -> {
                    binding?.toolbar?.visibility = View.GONE
                    binding?.bottomNavigation?.visibility = View.GONE

                }

                R.id.pet_menu -> {
                    binding?.toolbar?.visibility = View.GONE
                    binding?.bottomNavigation?.visibility = View.VISIBLE
                }

                else -> {
                    binding?.toolbar?.visibility = View.VISIBLE
                    binding?.bottomNavigation?.visibility = View.VISIBLE

                }
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