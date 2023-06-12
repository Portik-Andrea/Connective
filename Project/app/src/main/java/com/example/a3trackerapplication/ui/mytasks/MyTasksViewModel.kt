package com.example.a3trackerapplication.ui.mytasks

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.a3trackerapplication.MyApplication
import com.example.a3trackerapplication.models.ActivityModel
import com.example.a3trackerapplication.models.Task
import com.example.a3trackerapplication.repositories.TaskRepository
import kotlinx.coroutines.launch

class MyTasksViewModelFactory(
private val repository: TaskRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MyTasksViewModel( repository) as T
    }
}

class MyTasksViewModel(private val repository: TaskRepository): ViewModel() {
    var myTasks = MutableLiveData<List<Task>>()
    var tasks = MutableLiveData<List<ActivityModel.TaskData>>()

    fun getTasks() {
        viewModelScope.launch {
            try {
                val response = repository.getMyTasks(MyApplication.token)
                if(response?.isSuccessful == true) {
                    myTasks.value = response.body()
                    Log.d("xxx", "GetMy tasks response ${response.body()}")
                } else{
                    if (response != null) {
                        Log.i("xxx-uvm", response.message())
                    }
                }
            } catch (e: Exception) {
                Log.i("xxx", e.toString())
            }
        }
    }

    fun getTaskActivities() {
        viewModelScope.launch {
            try {
                val response = repository.getTaskActivities(MyApplication.token)
                if(response?.isSuccessful == true) {
                    tasks.value = response.body()
                    Log.d("xxx", "GetMy tasks response ${response.body()}")
                } else{
                    if (response != null) {
                        Log.i("xxx-uvm", response.message())
                    }
                }
            } catch (e: Exception) {
                Log.i("xxx", e.toString())
            }
        }
    }
}