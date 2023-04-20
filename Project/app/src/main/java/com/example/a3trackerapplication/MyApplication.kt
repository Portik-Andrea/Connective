package com.example.a3trackerapplication

import android.app.Application
import com.example.a3trackerapplication.models.Task
import com.example.a3trackerapplication.models.User

class MyApplication : Application() {
    companion object {
        var token: String = ""
        var deadline: Long = 0L
        var email: String = ""
        var taskPosition: Int = -1
    }
}