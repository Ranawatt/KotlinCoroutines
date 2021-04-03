package com.example.mindorkscoroutine.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mindorkscoroutine.data.local.dao.UserDao
import com.example.mindorkscoroutine.data.local.entity.User

@Database(entities = [User::class], exportSchema = false, version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}