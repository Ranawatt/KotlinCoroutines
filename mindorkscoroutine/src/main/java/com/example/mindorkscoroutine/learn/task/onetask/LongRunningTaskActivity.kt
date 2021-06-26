package com.example.mindorkscoroutine.learn.task.onetask

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.mindorkscoroutine.R
import com.example.mindorkscoroutine.data.api.ApiHelper
import com.example.mindorkscoroutine.data.api.ApiHelperImpl
import com.example.mindorkscoroutine.data.api.RetrofitBuilder
import com.example.mindorkscoroutine.data.local.DatabaseBuilder
import com.example.mindorkscoroutine.data.local.DatabaseHelper
import com.example.mindorkscoroutine.data.local.DatabaseHelperImpl
import com.example.mindorkscoroutine.learn.base.UserAdapter
import com.example.mindorkscoroutine.utils.ViewModelFactory
import kotlinx.coroutines.DEBUG_PROPERTY_VALUE_AUTO

class LongRunningTaskActivity: AppCompatActivity() {

    private lateinit var viewModel: LongRunningTaskViewModel
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_network_call)
        setupViewModel()
        setupLongRunningTaskObserver()
    }

    private fun setupLongRunningTaskObserver() {

    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, ViewModelFactory(
            ApiHelperImpl(RetrofitBuilder.apiService),
            DatabaseHelperImpl(DatabaseBuilder.getInstance(applicationContext))
        )).get(LongRunningTaskViewModel::class.java)
    }


}