package com.example.mindorkscoroutine.learn.task.onetask

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mindorkscoroutine.data.api.ApiHelper
import com.example.mindorkscoroutine.data.local.entity.User
import com.example.mindorkscoroutine.data.model.ApiUser
import com.example.mindorkscoroutine.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LongRunningTaskViewModel(private val apiHelper: ApiHelper): ViewModel() {

    private val status =  MutableLiveData<Resource<List<ApiUser>>>()

    init {
        startLongRunningTasks()
    }

    fun startLongRunningTasks() {
        viewModelScope.launch {
            try {
               doLongRunningTask()
                val users = apiHelper.getUsers()
                status.postValue(Resource.success(users))
            }catch (e: Exception) {
                status.postValue(Resource.error("Something Went Wrong ", null))
            }

        }
    }

    private suspend fun doLongRunningTask() {
        withContext(Dispatchers.Default) {
            delay(1000)
        }
    }


    fun getStatus(): LiveData<Resource<List<ApiUser>>> {
        return status
    }
}