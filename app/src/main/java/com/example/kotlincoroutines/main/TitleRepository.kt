package com.example.kotlincoroutines.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import kotlinx.coroutines.*

/**
 * TitleRepository provides an interface to fetch a title or request a new one be generated.
 * Repository modules handle data operations. They provide a clean API so that the rest of the app
 * can retrieve this data easily. They know where to get the data from and what API calls to make
 * when data is updated. You can consider repositories to be mediators between different data
 * sources, in our case it mediates between a network API and an offline database cache.
 */
class TitleRepository(val network: MainNetwork, val titleDao: TitleDao) {
    /**
     * [LiveData] to load title.
     * This is the main interface for loading a title. The title will be loaded from the offline cache.
     * Observing this will not cause the title to be refreshed, use [TitleRepository.refreshTitleWithCallbacks]
     * to refresh the title.
     */
    val title: LiveData<String?> = titleDao.titleLiveData.map { it?.title }
    /**
     * Refresh the current title and save the results to the offline cache.
     * This method does not return the new title. Use [TitleRepository.title] to observe
     * the current tile.
     */
    suspend fun refreshTitle(){

        withContext(Dispatchers.IO) {
            val result = try {
                // Make network request using a blocking call
                network.fetchNextTitle().execute()
            }catch (cause : Throwable) {
                // If the network throws an exception, inform the caller
                throw TitleRefreshError("Unable to Refresh Title ", cause)
            }
            if (result.isSuccessful){
                // Save it to the database
                titleDao.insertTitle(Title(result.body()!!))
            }else{
                // If it's not successful, inform the callback of the error
                throw TitleRefreshError("Unable to Refresh Title ", null)
            }
        }

    }
//    suspend fun refreshTitle() {
//        try {
//            val result = withTimeout(5_000) {
//                network.fetchNextTitle()
//            }
//            titleDao.insertTitle(Title(result))
//        } catch (error: Throwable) {
//            throw TitleRefreshError("Unable to refresh title", error)
//        }
//    }

    /**
     * This API is exposed for callers from the Java Programming language.
     * The request will run unstructured, which means it won't be able to be cancelled.
     * @param titleRefreshCallback a callback
     */
//    fun refreshTitleInterop(titleRefreshCallback: TitleRefreshCallback) {
//        val scope = CoroutineScope(Dispatchers.Default)
//        scope.launch {
//            try {
//                refreshTitle()
//                titleRefreshCallback.onCompleted()
//            } catch (throwable: Throwable) {
//                titleRefreshCallback.onError(throwable)
//            }
//        }
//    }
}
/**
 * Thrown when there was a error fetching a new title
 * @property message user ready error message
 * @property cause the original cause of this exception
 */
class TitleRefreshError(message: String, cause: Throwable?) : Throwable(message, cause)

interface TitleRefreshCallback {
    fun onCompleted()
    fun onError(cause: Throwable)
}