package com.example.a3trackerapplication.ui.login

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.a3trackerapplication.MyApplication
import com.example.a3trackerapplication.R
import com.example.a3trackerapplication.models.LoginRequest
import com.example.a3trackerapplication.models.LoginResult
import com.example.a3trackerapplication.repositories.UserRepository
import com.google.android.material.textfield.TextInputEditText
import org.mindrot.jbcrypt.BCrypt
import java.util.Base64


class LoginFragment : Fragment() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var emailEditText: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = LoginViewModelFactory(UserRepository())
        loginViewModel = ViewModelProvider(this, factory)[LoginViewModel::class.java]

        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if(!shouldInterceptBackPress()){
                    isEnabled = false
                    activity?.onBackPressed()
                }
            }
        })
    }

    fun shouldInterceptBackPress()=true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        emailEditText = view.findViewById(R.id.email)
        val passwordEditText: TextInputEditText = view.findViewById(R.id.password)
        val loginButton: Button = view.findViewById(R.id.loginButton)

        val prefs = requireActivity().getPreferences(Context.MODE_PRIVATE)
        if (!prefs.getString("email", "").equals("")) {
            emailEditText.setText(prefs.getString("email", ""))
        }

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                    this.requireContext(),
                    "Please, enter your email and password",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                val encodePassword = Base64.getEncoder().encodeToString(password.toByteArray())
                loginViewModel.login(LoginRequest(email, encodePassword))
            }
        }

        loginViewModel.loginResult.observe(viewLifecycleOwner) {
            // Save data to preferences
            if( it == LoginResult.INVALID_CREDENTIALS){
                Toast.makeText(
                    this.requireContext(),
                    "Invalid credentials",
                    Toast.LENGTH_LONG
                ).show()
            }
            if( it == LoginResult.UNKNOWN_ERROR){
                Toast.makeText(
                    this.requireContext(),
                    "UNKNOWN ERROR",
                    Toast.LENGTH_LONG
                ).show()
            }
            if ( it == LoginResult.SUCCESS ) {
                Toast.makeText(
                    this.requireContext(),
                    it.toString(),
                    Toast.LENGTH_LONG
                ).show()
                val preferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
                val edit = preferences.edit()
                edit.putString("token", MyApplication.token)
                edit.putString("userType",MyApplication.userType)
                edit.putString("email", emailEditText.text.toString())
                edit.apply()
                findNavController().navigate(R.id.action_loginFragment_to_activitiesFragment)
            }
        }
    }
}