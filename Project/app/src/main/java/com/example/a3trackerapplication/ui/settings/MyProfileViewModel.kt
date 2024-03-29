package com.example.a3trackerapplication.ui.settings

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.a3trackerapplication.MyApplication
import com.example.a3trackerapplication.models.UpdateUserRequest
import com.example.a3trackerapplication.models.User
import com.example.a3trackerapplication.repositories.UserRepository
import kotlinx.coroutines.launch

class MyProfileViewModelFactory(
    private val repository: UserRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MyProfileViewModel( repository) as T
    }
}


class MyProfileViewModel(private val repository: UserRepository): ViewModel() {
    var user = MutableLiveData<User>()
    var mentor = MutableLiveData<User>()
    var updateResult: MutableLiveData<Boolean> = MutableLiveData()

    fun myUser() {
        viewModelScope.launch {
            try {
                val response = repository.getMyUser(MyApplication.token)
                if (response?.isSuccessful == true) {
                    user.value = response.body()
                } else {
                }

            } catch (ex: Exception) {
            }
        }
    }
    fun updateMyProfile(request: UpdateUserRequest){
        viewModelScope.launch {
            try {
                val response = repository.updateUser(MyApplication.token,request)
                Log.d("xxx", "UpdateMy user response $response")
                if (response?.isSuccessful == true) {
                    Log.d("xxx", "UpdateMy user response ${response.body()}")
                    updateResult.value = response.body()
                } else {
                    Log.d("xxx", "UpdateMy user error response ${response?.errorBody()}")
                }

            } catch (ex: Exception) {
                Log.e("xxx", ex.message, ex)
            }
        }
    }

    fun selectMentor(mentorId:Long){
        viewModelScope.launch {
            try{
                val response = repository.selectMentor(MyApplication.token,mentorId)
                Log.d("xxx", "UpdateMy user response $response")
                if (response?.isSuccessful == true) {
                    Log.d("xxx", "UpdateMy user response ${response.body()}")
                    mentor.value = response.body()
                } else {
                    Log.d("xxx", "UpdateMy user error response ${response?.errorBody()}")
                }

            } catch (ex: Exception) {
                Log.e("xxx", ex.message, ex)
            }
        }
    }

}