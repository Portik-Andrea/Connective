package com.example.a3trackerapplication.ui.mygroups

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.a3trackerapplication.MyApplication
import com.example.a3trackerapplication.models.AddMember
import com.example.a3trackerapplication.models.Group
import com.example.a3trackerapplication.models.User
import com.example.a3trackerapplication.models.UserType
import com.example.a3trackerapplication.repositories.GroupRepository
import kotlinx.coroutines.launch


class GroupMembersViewModelFactory(
    private val repository: GroupRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GroupMembersViewModel(repository) as T
    }
}
class GroupMembersViewModel (private val repository: GroupRepository) : ViewModel() {
    val members = MutableLiveData<List<User>>()
    var errorMessage: String = ""
    var addNewMemberResponse = MutableLiveData<String>()
    fun getGroupMembers(groupId: Long) {
        viewModelScope.launch {
            try {
                val response = repository.getGroupMembers(MyApplication.token, groupId)
                if(response?.isSuccessful == true) {
                    members.value = response.body()
                } else{
                    if (response != null) {
                        errorMessage = "Failed to load members, please try again later"
                    }
                }
            } catch (e: Exception) {
                errorMessage = "UNKNOWN ERROR"
            }
        }
    }

    fun addMember(addMember: AddMember) {
        viewModelScope.launch {
            try {
                val response = repository.addNewMember(MyApplication.token, addMember)
                if(response?.isSuccessful == true) {
                    addNewMemberResponse.value = response.body()
                } else{
                    if (response != null) {
                        errorMessage = "Failed to add member, please try again later"
                    }
                }
            } catch (e: Exception) {
                errorMessage = "UNKNOWN ERROR"
            }
        }
    }

}