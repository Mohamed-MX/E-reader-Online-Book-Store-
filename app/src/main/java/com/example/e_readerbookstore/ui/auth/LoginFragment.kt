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
import com.example.e_readerbookstore.ui.home.HomeFragment

class LoginFragment : Fragment() {

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dbHelper = DatabaseHelper(requireContext())

        val etUsername = view.findViewById<EditText>(R.id.etUsername)
        val etPassword = view.findViewById<EditText>(R.id.etPassword)
        val btnLogin = view.findViewById<Button>(R.id.btnLogin)
        val tvGoToSignUp = view.findViewById<TextView>(R.id.tvGoToSignUp)

        btnLogin.setOnClickListener {
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                val user = dbHelper.checkUser(username, password)
                if (user != null) {
                    Toast.makeText(context, getString(R.string.login_success), Toast.LENGTH_SHORT).show()
                    saveUserSession(user.id, user.username)
                    (activity as MainActivity).navigateToHome(HomeFragment())
                } else {
                    Toast.makeText(context, getString(R.string.login_failed), Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, getString(R.string.empty_fields), Toast.LENGTH_SHORT).show()
            }
        }

        tvGoToSignUp.setOnClickListener {
            (activity as MainActivity).loadFragment(SignUpFragment())
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
