package com.example.a3trackerapplication.repositories

import com.example.a3trackerapplication.api.TrackerApi
import com.example.a3trackerapplication.models.EditTaskRequest
import com.example.a3trackerapplication.models.NewTaskRequest
import com.example.a3trackerapplication.models.Task
import com.example.a3trackerapplication.models.User
import retrofit2.Response

class TaskRepository {
    suspend fun getAllTasks(token: String): Response<List<Task>>? {
        return TrackerApi.getApi()?.getAllTasks(token = token);
    }

    suspend fun createTask(token: String,request: NewTaskRequest): Response<String>? {
        return TrackerApi.getApi()?.createTask(token = token,request=request);
    }

    suspend fun updateTask(token: String, request: EditTaskRequest): Response<String>?{
        return TrackerApi.getApi()?.updateTask(token = token,request=request);
    }

}