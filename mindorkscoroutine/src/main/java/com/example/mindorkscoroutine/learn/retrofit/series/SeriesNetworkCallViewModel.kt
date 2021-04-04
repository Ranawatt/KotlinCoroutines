package com.example.mindorkscoroutine.learn.retrofit.series

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mindorkscoroutine.data.api.ApiHelper
import com.example.mindorkscoroutine.data.local.DatabaseHelper
import com.example.mindorkscoroutine.data.model.ApiUser
import com.example.mindorkscoroutine.utils.Resource
import kotlinx.coroutines.launch

class SeriesNetworkCallViewModel(
    private val apiHelper: ApiHelper,
    private val databaseHelper: DatabaseHelper) : ViewModel() {

    private val users = MutableLiveData<Resource<List<ApiUser>>>()
    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            users.postValue(Resource.loading(null))
            try {
                val usersFromApi = apiHelper.getUsers()
                val moreUsersFromApi = apiHelper.getMoreUsers()
                val response = mutableListOf<ApiUser>()
                response.addAll(usersFromApi)
                response.addAll(moreUsersFromApi)
                users.postValue(Resource.success(response))
            }catch (e: Exception){
                users.postValue(Resource.error("Something Went Wrong",null))
            }
        }
    }

    fun getUsers(): LiveData<Resource<List<ApiUser>>> = users


}