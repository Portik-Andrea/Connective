package com.example.a3trackerapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
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
    private lateinit var appNameTextView: TextView
    private lateinit var taskManagerTextView: TextView
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
        appNameTextView = this.findViewById(R.id.appNameTextView)
        taskManagerTextView = this.findViewById(R.id.taskManageTextView)
        top = AnimationUtils.loadAnimation(this,R.anim.top)

        appNameTextView.startAnimation(top)
        taskManagerTextView.startAnimation(top)
    }
    /*private fun getMyUser():Boolean{
        var tokenIsValid: Boolean = false
        lifecycleScope.launch{
            try {
                val response = UserRepository().getMyUser(MyApplication.token)
                Log.d("xxx", "GetMy user response $response")
                if (response?.isSuccessful == true) {
                    Log.d("xxx", "GetMy user token ${response.body()}")
                    tokenIsValid=true
                    response.body()
                } else {
                    Log.d("xxx", "GetMy user token error response ${response?.errorBody()}")
                }

            } catch (ex: Exception) {
                Log.e("xxx", ex.message, ex)
            }
        }

        return tokenIsValid
    }*/
}