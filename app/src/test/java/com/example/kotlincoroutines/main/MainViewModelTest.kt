package com.example.kotlincoroutines.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.kotlincoroutines.fakes.MainNetworkCompletableFake
import com.example.kotlincoroutines.fakes.MainNetworkFake
import com.example.kotlincoroutines.fakes.TitleDaoFake
import com.example.kotlincoroutines.main.utils.MainCoroutineScopeRule
import com.example.kotlincoroutines.main.utils.captureValues
import com.example.kotlincoroutines.main.utils.getValueForTest
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.HttpException
import retrofit2.Response

@RunWith(JUnit4::class)
class MainViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()

    lateinit var subject: MainViewModel

    @Before
    fun setup() {
        subject = MainViewModel(
            TitleRepository(
                MainNetworkFake("OK"),
                TitleDaoFake("initial")
            ))
    }

    @Test
    fun whenMainClicked_updatesTaps() {
        subject.onMainViewClicked()
        Truth.assertThat(subject.taps.getValueForTest()).isEqualTo("0 taps")
        coroutineScope.advanceTimeBy(1000)
        Truth.assertThat(subject.taps.getValueForTest()).isEqualTo("1 taps")
    }

    @Test
    fun loadsTitleByDefault() {
        assertThat(subject.titles.getValueForTest()).isEqualTo("initial")
    }

    @Test
    fun whenSuccessfulTitleLoad_itShowsAndHidesSpinner() = coroutineScope.runBlockingTest {
        val network = MainNetworkCompletableFake()

        subject = MainViewModel(
            TitleRepository(
                network,
                TitleDaoFake("title")
            )
        )

        subject.spinner.captureValues {
            subject.onMainViewClicked()
            assertThat(values).isEqualTo(listOf(false, true))
            network.sendCompletionToAllCurrentRequests("OK")
            assertThat(values).isEqualTo(listOf(false, true, false))
        }
    }

    @Test
    fun whenErrorTitleReload_itShowsErrorAndHidesSpinner() = coroutineScope.runBlockingTest {
        val network = MainNetworkCompletableFake()
        subject = MainViewModel(
            TitleRepository(
                network,
                TitleDaoFake("title")
            )
        )

        subject.spinner.captureValues {
            assertThat(values).isEqualTo(listOf(false))
            subject.onMainViewClicked()
            assertThat(values).isEqualTo(listOf(false, true))
            network.sendErrorToCurrentRequests(makeErrorResult("An error"))
            assertThat(values).isEqualTo(listOf(false, true, false))
        }
    }

    @Test
    fun whenErrorTitleReload_itShowsErrorText() = coroutineScope.runBlockingTest {
        val network = MainNetworkCompletableFake()
        subject = MainViewModel(
            TitleRepository(
                network,
                TitleDaoFake("title")
            )
        )

        subject.onMainViewClicked()
        network.sendErrorToCurrentRequests(makeErrorResult("An error"))
        assertThat(subject.snackbar.getValueForTest()).isEqualTo("Unable to refresh title")
        subject.onSnackbarShown()
        assertThat(subject.snackbar.getValueForTest()).isEqualTo(null)
    }

    @Test
    fun whenMainViewClicked_titleIsRefreshed() = coroutineScope.runBlockingTest {
        val titleDao = TitleDaoFake("title")
        subject = MainViewModel(
            TitleRepository(
                MainNetworkFake("OK"),
                titleDao
            )
        )
        subject.onMainViewClicked()
        assertThat(titleDao.nextInsertedOrNull()).isEqualTo("OK")
    }

    private fun makeErrorResult(result: String): HttpException {
        return HttpException(
            Response.error<String>(
            500,
            ResponseBody.create(
                MediaType.get("application/json"),
                "\"$result\"")
        ))
    }
}