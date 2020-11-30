package by.nalivajr.imagelistdemo.domain.repository.impl

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import by.nalivajr.imagelistdemo.domain.ImagesDataSourceFactory
import by.nalivajr.imagelistdemo.domain.exception.NotSupportedException
import by.nalivajr.imagelistdemo.domain.model.ImageInfo
import by.nalivajr.imagelistdemo.domain.model.LoadingImagesDataHolder
import by.nalivajr.imagelistdemo.domain.repository.ImagesRepository
import by.nalivajr.imagelistdemo.ui.model.UiResult

class ImagesRepositoryImpl(
    private val dataSourceFactory: ImagesDataSourceFactory
) : ImagesRepository {

    override fun init(): LoadingImagesDataHolder {
        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(ImagesRepository.PAGE_SIZE)
            .setPrefetchDistance(2 * ImagesRepository.PRELOAD_COUNT)
            .setInitialLoadSizeHint(2 * ImagesRepository.PRELOAD_COUNT)
            .build()

        val pagedListData = LivePagedListBuilder(dataSourceFactory, pagedListConfig).build()
        val resultData = MediatorLiveData<UiResult<PagedList<ImageInfo>>>()
        resultData.addSource(pagedListData) {
            resultData.postValue(UiResult.success(it))
        }
        resultData.addSource(dataSourceFactory.loadingErrorsData) {
            resultData.postValue(UiResult.error(it))
        }
        return LoadingImagesDataHolder(resultData, dataSourceFactory.loadingStateData)
    }

    override fun reload() {
        dataSourceFactory.sourceLiveData.value?.invalidate()
    }

    override fun loadOne(): ImageInfo {
        throw NotSupportedException()
    }
}