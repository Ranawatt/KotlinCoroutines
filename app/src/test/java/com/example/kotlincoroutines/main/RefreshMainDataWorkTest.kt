package com.example.kotlincoroutines.main

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class RefreshMainDataWorkTest{

    @Test
    fun testRefreshMainDataWork(){
        val fakeNetwork = MainNetworkFake("OK")
        val context = ApplicationProvider.getApplicationContext<Context>()
        val worker = TestListenableWorkerBuilder<RefreshMainDataWork>(context)
            .setWorkerFactory(RefreshMainDataWork.Factory(fakeNetwork))
            .build()

        // Start the work synchronously
        val result = worker.startWork().get()

        assertThat(result).isEqualTo(Result.success())

    }
}