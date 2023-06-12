package com.example.a3trackerapplication.repositories

import com.example.a3trackerapplication.api.TrackerApi
import com.example.a3trackerapplication.models.ActivityModel
import com.example.a3trackerapplication.models.AddMember
import com.example.a3trackerapplication.models.Group
import com.example.a3trackerapplication.models.User
import retrofit2.Response

class GroupRepository {
    suspend fun getGroups(token: String): Response<List<Group>>? {
        return TrackerApi.getApi()?.getGroups(token = token);
    }
    suspend fun getMyGroups(token: String): Response<List<Group>>? {
        return TrackerApi.getApi()?.getMyGroups(token = token);
    }
    suspend fun getGroupMembers(token: String, groupId: Long): Response<List<User>>? {
        return TrackerApi.getApi()?.getGroupMembers(token = token,groupId = groupId);
    }

    suspend fun addNewMember(token: String, addMember: AddMember): Response<String>? {
        return TrackerApi.getApi()?.addNewMember(token = token, request = addMember);
    }

    suspend fun getGroupActivities(token: String): Response<List<ActivityModel.GroupData>>? {
        return TrackerApi.getApi()?.getGroupActivities(token = token);
    }
}