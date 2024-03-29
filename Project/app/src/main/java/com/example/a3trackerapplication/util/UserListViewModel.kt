package com.example.a3trackerapplication.util

import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.a3trackerapplication.MyApplication
import com.example.a3trackerapplication.models.ActivityModel
import com.example.a3trackerapplication.models.User
import com.example.a3trackerapplication.repositories.UserRepository
import kotlinx.coroutines.launch

class UserListViewModelFactory(
    private val repository: UserRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserListViewModel( repository) as T
    }
}

class UserListViewModel(private val repository: UserRepository) : ViewModel() {
    var userList = MutableLiveData<List<User>>()

    fun readUsers() {
        viewModelScope.launch {
            try {
                val response = repository.getUsers(MyApplication.token)
                if (response?.isSuccessful == true) {
                    userList.value = response.body()!!
                } else {
                    if (response != null) {
                    }
                }
            } catch (e: Exception) {
            }
        }
    }

}
