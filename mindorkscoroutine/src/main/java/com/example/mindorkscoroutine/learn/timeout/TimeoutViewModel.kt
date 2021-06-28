package com.example.mindorkscoroutine.learn.timeout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mindorkscoroutine.data.api.ApiHelper
import com.example.mindorkscoroutine.data.local.DatabaseHelper
import com.example.mindorkscoroutine.data.model.ApiUser
import com.example.mindorkscoroutine.utils.Resource

class TimeoutViewModel(private val apiHelper: ApiHelper,
      private val databaseHelper: DatabaseHelper): ViewModel() {

    private val users = MutableLiveData<Resource<List<ApiUser>>>()

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        TODO("Not yet implemented")
    }

    fun getUsers() : LiveData<Resource<List<ApiUser>>> {
        return  users
    }
}