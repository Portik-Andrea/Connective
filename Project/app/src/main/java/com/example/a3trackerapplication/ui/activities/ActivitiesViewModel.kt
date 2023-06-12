package com.example.a3trackerapplication.ui.activities

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.a3trackerapplication.MyApplication
import com.example.a3trackerapplication.models.ActivityModel
import com.example.a3trackerapplication.repositories.UserRepository
import com.example.a3trackerapplication.util.UserListViewModel
import kotlinx.coroutines.launch

class ActivitiesViewModelFactory(
    private val repository: UserRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ActivitiesViewModel( repository) as T
    }
}

class ActivitiesViewModel(private val repository: UserRepository) : ViewModel() {
    val users = MutableLiveData<List<ActivityModel.UserData>>()
    fun getUserActivities() {
        viewModelScope.launch {
            try {
                val response = repository.getUserActivities(MyApplication.token)
                if (response?.isSuccessful == true) {
                    users.value = response.body()!!
                } else {
                    if (response != null) {
                    }
                }
            } catch (e: Exception) {
            }
        }
    }
}