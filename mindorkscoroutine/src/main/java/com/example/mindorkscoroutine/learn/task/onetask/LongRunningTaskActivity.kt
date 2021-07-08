package com.example.mindorkscoroutine.learn.task.onetask

import android.os.Bundle
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
import com.example.mindorkscoroutine.data.model.ApiUser
import com.example.mindorkscoroutine.learn.base.ApiUserAdapter
import com.example.mindorkscoroutine.learn.base.UserAdapter
import com.example.mindorkscoroutine.utils.Status
import com.example.mindorkscoroutine.utils.ViewModelFactory
import com.example.mindorkscoroutine.utils.gone
import com.example.mindorkscoroutine.utils.visible
import kotlinx.android.synthetic.main.activity_single_network_call.*

class LongRunningTaskActivity: AppCompatActivity() {

    private lateinit var viewModel: LongRunningTaskViewModel
    private lateinit var adapter: ApiUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_network_call)
        setupUI()
        setupViewModel()
        setupLongRunningTaskObserver()
    }

    private fun setupUI() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ApiUserAdapter(arrayListOf())
        recyclerView.addItemDecoration(DividerItemDecoration(this,
            (recyclerView.layoutManager as LinearLayoutManager).orientation)
        )
        recyclerView.adapter = adapter
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, ViewModelFactory(
            ApiHelperImpl(RetrofitBuilder.apiService),
            DatabaseHelperImpl(DatabaseBuilder.getInstance(applicationContext))
        )).get(LongRunningTaskViewModel::class.java)
    }

    private fun setupLongRunningTaskObserver() {
        viewModel.getStatus().observe(this, Observer {
            when(it.status) {
                Status.SUCCESS -> {
                    it.data?.let { status ->
                        renderList(status)
                    }
                }
                Status.LOADING -> {
                    progressBar.visible()
                    recyclerView.gone()
                }
                Status.ERROR -> {
                    progressBar.gone()
                    Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_LONG).show()
                }
            }
        })


    }

    private fun renderList(status: List<ApiUser>) {
        adapter.addData(status)
        adapter.notifyDataSetChanged()
    }
}