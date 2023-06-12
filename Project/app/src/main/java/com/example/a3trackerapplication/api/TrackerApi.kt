package com.example.a3trackerapplication.api

import com.example.a3trackerapplication.models.*
import com.example.a3trackerapplication.util.Constants
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface TrackerApi {
    @POST(Constants.LOGIN_URL)
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @GET("users/getAllUsers")
    suspend fun getUsers(@Header("token") token: String): Response<List<User>>

    @GET("users/myUser")
    suspend fun getMyUser(@Header("token") token: String): Response<User>

    @POST("users/updateUser")
    suspend fun updateUser(@Header("token") token: String,@Body request: UpdateUserRequest): Response<Boolean>

    @GET("users/mentors")
    suspend fun getAllMentor(@Header("token") token: String): Response<List<User>>

    @GET("users/selectMentor/{mentorId}")
    suspend fun getSelectMentor(@Header("token") token: String,@Path("mentorId") mentorId : Long): Response<User>

    @GET("tasks/allTasks")
    suspend fun getAllTasks(@Header("token") token: String): Response<List<Task>>

    @GET("tasks/myTasks")
    suspend fun getMyTasks(@Header("token") token: String): Response<List<Task>>

    @POST("tasks/createTask")
    suspend fun createTask(@Header("token") token: String, @Body request: NewTaskRequest): Response<String>

    @POST("tasks/updateTask")
    suspend fun updateTask(@Header("token") token: String, @Body request: EditTaskRequest): Response<String>

    @GET("tasks/getTask/{taskId}")
    suspend fun getTask(@Header("token") token: String,@Path("taskId") taskId : Long): Response<Task>

    @GET("groups/getGroups")
    suspend fun getGroups(@Header("token") token: String): Response<List<Group>>

    @GET("groups/myGroups")
    suspend fun getMyGroups(@Header("token") token: String): Response<List<Group>>

    @GET("groups/{groupId}")
    suspend fun getGroupMembers(@Header("token") token: String,@Path("groupId") groupId : Long): Response<List<User>>

    @POST("groups/addNewMember")
    suspend fun addNewMember(@Header("token") token: String, @Body request: AddMember): Response<String>

    @GET("activities/getTaskActivities")
    suspend fun getTaskActivities(@Header("token") token: String): Response<List<ActivityModel.TaskData>>

    @GET("activities/getGroupActivities")
    suspend fun getGroupActivities(@Header("token") token: String): Response<List<ActivityModel.GroupData>>

    @GET("activities/getUserActivities")
    suspend fun getUserActivities(@Header("token") token: String): Response<List<ActivityModel.UserData>>

    companion object {
        fun getApi(): TrackerApi? {
            return RetrofitInstance.client?.create(TrackerApi::class.java)
        }
    }
}