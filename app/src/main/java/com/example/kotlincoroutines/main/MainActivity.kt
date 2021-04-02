package com.example.kotlincoroutines.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlincoroutines.R
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Get MainViewModel by passing a database to the factory
        val database = getDatabase(this)
        val repository = TitleRepository(getNetworkService(), database.titleDao)
        val viewModel = ViewModelProvider(this, MainViewModel.FACTORY(repository))
            .get(MainViewModel::class.java)

        // When rootLayout is clicked call onMainViewClicked in ViewModel
        rootLayout.setOnClickListener {
            viewModel.onMainViewClicked()
        }

        // update the title when the [MainViewModel.title] changes
        viewModel.titles.observe(this, Observer {
            titles.text = it
        })

        viewModel.taps.observe(this, Observer {
            taps.text = it
        })
        // show the spinner when [MainViewModel.spinner] is true
        viewModel.spinner.observe(this, Observer {
            spinner.visibility = if (it) View.VISIBLE else View.GONE
        })

        // Show a snackbar whenever the [ViewModel.snackbar] is updated a non-null value
        viewModel.snackbar.observe(this) { text ->
            text?.let {
                Snackbar.make(rootLayout, text, Snackbar.LENGTH_SHORT).show()
                viewModel.onSnackbarShown()
            }
        }
    }
}
