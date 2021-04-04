package com.example.mindorkscoroutine.data.local

import android.content.Context
import androidx.room.Room

object DatabaseBuilder {

    @Volatile private var INSTANCE: AppDatabase? = null

    fun getInstance(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(AppDatabase::class) {
                INSTANCE?: buildRoomDB(context).also { INSTANCE = it }
        }
    }

    private fun buildRoomDB(context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java, "example-coroutines"
        ).build()

}