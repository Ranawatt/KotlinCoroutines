package com.example.kotlincoroutines

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
/**
 * onCreate is called before the first screen is shown to the user.
 *
 * Use it to setup any background tasks, running expensive setup operations in a background
 * thread to avoid delaying app start.
 */
class KotlinCoroutinesApp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupWorkManagerJob()
    }

    private fun setupWorkManagerJob() {
        // initialize WorkManager with a Factory
        val workManagerConfiguration = Configuration.Builder()
            .setWorkerFactory(RefreshMainDataWork.Factory())
            .build()
        WorkManager.initialize(this, workManagerConfiguration)

        // Use constraints to require the work only run when the device is charging and the
        // network is unmetered
        val constraints = Constraints.Builder()
            .setRequiresCharging(true)
            .setRequiredNetworkType(UNMETERED)
            .build()

        // Specify that the work should attempt to run every day
        val work = PeriodicWorkRequestBuilder<RefreshMainDataWork>(1, TimeUnit.DAYS)
            .setConstraints(constraints)
            .build()

        // Enqueue it work WorkManager, keeping any previously scheduled jobs for the same
        // work.
        WorkManager.getInstance(this)
            .enqueueUniquePeriodicWork(RefreshMainDataWork::class.java.name, KEEP, work)
    }
}