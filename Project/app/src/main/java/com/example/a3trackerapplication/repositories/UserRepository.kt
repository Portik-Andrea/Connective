package com.example.a3trackerapplication.repositories

import com.example.a3trackerapplication.api.RetrofitInstance
import com.example.a3trackerapplication.api.TrackerApi
import com.example.a3trackerapplication.models.*
import retrofit2.Response

class UserRepository {
    suspend fun login(request: LoginRequest): Response<LoginResponse>? {
        return TrackerApi.getApi()?.login(request = request);
    }

    suspend fun getUsers(token: String): Response<List<User>>? {
        return TrackerApi.getApi()?.getUsers(token = token);
    }

    suspend fun getMyUser(token: String): Response<User>? {
        return TrackerApi.getApi()?.getMyUser(token = token);
    }

    suspend fun getTasks(token: String): Response<List<Task>>? {
        return TrackerApi.getApi()?.getTasks(token = token);
    }

    suspend fun createTask(token: String,request: NewTaskRequest): Response<String>? {
        return TrackerApi.getApi()?.createTask(token = token,request=request);
    }

    suspend fun updateTask(token: String, request: EditTaskRequest): Response<String>?{
        return TrackerApi.getApi()?.updateTask(token = token,request=request);
    }
}