package com.example.a3trackerapplication.ui.mygroups

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.a3trackerapplication.MyApplication
import com.example.a3trackerapplication.models.ActivityModel
import com.example.a3trackerapplication.models.Group
import com.example.a3trackerapplication.models.UserType
import com.example.a3trackerapplication.repositories.GroupRepository
import kotlinx.coroutines.launch

class GroupViewModelFactory(
    private val repository: GroupRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GroupViewModel(repository) as T
    }
}

class GroupViewModel (private val repository: GroupRepository) : ViewModel() {
    var myGroups = MutableLiveData<List<Group>>()
    var groups = MutableLiveData<List<ActivityModel.GroupData>>()
    fun getGroups() {
        viewModelScope.launch {
            try {
                val response = if(MyApplication.userType==UserType.MENTEE.toString()){
                                    repository.getMyGroups(MyApplication.token)
                               } else{
                                    repository.getGroups(MyApplication.token)
                               }
                if(response?.isSuccessful == true) {
                    myGroups.value = response.body()
                } else{
                    if (response != null) {
                    }
                }
            } catch (e: Exception) {
            }
        }
    }

    fun getGroupActivities() {
        viewModelScope.launch {
            try {
                val response = repository.getGroupActivities(MyApplication.token)
                if(response?.isSuccessful == true) {
                    groups.value = response.body()
                } else{
                    if (response != null) {
                    }
                }
            } catch (e: Exception) {
            }
        }
    }
}