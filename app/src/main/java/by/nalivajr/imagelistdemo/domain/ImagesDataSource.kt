package by.nalivajr.imagelistdemo.domain

import androidx.paging.ItemKeyedDataSource
import by.nalivajr.imagelistdemo.api.ImagesApiService
import by.nalivajr.imagelistdemo.domain.model.ImageInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ImagesDataSource(
        private val imagesApiService: ImagesApiService
) : ItemKeyedDataSource<Int, ImageInfo>() {

    private val listeners: MutableList<LoadingStateListener> = mutableListOf()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<ImageInfo>
    ) {
        val limit = params.requestedLoadSize
        loadImages(limit, callback)
    }

    private fun loadImages(limit: Int, callback: LoadCallback<ImageInfo>) {
        listeners.forEach { it.onLoadingStateChanged(true) }
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val loadedImages = imagesApiService.loadImages(limit)
                callback.onResult(loadedImages)
            } catch (e: Throwable) {
                callback.onResult(emptyList())
                listeners.forEach { it.onLoadingFailed(e) }
            } finally {
                listeners.forEach { it.onLoadingStateChanged(false) }
            }
        }
    }

    override fun getKey(item: ImageInfo): Int = item.id

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<ImageInfo>) {
        loadImages(params.requestedLoadSize, callback)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<ImageInfo>) {
        callback.onResult(emptyList())
    }

    fun addLoadingStateListener(listener: LoadingStateListener) {
        listeners.add(listener)
    }


    interface LoadingStateListener {
        fun onLoadingStateChanged(loading: Boolean)
        fun onLoadingFailed(error: Throwable)
    }
}