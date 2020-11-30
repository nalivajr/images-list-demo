package by.nalivajr.imagelistdemo.domain

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import by.nalivajr.imagelistdemo.api.ImagesApiService
import by.nalivajr.imagelistdemo.domain.model.ImageInfo

class ImagesDataSourceFactory(private val imagesApiService: ImagesApiService) : DataSource.Factory<Int, ImageInfo>() {

    val sourceLiveData = MutableLiveData<ImagesDataSource>()
    val loadingStateData = MutableLiveData<Boolean>()
    val loadingErrorsData = MutableLiveData<Throwable>()

    override fun create(): ImagesDataSource {
        val dataSource = ImagesDataSource(imagesApiService)
        sourceLiveData.postValue(dataSource)
        dataSource.addLoadingStateListener(object: ImagesDataSource.LoadingStateListener {

            override fun onLoadingStateChanged(loading: Boolean) {
                loadingStateData.postValue(loading)
            }

            override fun onLoadingFailed(error: Throwable) {
                loadingStateData.postValue(false)
                loadingErrorsData.postValue(error)
            }
        })
        return dataSource
    }
}