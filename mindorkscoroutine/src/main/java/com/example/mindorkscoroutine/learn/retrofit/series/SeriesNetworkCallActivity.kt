package com.example.mindorkscoroutine.learn.retrofit.series

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
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
import kotlinx.android.synthetic.main.activity_single_network_call.*

class SeriesNetworkCallActivity : AppCompatActivity() {

    private lateinit var viewModel: SeriesNetworkCallViewModel
    private lateinit var adapter: ApiUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_network_call)
        setupUI()
        setupViewModel()
        setupObserver()
    }

    private fun setupUI() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ApiUserAdapter(
            arrayListOf()
        )
        recyclerView.addItemDecoration(
            DividerItemDecoration(recyclerView.context, (recyclerView.layoutManager as LinearLayoutManager).orientation)
        )
        recyclerView.adapter = adapter
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, ViewModelFactory(
            ApiHelperImpl(RetrofitBuilder.apiService),
                DatabaseHelperImpl(DatabaseBuilder.getInstance(applicationContext))
        )
        ).get(SeriesNetworkCallViewModel::class.java)
    }

    private fun setupObserver() {
        viewModel.getUsers().observe(this, Observer {
            when(it.status){
                Status.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    it.data?.let {
                        users -> renderList(users)
                    }
                    recyclerView.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                }
                Status.ERROR -> {
                    progressBar.visibility = View.VISIBLE
                    Toast.makeText(this, "Unable to laod data", Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun renderList(users: List<ApiUser>) {
        adapter.addData(users)
        adapter.notifyDataSetChanged()
    }
}