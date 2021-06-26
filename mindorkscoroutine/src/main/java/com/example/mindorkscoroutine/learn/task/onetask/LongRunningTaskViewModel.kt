package com.example.mindorkscoroutine.learn.task.onetask

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mindorkscoroutine.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LongRunningTaskViewModel: ViewModel() {

    private val status = MutableLiveData<Resource<String>>()

    init {
        startLongRunningTasks()
    }

    private fun startLongRunningTasks() {
        viewModelScope.launch {
            status.postValue(Resource.loading(null))
            try {
               doLongRunningTask()
                status.postValue(Resource.success("Task Completed"))
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


    fun getStatus(): LiveData<Resource<String>> {
        return status
    }
}