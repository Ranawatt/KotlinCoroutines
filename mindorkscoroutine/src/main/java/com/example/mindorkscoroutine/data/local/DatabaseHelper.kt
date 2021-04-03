package com.example.mindorkscoroutine.data.local

import com.example.mindorkscoroutine.data.local.entity.User

interface DatabaseHelper {

    suspend fun getUsers(): List<User>

    suspend fun insertAll(users: List<User>)
}