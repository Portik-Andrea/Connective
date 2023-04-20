package com.example.a3trackerapplication.repositories

import com.example.a3trackerapplication.api.RetrofitInstance
import com.example.a3trackerapplication.models.*
import retrofit2.Response

class UserRepository {
    suspend fun login(request: LoginRequest): Response<LoginResponse> {
        return RetrofitInstance.api.login(request)
    }

    suspend fun getUsers(token: String): Response<List<User>> {
        return RetrofitInstance.api.getUsers(token)
    }

    suspend fun getMyUser(token: String): Response<User> {
        return RetrofitInstance.api.getMyUser(token)
    }

    suspend fun getTasks(token: String): Response<List<Task>> {
        return RetrofitInstance.api.getTasks(token)
    }

    suspend fun createTask(token: String,request: NewTaskRequest): Response<String> {
        return RetrofitInstance.api.createTask(token,request)
    }

    suspend fun updateTask(token: String, request: EditTaskRequest): Response<String> {
        return RetrofitInstance.api.updateTask(token, request)
    }
}