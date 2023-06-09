package com.example.a3trackerapplication.ui.mytasks

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.a3trackerapplication.MyApplication
import com.example.a3trackerapplication.models.NewTaskRequest
import com.example.a3trackerapplication.repositories.TaskRepository
import kotlinx.coroutines.launch
import okhttp3.ResponseBody.Companion.toResponseBody


class NewTaskViewModelFactory(
    private val repository: TaskRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewTaskViewModel(repository) as T
    }
}


class NewTaskViewModel(private val repository: TaskRepository) : ViewModel()  {

    var createTaskResult: MutableLiveData<String> = MutableLiveData()
    private var errorMessage: MutableLiveData<List<String>> = MutableLiveData()

    fun createTask(request: NewTaskRequest) {
        viewModelScope.launch {
            try {
                val response = repository.createTask(MyApplication.token,request)
                if (response?.isSuccessful == true) {
                    createTaskResult.value = "SUCCESS"
                    Log.d("xxx", "Login body" + response.body().toString())
                } else {
                    val responseBody = response?.message();
                    val extractedText: String? = extractTextFromResponse(responseBody.toString())
                    createTaskResult.value = "INVALID CREDENTIALS"
                    val errorBody:String = response?.errorBody()!!.string()
                    val list: List<String> = errorBody.substring(1, errorBody.length - 1).split(", ")
                    errorMessage.value = list
                }
            } catch (e: Exception) {
                createTaskResult.value = "UNKNOWN ERROR"
                Log.i("xxx", "Login error $e")
            }
        }
    }
    private fun extractTextFromResponse(responseBody: String): String {
        val startTag = "["
        val endTag = "]"
        val startIndex = responseBody.indexOf(startTag)
        val endIndex = responseBody.indexOf(endTag)
        return if (startIndex != -1 && endIndex != -1 && endIndex > startIndex) {
            responseBody.substring(startIndex + startTag.length, endIndex)
        } else "nem sikerult konvertalni"
    }
}