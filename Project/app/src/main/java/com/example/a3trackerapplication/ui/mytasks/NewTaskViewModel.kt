package com.example.a3trackerapplication.ui.mytasks

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.a3trackerapplication.MyApplication
import com.example.a3trackerapplication.models.LoginRequest
import com.example.a3trackerapplication.models.LoginResult
import com.example.a3trackerapplication.models.NewTaskRequest
import com.example.a3trackerapplication.repositories.TaskRepository
import com.example.a3trackerapplication.repositories.UserRepository
import com.example.a3trackerapplication.ui.login.LoginViewModel
import kotlinx.coroutines.launch


class NewTaskViewModelFactory(
    private val repository: TaskRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewTaskViewModel(repository) as T
    }
}


class NewTaskViewModel(private val repository: TaskRepository) : ViewModel()  {

    var createTaskResult: MutableLiveData<String> = MutableLiveData()

    fun createTask(request: NewTaskRequest) {
        viewModelScope.launch {
            try {
                val response = repository.createTask(MyApplication.token,request)
                if (response?.isSuccessful == true) {
                    createTaskResult.value = "SUCCESS"
                    Log.d("xxx", "Login body" + response.body().toString())
                } else {
                    createTaskResult.value = "INVALID_CREDENTIALS"
                    Log.i("xxx", "Login invalid credentials " + response?.errorBody().toString()  )
                }
            } catch (e: Exception) {
                createTaskResult.value = "UNKNOWN_ERROR"
                Log.i("xxx","Login error" + e.toString())
            }
        }
    }
}