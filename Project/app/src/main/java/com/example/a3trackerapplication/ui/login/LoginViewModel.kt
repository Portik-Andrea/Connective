package com.example.a3trackerapplication.ui.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.a3trackerapplication.MyApplication
import com.example.a3trackerapplication.models.LoginRequest
import com.example.a3trackerapplication.models.LoginResult
import com.example.a3trackerapplication.repositories.UserRepository
import kotlinx.coroutines.launch

class LoginViewModelFactory(
    private val repository: UserRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel(repository) as T
    }
}

class LoginViewModel(private val repository: UserRepository) : ViewModel() {

    var loginResult: MutableLiveData<LoginResult> = MutableLiveData()
    fun login(request: LoginRequest) {
        viewModelScope.launch {
            try {
                val response = repository.login(request)
                if (response.isSuccessful) {
                    MyApplication.token = response.body()!!.token
                    MyApplication.userType = response.body()!!.userType
                    loginResult.value = LoginResult.SUCCESS
                } else {
                    loginResult.value = LoginResult.INVALID_CREDENTIALS
                }
            } catch (e: Exception) {
                loginResult.value = LoginResult.UNKNOWN_ERROR
            }
        }
    }
}