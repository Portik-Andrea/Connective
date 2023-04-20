package com.example.a3trackerapplication.ui.settings

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.a3trackerapplication.R

class SettingsFragment : Fragment() {
    private lateinit var viewProfileTextView:TextView
    private lateinit var logoutButtom: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.apply {
            initViewItems(this)
            registerListeners()
        }
    }

    private fun initViewItems(view: View){
        viewProfileTextView = view.findViewById(R.id.viewProfileTextView)
        logoutButtom = view.findViewById(R.id.logoutButton)
    }

    private fun registerListeners(){
        viewProfileTextView.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_myProfileFragment)
        }
        logoutButtom.setOnClickListener {
            val preferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
            val editor = preferences.edit()
            editor.clear()
            editor.apply()
            findNavController().navigate(R.id.action_settingsFragment_to_loginFragment)
        }
    }

}