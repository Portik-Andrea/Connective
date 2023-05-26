package com.example.a3trackerapplication.repositories

import com.example.a3trackerapplication.api.TrackerApi
import com.example.a3trackerapplication.models.*
import retrofit2.Response

class UserRepository {
    suspend fun test(): Response<String>? {
        return TrackerApi.getApi()?.test();
    }
    suspend fun login(request: LoginRequest): Response<LoginResponse> {
        return TrackerApi.getApi()!!.login(request = request);
    }

    suspend fun getUsers(token: String): Response<List<User>>? {
        return TrackerApi.getApi()?.getUsers(token = token);
    }

    suspend fun getMyUser(token: String): Response<User>? {
        return TrackerApi.getApi()?.getMyUser(token = token);
    }

    suspend fun updateUser(token: String,updateUser: UpdateUserRequest): Response<Boolean>? {
        return TrackerApi.getApi()?.updateUser(token = token,request = updateUser);
    }

    suspend fun getAllMentors(token: String): Response<List<User>>?{
        return TrackerApi.getApi()?.getAllMentor(token = token);
    }

    suspend fun selectMentor(token: String, mentorId: Long): Response<User>?{
        return TrackerApi.getApi()?.getSelectMentor(token = token, mentorId = mentorId);
    }
}