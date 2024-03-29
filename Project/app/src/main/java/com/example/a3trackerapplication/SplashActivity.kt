package com.example.a3trackerapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.a3trackerapplication.models.User
import com.example.a3trackerapplication.repositories.UserRepository
import com.example.a3trackerapplication.ui.settings.MyProfileViewModel
import kotlinx.coroutines.launch
import okhttp3.internal.platform.android.ConscryptSocketAdapter.Companion.factory


@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private var SPLASH_SCREEN = 2500
    private lateinit var appNameImageView: ImageView
    private lateinit var top: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        initViewItems()
        //val tokenIsValid= this.getMyUser()
        val homeIntent= Intent(this@SplashActivity,MainActivity::class.java)
        //homeIntent.putExtra("message_key",tokenIsValid)

        Handler().postDelayed({
            startActivity((homeIntent))
            finish()
        },SPLASH_SCREEN.toLong())

    }
    private fun initViewItems() {
        appNameImageView = this.findViewById(R.id.appNameImageView)
        top = AnimationUtils.loadAnimation(this,R.anim.top)
        appNameImageView.startAnimation(top)
    }
    private fun getMyUser():Boolean{
        var tokenIsValid: Boolean = false
        lifecycleScope.launch{
            try {
                val response = UserRepository().getMyUser(MyApplication.token)
                if (response?.isSuccessful == true) {
                    tokenIsValid=true
                    response.body()
                } else {
                }

            } catch (ex: Exception) {
            }
        }

        return tokenIsValid
    }
}