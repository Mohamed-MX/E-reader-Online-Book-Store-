package com.example.e_readerbookstore.ui.settings

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import com.example.e_readerbookstore.MainActivity
import com.example.e_readerbookstore.R
import com.example.e_readerbookstore.ui.auth.LoginFragment

class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        val sharedPref = activity?.getSharedPreferences("EReaderPrefs", Context.MODE_PRIVATE)
        val username = sharedPref?.getString("USERNAME", "Guest")
        val isDarkMode = sharedPref?.getBoolean("DARK_MODE", false) ?: false

        val tvUsername = view.findViewById<TextView>(R.id.tvUsername)
        val switchDarkMode = view.findViewById<SwitchCompat>(R.id.switchDarkMode)
        val btnLogout = view.findViewById<Button>(R.id.btnLogout)

        tvUsername.text = "Username: $username"
        switchDarkMode.isChecked = isDarkMode

        switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            // Save bottom nav visibility state to SharedPreferences before theme change
            val mainActivity = activity as? MainActivity
            val isBottomNavVisible = mainActivity?.isBottomNavVisible() ?: true // Default to true if logged in
            val userId = sharedPref?.getInt("USER_ID", -1) ?: -1
            
            // If user is logged in, bottom nav should always be visible
            val shouldShowBottomNav = userId != -1 || isBottomNavVisible
            sharedPref?.edit()?.putBoolean("BOTTOM_NAV_VISIBLE", shouldShowBottomNav)?.apply()
            sharedPref?.edit()?.putBoolean("DARK_MODE", isChecked)?.apply()
            
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        btnLogout.setOnClickListener {
            sharedPref?.edit()?.clear()?.apply()
            (activity as? MainActivity)?.let {
                it.loadFragment(LoginFragment())
                it.setBottomNavVisibility(false)
            }
        }
    }
}
