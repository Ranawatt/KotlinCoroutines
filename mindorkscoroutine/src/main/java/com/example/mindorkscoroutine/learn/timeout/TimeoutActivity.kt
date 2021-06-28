package com.example.mindorkscoroutine.learn.timeout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mindorkscoroutine.R
import com.example.mindorkscoroutine.data.api.ApiHelperImpl
import com.example.mindorkscoroutine.data.api.ApiService
import com.example.mindorkscoroutine.data.api.RetrofitBuilder
import com.example.mindorkscoroutine.data.local.DatabaseBuilder
import com.example.mindorkscoroutine.data.local.DatabaseHelperImpl
import com.example.mindorkscoroutine.data.model.ApiUser
import com.example.mindorkscoroutine.learn.base.ApiUserAdapter
import com.example.mindorkscoroutine.utils.*
import kotlinx.android.synthetic.main.activity_single_network_call.*

class TimeoutActivity: AppCompatActivity() {

    private  lateinit var viewmodel: TimeoutViewModel
    private lateinit var apiUserAdapter: ApiUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_network_call)
        setupUI()
        setupViewModel()
        setupObserver()
    }

    private fun setupUI() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        apiUserAdapter = ApiUserAdapter(arrayListOf())
        recyclerView.addItemDecoration(DividerItemDecoration(this,
            (recyclerView.layoutManager as LinearLayoutManager).orientation)
        )
        recyclerView.adapter = apiUserAdapter
    }

    private fun setupViewModel() {
        viewmodel = ViewModelProvider(this, ViewModelFactory(
            ApiHelperImpl(RetrofitBuilder.apiService),
        DatabaseHelperImpl(DatabaseBuilder.getInstance(applicationContext)))).get(TimeoutViewModel::class.java)
    }

    private fun setupObserver() {
        viewmodel.getUsers().observe(this, Observer {
            when(it.status) {
                Status.SUCCESS -> {
                    progressBar.gone()
                    it.data?.let { user -> renderList(user) }
                    recyclerView.visible()
                }
                Status.LOADING -> {
                    progressBar.visible()
                    recyclerView.invisible()
                }
                Status.ERROR -> {
                    progressBar.gone()
                    showToast(R.string.network_error)
                }
            }
        })
    }

    private fun renderList(user: List<ApiUser>) {
        apiUserAdapter.addData(user)
        apiUserAdapter.notifyDataSetChanged()
    }
}