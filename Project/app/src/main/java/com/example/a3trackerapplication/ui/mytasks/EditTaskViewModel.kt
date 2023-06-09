package com.example.a3trackerapplication.ui.mytasks

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.a3trackerapplication.MyApplication
import com.example.a3trackerapplication.models.EditTaskRequest
import com.example.a3trackerapplication.models.LoginResult
import com.example.a3trackerapplication.models.Task
import com.example.a3trackerapplication.repositories.TaskRepository
import kotlinx.coroutines.launch

class EditTaskViewModelFactory(
    private val repository: TaskRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EditTaskViewModel(repository) as T
    }
}
class EditTaskViewModel(private val repository: TaskRepository) : ViewModel() {
    var selectTask: MutableLiveData<Task> = MutableLiveData()
    var editTaskResult: MutableLiveData<String> = MutableLiveData()
    private var errorMessage: MutableLiveData<List<String>> = MutableLiveData()
    fun updateTask(request: EditTaskRequest) {
        viewModelScope.launch {
            try {
                val response = repository.updateTask(MyApplication.token,request)
                if (response?.isSuccessful == true) {
                    editTaskResult.value = response.body()
                } else if(response?.code() == 400){
                    editTaskResult.value = "INVALID CREDENTIALS"
                    val errorBody:String = response.errorBody()!!.string()
                    val list: List<String> = errorBody.substring(1, errorBody.length - 1).split(", ")
                    errorMessage.value = list
                }
                else {
                    editTaskResult.value = "OTHER ERROR"
                }
            } catch (e: Exception) {
                editTaskResult.value = e.message
            }
        }
    }

    fun getTask(taskId: Long){
        viewModelScope.launch {
            try {
                val response = repository.getTask(MyApplication.token,taskId)
                if (response?.isSuccessful == true) {
                    selectTask.value = response.body()
                }
            } catch (e: Exception) { }
        }
    }

}