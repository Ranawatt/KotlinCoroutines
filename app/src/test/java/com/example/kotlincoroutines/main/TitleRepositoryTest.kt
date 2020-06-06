package com.example.kotlincoroutines.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.kotlincoroutines.fakes.MainNetworkCompletableFake
import com.example.kotlincoroutines.fakes.MainNetworkFake
import com.example.kotlincoroutines.fakes.TitleDaoFake
import com.google.common.truth.Truth
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule

class TitleRepositoryTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun whenRefreshTitleSuccess_insertsRows() = runBlockingTest{
        val titleDao = TitleDaoFake("title")
        val subject = TitleRepository(
            MainNetworkFake("ok"),
            titleDao
        )
        // launch starts a coroutine then immediately returns
//        GlobalScope.launch {
            // Since this is a asynchronous code, this may be called after the test completes
            subject.refreshTitle()
//        }
        // test functions return immediately, and
        // doesn't see the result of refreshTitle

        Truth.assertThat(titleDao.nextInsertedOrNull()).isEqualTo("ok")
    }

    @Test(expected = TitleRefreshError::class)
    fun whenRefreshTitleTimeOut_throws() = runBlockingTest {

        val network = MainNetworkCompletableFake()
        val subject = TitleRepository(
            network,
            TitleDaoFake("title")
        )

        launch {
            subject.refreshTitle()
        }
        advanceTimeBy(5_000)
    }


}