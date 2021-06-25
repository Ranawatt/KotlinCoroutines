package com.example.mindorkscoroutine.learn.room

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mindorkscoroutine.R
import com.example.mindorkscoroutine.data.api.ApiHelperImpl
import com.example.mindorkscoroutine.data.api.RetrofitBuilder
import com.example.mindorkscoroutine.data.local.DatabaseBuilder
import com.example.mindorkscoroutine.data.local.DatabaseHelperImpl
import com.example.mindorkscoroutine.learn.base.UserAdapter
import com.example.mindorkscoroutine.utils.ViewModelFactory
import kotlinx.android.synthetic.main.activity_single_network_call.*

class RoomDBActivity: AppCompatActivity() {

    private lateinit var viewmodel: RoomViewModel
    private lateinit var userAdapter: UserAdapter
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_single_network_call)
        setupUI()
        setupViewModel()
        setupObsever()
    }

    private fun setupUI() {
        userAdapter = UserAdapter(arrayListOf())
        recyclerView.addItemDecoration(DividerItemDecoration(this,
            (recyclerView.layoutManager as LinearLayoutManager).orientation
        ))
        recyclerView.adapter = userAdapter
    }

    private fun setupViewModel() {
        viewmodel = ViewModelProvider(this, ViewModelFactory(
            ApiHelperImpl(RetrofitBuilder.apiService),
            DatabaseHelperImpl(DatabaseBuilder.getInstance(applicationContext)))
        ).get(RoomViewModel::class.java)
    }

    private fun setupObsever() {
        TODO("Not yet implemented")
    }
}