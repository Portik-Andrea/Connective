package com.example.a3trackerapplication.ui.mygroups

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.a3trackerapplication.MyApplication
import com.example.a3trackerapplication.models.Group
import com.example.a3trackerapplication.models.Task
import com.example.a3trackerapplication.models.UserType
import com.example.a3trackerapplication.repositories.GroupRepository
import com.example.a3trackerapplication.repositories.TaskRepository
import com.example.a3trackerapplication.ui.mytasks.EditTaskViewModel
import kotlinx.coroutines.launch

class GroupViewModelFactory(
    private val repository: GroupRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GroupViewModel(repository) as T
    }
}

class GroupViewModel (private val repository: GroupRepository) : ViewModel() {
    var groups = MutableLiveData<List<Group>>()

    fun getGroups() {
        viewModelScope.launch {
            try {
                val response = if(MyApplication.userType==UserType.MENTEE.toString()){
                                    repository.getMyGroups(MyApplication.token)
                               } else{
                                    repository.getGroups(MyApplication.token)
                               }
                if(response?.isSuccessful == true) {
                    groups.value = response.body()
                    Log.d("xxx", "GetGroups response ${response.body()}")
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