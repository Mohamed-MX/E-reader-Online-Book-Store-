package com.example.e_readerbookstore

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.e_readerbookstore.ui.auth.LoginFragment
import com.example.e_readerbookstore.ui.cart.CartFragment
import com.example.e_readerbookstore.ui.home.HomeFragment
import com.example.e_readerbookstore.ui.settings.SettingsFragment

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        // Initialize dark mode from preferences
        val sharedPref = getSharedPreferences("EReaderPrefs", Context.MODE_PRIVATE)
        val isDarkMode = sharedPref.getBoolean("DARK_MODE", false)
        AppCompatDelegate.setDefaultNightMode(
            if (isDarkMode) AppCompatDelegate.MODE_NIGHT_YES 
            else AppCompatDelegate.MODE_NIGHT_NO
        )
        
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNav = findViewById(R.id.bottom_navigation)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    loadFragment(HomeFragment())
                    true
                }
                R.id.nav_cart -> {
                    loadFragment(CartFragment())
                    true
                }
                R.id.nav_settings -> {
                    loadFragment(SettingsFragment())
                    true
                }
                else -> false
            }
        }

        // Restore bottom nav visibility state after activity recreation
        val userId = sharedPref.getInt("USER_ID", -1)
        val savedBottomNavVisible = sharedPref.getBoolean("BOTTOM_NAV_VISIBLE", false)
        
        // If user is logged in, bottom nav should always be visible
        // Otherwise, use saved state (for theme changes)
        if (userId != -1) {
            setBottomNavVisibility(true)
        } else {
            setBottomNavVisibility(savedBottomNavVisible)
        }

        if (savedInstanceState == null) {
            loadFragment(LoginFragment())
        }
    }

    override fun onResume() {
        super.onResume()
        // Ensure bottom nav is visible if user is logged in
        val sharedPref = getSharedPreferences("EReaderPrefs", Context.MODE_PRIVATE)
        val userId = sharedPref.getInt("USER_ID", -1)
        if (userId != -1) {
            setBottomNavVisibility(true)
        }
    }

    fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    fun navigateToHome(fragment: Fragment) {
        loadFragment(fragment)
        setBottomNavVisibility(true)
        bottomNav.selectedItemId = R.id.nav_home
    }

    fun setBottomNavVisibility(visible: Boolean) {
        bottomNav.visibility = if (visible) View.VISIBLE else View.GONE
        // Save state to SharedPreferences
        val sharedPref = getSharedPreferences("EReaderPrefs", Context.MODE_PRIVATE)
        sharedPref.edit().putBoolean("BOTTOM_NAV_VISIBLE", visible).apply()
    }
    
    fun isBottomNavVisible(): Boolean {
        return bottomNav.visibility == View.VISIBLE
    }
}
