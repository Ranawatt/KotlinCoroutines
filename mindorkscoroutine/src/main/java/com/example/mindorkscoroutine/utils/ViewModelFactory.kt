package com.example.mindorkscoroutine.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mindorkscoroutine.data.api.ApiHelper
import com.example.mindorkscoroutine.data.local.DatabaseHelper
import com.example.mindorkscoroutine.learn.retrofit.series.SeriesNetworkCallViewModel
import com.example.mindorkscoroutine.learn.retrofit.single.SingleNetworkCallViewModel

class ViewModelFactory(
    private val apiHelper: ApiHelper,
    private val databaseHelper: DatabaseHelper
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SingleNetworkCallViewModel::class.java))
            return SingleNetworkCallViewModel(apiHelper, databaseHelper) as T
        if (modelClass.isAssignableFrom(SeriesNetworkCallViewModel::class.java))
            return SeriesNetworkCallViewModel(apiHelper, databaseHelper) as T
        throw IllegalArgumentException("Unknown class Name")
    }
}
