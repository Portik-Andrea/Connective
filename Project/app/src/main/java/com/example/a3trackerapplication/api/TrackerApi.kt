package com.example.a3trackerapplication.api

import com.example.a3trackerapplication.models.*
import com.example.a3trackerapplication.util.Constants
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface TrackerApi {
    @GET("public/test")
    suspend fun test(): Response<String>

    @POST(Constants.LOGIN_URL)
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @GET(Constants.GET_USERS_URL)
    suspend fun getUsers(@Header("token") token: String): Response<List<User>>

    @GET("users/myuser")
    suspend fun getMyUser(@Header("token") token: String): Response<User>

    @POST("users/updateuser")
    suspend fun updateUser(@Header("token") token: String,@Body request: UpdateUserRequest): Response<Boolean>

    @GET("/task/getTasks")
    suspend fun getTasks(@Header("token") token: String): Response<List<Task>>

    @POST("/task/create")
    suspend fun createTask(@Header("token") token: String, @Body request: NewTaskRequest): Response<String>

    @POST("/task/update")
    suspend fun updateTask(@Header("token") token: String, @Body request: EditTaskRequest): Response<String>

    companion object {
        fun getApi(): TrackerApi? {
            return RetrofitInstance.client?.create(TrackerApi::class.java)
        }
    }
}