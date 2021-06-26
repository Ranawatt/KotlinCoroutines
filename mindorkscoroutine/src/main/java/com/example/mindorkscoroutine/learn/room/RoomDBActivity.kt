package com.example.mindorkscoroutine.learn.room

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mindorkscoroutine.R
import com.example.mindorkscoroutine.data.api.ApiHelperImpl
import com.example.mindorkscoroutine.data.api.RetrofitBuilder
import com.example.mindorkscoroutine.data.local.DatabaseBuilder
import com.example.mindorkscoroutine.data.local.DatabaseHelperImpl
import com.example.mindorkscoroutine.data.local.entity.User
import com.example.mindorkscoroutine.learn.base.UserAdapter
import com.example.mindorkscoroutine.utils.Status
import com.example.mindorkscoroutine.utils.ViewModelFactory
import com.example.mindorkscoroutine.utils.gone
import com.example.mindorkscoroutine.utils.visible
import kotlinx.android.synthetic.main.activity_single_network_call.*

class RoomDBActivity: AppCompatActivity() {

    private lateinit var viewmodel: RoomViewModel
    private lateinit var userAdapter: UserAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_network_call)
        setupUI()
        setupViewModel()
        setupObserver()
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

    private fun setupObserver() {
        viewmodel.getUsers().observe(this, Observer {
            when(it.status) {
                Status.SUCCESS -> {
                    progressBar.gone()
                    it.data?.let { user ->
                        renderList(user)
                    }
                    recyclerView.visible()
                }
                Status.LOADING -> {
                    recyclerView.gone()
                    progressBar.visible()
                }
                Status.ERROR -> {
                    progressBar.gone()
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun renderList(users: List<User>) {
        userAdapter.addData(users)
        userAdapter.notifyDataSetChanged()
    }

}