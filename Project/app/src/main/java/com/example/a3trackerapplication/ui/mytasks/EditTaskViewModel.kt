package com.example.a3trackerapplication.ui.mytasks

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.a3trackerapplication.MyApplication
import com.example.a3trackerapplication.models.EditTaskRequest
import com.example.a3trackerapplication.models.LoginResult
import com.example.a3trackerapplication.repositories.UserRepository
import kotlinx.coroutines.launch

class EditTaskViewModelFactory(
    private val repository: UserRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EditTaskViewModel(repository) as T
    }
}
class EditTaskViewModel(val repository: UserRepository) : ViewModel() {
    var editTaskResult: MutableLiveData<LoginResult> = MutableLiveData()
    fun updateTask(request: EditTaskRequest) {
        viewModelScope.launch {
            try {
                val response = repository.updateTask(MyApplication.token,request)
                if (response?.isSuccessful == true) {
                    editTaskResult.value = LoginResult.SUCCESS
                    Log.d("xxx", "Login body" + response.body().toString())
                } else {
                    editTaskResult.value = LoginResult.INVALID_CREDENTIALS
                    Log.i("xxx", "Login invalid credentials " + response?.errorBody().toString()  )
                }
            } catch (e: Exception) {
                editTaskResult.value = LoginResult.UNKNOWN_ERROR
                Log.i("xxx", "Login error $e")
            }
        }
    }
}