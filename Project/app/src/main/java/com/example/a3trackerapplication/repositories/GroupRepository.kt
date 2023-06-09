package com.example.a3trackerapplication.repositories

import com.example.a3trackerapplication.api.TrackerApi
import com.example.a3trackerapplication.models.Group
import com.example.a3trackerapplication.models.User
import retrofit2.Response

class GroupRepository {
    suspend fun getGroups(token: String): Response<List<Group>>? {
        return TrackerApi.getApi()?.getGroups(token = token);
    }
}