package com.example.mindorkscoroutine.learn.retrofit.series

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mindorkscoroutine.data.api.ApiHelper
import com.example.mindorkscoroutine.data.local.DatabaseHelper
import com.example.mindorkscoroutine.data.local.entity.User
import com.example.mindorkscoroutine.utils.Resource
import kotlinx.coroutines.launch

class SeriesNetworkCallViewModel(
    private val apiHelper: ApiHelper,
    private val databaseHelper: DatabaseHelper) : ViewModel() {

    var users = MutableLiveData<Resource<List<User>>>()
    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            val usersResponse = apiHelper.getUsers()
            val moreUsersResponse = apiHelper.getMoreUsers()
        }
    }

}