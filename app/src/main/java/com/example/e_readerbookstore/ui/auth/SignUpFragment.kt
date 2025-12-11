package com.example.e_readerbookstore.ui.auth

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.e_readerbookstore.MainActivity
import com.example.e_readerbookstore.R
import com.example.e_readerbookstore.database.DatabaseHelper
import com.example.e_readerbookstore.model.User
import com.example.e_readerbookstore.ui.home.HomeFragment

class SignUpFragment : Fragment() {

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_signup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dbHelper = DatabaseHelper(requireContext())

        val etUsername = view.findViewById<EditText>(R.id.etUsernameSignUp)
        val etPassword = view.findViewById<EditText>(R.id.etPasswordSignUp)
        val btnSignUp = view.findViewById<Button>(R.id.btnSignUp)
        val tvGoToLogin = view.findViewById<TextView>(R.id.tvGoToLogin)

        btnSignUp.setOnClickListener {
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                val user = User(username = username, password = password)
                val id = dbHelper.addUser(user)
                if (id > -1) {
                    Toast.makeText(context, getString(R.string.signup_success), Toast.LENGTH_SHORT).show()
                    saveUserSession(id.toInt(), username)
                    (activity as MainActivity).navigateToHome(HomeFragment())
                } else {
                    Toast.makeText(context, getString(R.string.signup_failed), Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, getString(R.string.empty_fields), Toast.LENGTH_SHORT).show()
            }
        }

        tvGoToLogin.setOnClickListener {
            (activity as MainActivity).loadFragment(LoginFragment())
        }
    }
    
    private fun saveUserSession(userId: Int, username: String) {
        val sharedPref = activity?.getSharedPreferences("EReaderPrefs", Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putInt("USER_ID", userId)
            putString("USERNAME", username)
            apply()
        }
    }
}
