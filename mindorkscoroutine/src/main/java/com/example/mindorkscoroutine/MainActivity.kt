package com.example.mindorkscoroutine

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.mindorkscoroutine.learn.retrofit.single.SingleNetworkCallActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun startSingleNetworkCallActivity(view: View) {
        startActivity(Intent(this, SingleNetworkCallActivity::class.java))
    }
    fun seriesNetworkCallActivity(view: View) {
        startActivity(Intent(this, SeriesNetworkCallActivity::class.java))
    }
}
