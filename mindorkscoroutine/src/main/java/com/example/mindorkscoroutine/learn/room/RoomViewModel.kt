package com.example.mindorkscoroutine.learn.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mindorkscoroutine.data.api.ApiHelper
import com.example.mindorkscoroutine.data.local.DatabaseHelper
import com.example.mindorkscoroutine.data.local.entity.User
import com.example.mindorkscoroutine.data.model.ApiUser
import com.example.mindorkscoroutine.utils.Resource
import kotlinx.coroutines.launch

class RoomViewModel(private val apiHelper: ApiHelper,
    private val databaseHelper: DatabaseHelper) : ViewModel() {

    private val users = MutableLiveData<Resource<List<User>>>()
    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            users.postValue(Resource.loading(null))
            try {
                val usersFromDB = databaseHelper.getUsers()
                if (usersFromDB.isEmpty()) {
                    val usersFromApi = apiHelper.getUsers()
                    val usersToInsertInDB = mutableListOf<User>()

                    for(apiUser in usersFromDB){
                        val user = User(
                            apiUser.id,
                            apiUser.name,
                            apiUser.email,
                            apiUser.avatar
                        )
                        usersToInsertInDB.add(user)
                    }
                    databaseHelper.insertAll(usersToInsertInDB)
                    users.postValue(Resource.success(usersToInsertInDB))
                }else{
                    users.postValue(Resource.success(usersFromDB))
                }

            }catch (e: Exception) {
                users.postValue(Resource.error("Something Went Wrong", null))
            }
        }
    }

    fun getUsers() : LiveData<Resource<List<User>>> {
        return users
    }

}