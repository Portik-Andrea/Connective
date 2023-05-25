package com.example.a3trackerapplication.ui.settings

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.a3trackerapplication.MyApplication
import com.example.a3trackerapplication.models.Task
import com.example.a3trackerapplication.models.User
import com.example.a3trackerapplication.repositories.UserRepository
import com.example.a3trackerapplication.ui.mytasks.MyTasksViewModel
import kotlinx.coroutines.launch

class SelectMentorViewModelFactory(
    private val repository: UserRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SelectMentorViewModel( repository) as T
    }
}

class SelectMentorViewModel(private val repository: UserRepository): ViewModel() {
    var allMentors = MutableLiveData<List<User>>()
    fun getAllMentors() {
        viewModelScope.launch {
            try {
                val response = repository.getAllMentors(MyApplication.token)
                if(response?.isSuccessful == true) {
                    allMentors.value = response.body()
                    Log.d("xxx", "GetMentors response ${response.body()}")
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