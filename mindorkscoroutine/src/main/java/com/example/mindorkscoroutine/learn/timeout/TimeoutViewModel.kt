package com.example.mindorkscoroutine.learn.timeout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mindorkscoroutine.data.api.ApiHelper
import com.example.mindorkscoroutine.data.local.DatabaseHelper
import com.example.mindorkscoroutine.data.model.ApiUser
import com.example.mindorkscoroutine.utils.Resource
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout

class TimeoutViewModel(private val apiHelper: ApiHelper): ViewModel() {

    private val users = MutableLiveData<Resource<List<ApiUser>>>()

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            users.postValue(Resource.loading(null))
            try {
                withTimeout(100){
                    val apiUsers = apiHelper.getUsers()
                    users.postValue(Resource.success(apiUsers))
                }
            }catch (e: TimeoutCancellationException) {
                users.postValue(Resource.error("TimeOutCancellation Exception", null))
            }catch (e: Exception) {
                users.postValue(Resource.error("Something Went Wrong", null))
            }
        }
    }

    fun getUsers() : LiveData<Resource<List<ApiUser>>> {
        return  users
    }
}