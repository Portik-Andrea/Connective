package com.example.a3trackerapplication

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.a3trackerapplication.models.User
import com.example.a3trackerapplication.repositories.UserRepository
import com.example.a3trackerapplication.util.UserListViewModel
import com.example.a3trackerapplication.util.UserListViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent = intent
        val tokenIsValid = intent.getStringExtra("message_key")
        if(tokenIsValid=="true"){
            Navigation.findNavController(this,R.id.myNavHostFragment).navigate(R.id.activitiesFragment)
        }

        //menu navigation
        val navController = findNavController( R.id.myNavHostFragment)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        bottomNav.setOnNavigationItemSelectedListener{ it ->
            when (it.itemId) {
                R.id.activitiesFragment -> {
                    Navigation.findNavController(this,R.id.myNavHostFragment).navigate(R.id.activitiesFragment)
                }
                R.id.myTasksFragment -> {
                    Navigation.findNavController(this,R.id.myNavHostFragment).navigate(R.id.myTasksFragment)
                }
                R.id.myGroupsFragment -> {
                    Navigation.findNavController(this,R.id.myNavHostFragment).navigate(R.id.myGroupsFragment)
                }
                R.id.settingsFragment ->{
                    Navigation.findNavController(this,R.id.myNavHostFragment).navigate(R.id.settingsFragment)
                }
                else -> super.onOptionsItemSelected(it)
            }
            it.isChecked=true
            true
        }

        // menu hide
        navController.addOnDestinationChangedListener{_,nd:NavDestination,_ ->
            if (nd.id == R.id.loginFragment) {
                bottomNav.visibility = View.GONE
                toolbar.visibility = View.GONE
            } else {
                bottomNav.visibility = View.VISIBLE
                toolbar.visibility = View.VISIBLE
            }
        }
    }
}