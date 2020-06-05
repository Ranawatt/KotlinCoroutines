package com.example.kotlincoroutines.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlincoroutines.util.BACKGROUND
import com.example.kotlincoroutines.util.singleArgViewModelFactory
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * MainViewModel designed to store and manage UI-related data in a lifecycle conscious way. This
 * allows data to survive configuration changes such as screen rotations. In addition, background
 * work such as fetching network results can continue through configuration changes and deliver
 * results after the new Fragment or Activity is available.
 * @param repository the data source this ViewModel will fetch results from.
 */
class MainViewModel(private val repository: TitleRepository) : ViewModel() {

    companion object {
        /**
         * Factory for creating [MainViewModel]
         *
         * @param arg the repository to pass to [MainViewModel]
         */
        val FACTORY = singleArgViewModelFactory(::MainViewModel)
    }
    /**
     * Request a snackbar to display a string.
     * This variable is private because we don't want to expose MutableLiveData
     * MutableLiveData allows anyone to set a value, and MainViewModel is the only
     * class that should be setting values.
     */
    private val _snackBar = MutableLiveData<String?>()
    // Request a snackbar to display a string.
    val snackbar: LiveData<String?> get() = _snackBar
    // Update title text via this LiveData
    val title = repository.title

    private val _spinner = MutableLiveData<Boolean>(false)
    // Show a loading spinner if true
    val spinner: LiveData<Boolean> get() = _spinner
    // Count of taps on the screen
    private var tapCount = 0
    // LiveData with formatted tap count.
    private val _taps = MutableLiveData<String>("$tapCount taps")
    // Public view of tap live data.
    val taps: LiveData<String> get() = _taps
    // Respond to onClick events by refreshing the title.
    // The loading spinner will display until a result is returned, and errors will trigger a snackbar.
    fun onMainViewClicked() {
        refreshTitle()
        updateTaps()
    }
    // Wait one second then update the tap count.
    fun updateTaps() {
        viewModelScope.launch {
            delay(1000)
            _taps.postValue("${++tapCount} taps")
        }
    }
    // Called immediately after the UI shows the snackbar.
    fun onSnackbarShown() {
        _snackBar.value = null
    }
    // Refresh the title, showing a loading spinner while it refreshes and errors via snackbar.
    fun refreshTitle() = launchLoadData {
        repository.refreshTitle()
    }
    /**
     * Helper function to call a data load function with a loading spinner, errors will trigger a
     * snackbar.
     *
     * By marking `block` as `suspend` this creates a suspend lambda which can call suspend
     * functions.
     *
     * @param block lambda to actually load data. It is called in the viewModelScope. Before calling the
     *              lambda the loading spinner will display, after completion or error the loading
     *              spinner will stop
     */
    private fun launchLoadData(block: suspend () -> Unit): Job {
        return viewModelScope.launch {
            try {
                _spinner.value = true
                block()
            }catch (error: TitleRefreshError) {
                _snackBar.value = error.message
            } finally {
                _spinner.value = false
            }
        }

    }
}