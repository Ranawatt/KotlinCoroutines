package com.example.mindorkscoroutine

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.mindorkscoroutine.learn.retrofit.series.SeriesNetworkCallActivity
import com.example.mindorkscoroutine.learn.retrofit.single.SingleNetworkCallActivity
import com.example.mindorkscoroutine.learn.room.RoomDBActivity
import com.example.mindorkscoroutine.learn.task.onetask.LongRunningTaskActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun singleNetworkCallActivity(view: View) {
        startActivity(Intent(this, SingleNetworkCallActivity::class.java))
    }
    fun seriesNetworkCallActivity(view: View) {
        startActivity(Intent(this, SeriesNetworkCallActivity::class.java))
    }

    fun longRunningTaskActivity(view: View) {
        startActivity(Intent(this, LongRunningTaskActivity::class.java))
    }

    fun roomDatabaseCallActivity(view: View) {
        startActivity(Intent(this, RoomDBActivity::class.java))
    }
}
