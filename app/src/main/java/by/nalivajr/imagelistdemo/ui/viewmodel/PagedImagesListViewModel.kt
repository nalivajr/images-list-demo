package by.nalivajr.imagelistdemo.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.PagedList
import by.nalivajr.imagelistdemo.domain.errorhandling.ErrorDispatcher
import by.nalivajr.imagelistdemo.domain.model.ImageInfo
import by.nalivajr.imagelistdemo.domain.repository.ImagesRepository
import by.nalivajr.imagelistdemo.ui.model.UiResult

class PagedImagesListViewModel(
    private val imagesRepository: ImagesRepository,
    private val errorDispatcher: ErrorDispatcher
) : BaseImagesListViewModel<PagedList<ImageInfo>>() {

    private val dataSourceStateHolder = imagesRepository.init()

    override val imagesList: LiveData<UiResult<PagedList<ImageInfo>>>
            = Transformations.map(dataSourceStateHolder.imagesList) { input ->
        return@map if (input.isSuccess) {
            input
        } else {
            UiResult.error(errorDispatcher.dispatchError(input.error), input.error)
        }
    }
    override val loadingState: LiveData<Boolean> = dataSourceStateHolder.loadingIndicatorData

    fun loadImages() {
        imagesRepository.reload()
    }
}