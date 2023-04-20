package com.example.a3trackerapplication.ui.mytasks

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.a3trackerapplication.MyApplication
import com.example.a3trackerapplication.models.Task
import com.example.a3trackerapplication.repositories.UserRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

class MyTasksViewModelFactory(
private val repository: UserRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MyTasksViewModel( repository) as T
    }
}

class MyTasksViewModel(val repository: UserRepository): ViewModel() {
    var myTasks = MutableLiveData<List<Task>>()

    fun getTasks() {
        viewModelScope.launch {
            try {
                val response = repository.getTasks(MyApplication.token)
                if(response.isSuccessful) {
                    myTasks.value = response.body()
                    Log.d("xxx", "GetMy tasks response ${response.body()}")
                } else{
                    Log.i("xxx-uvm", response.message())
                }
            } catch (e: Exception) {
                Log.i("xxx", e.toString())
            }
        }
    }
}