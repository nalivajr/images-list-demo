package by.nalivajr.imagelistdemo.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import by.nalivajr.imagelistdemo.api.ImagesApiService
import by.nalivajr.imagelistdemo.domain.errorhandling.ErrorDispatcher
import by.nalivajr.imagelistdemo.domain.model.ImageInfo
import by.nalivajr.imagelistdemo.domain.repository.ImagesRepository
import by.nalivajr.imagelistdemo.ui.model.UiResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ImagesListViewModel(
    private val imagesApiService: ImagesApiService,
    private val errorDispatcher: ErrorDispatcher
) : BaseImagesListViewModel<List<ImageInfo>>() {

    private val internalImagesList = MutableLiveData<UiResult<List<ImageInfo>>>()
    private val internalLoadingState: MutableLiveData<Boolean> = MutableLiveData()

    override val imagesList: LiveData<UiResult<List<ImageInfo>>> = internalImagesList
    override val loadingState: LiveData<Boolean> = internalLoadingState

    init {
        internalLoadingState.postValue(true)
    }

    fun reloadImages() {
        doLoad {
            imagesApiService.loadImages(ImagesRepository.PRELOAD_COUNT)
        }
    }

    fun addImage() {
        doLoad {
            val images = imagesApiService.loadImages(1)
            val newList = mutableListOf<ImageInfo>()
            newList.addAll(internalImagesList.value?.data ?: emptyList())
            newList.addAll(images)
            return@doLoad newList
        }
    }

    private fun doLoad(loader: () -> List<ImageInfo>) {
        internalLoadingState.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                loader.invoke()
            }.onSuccess {
                internalImagesList.postValue(UiResult.success(it))
            }.onFailure {
                internalImagesList.postValue(UiResult.error(errorDispatcher.dispatchError(it)))
            }
            internalLoadingState.postValue(false)
        }
    }

}