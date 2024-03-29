package com.example.a3trackerapplication.repositories

import com.example.a3trackerapplication.api.TrackerApi
import com.example.a3trackerapplication.models.ActivityModel
import com.example.a3trackerapplication.models.EditTaskRequest
import com.example.a3trackerapplication.models.NewTaskRequest
import com.example.a3trackerapplication.models.Task
import com.example.a3trackerapplication.models.User
import retrofit2.Response

class TaskRepository {
    suspend fun getAllTasks(token: String): Response<List<Task>>? {
        return TrackerApi.getApi()?.getAllTasks(token = token);
    }
    suspend fun getMyTasks(token: String): Response<List<Task>>? {
        return TrackerApi.getApi()?.getMyTasks(token = token);
    }

    suspend fun createTask(token: String,request: NewTaskRequest): Response<String>? {
        return TrackerApi.getApi()?.createTask(token = token,request=request);
    }

    suspend fun updateTask(token: String, request: EditTaskRequest): Response<String>?{
        return TrackerApi.getApi()?.updateTask(token = token,request=request);
    }

    suspend fun getTask(token: String, taskId: Long): Response<Task>?{
        return TrackerApi.getApi()?.getTask(token = token, taskId = taskId)
    }

    suspend fun getTaskActivities(token: String): Response<List<ActivityModel.TaskData>>? {
        return TrackerApi.getApi()?.getTaskActivities(token = token);
    }
}