package by.nalivajr.imagelistdemo.ui.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.nalivajr.imagelistdemo.domain.errorhandling.ErrorDispatcher
import by.nalivajr.imagelistdemo.domain.model.ImagesPage
import by.nalivajr.imagelistdemo.domain.repository.ImagesRepository
import by.nalivajr.imagelistdemo.ui.model.UiResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ImagesListViewModel(
    private val imagesRepository: ImagesRepository,
    private val errorDispatcher: ErrorDispatcher
) : ViewModel() {

    val imagesList: MutableLiveData<UiResult<List<ImagesPage>>> = MutableLiveData()
    val loadingState: MutableLiveData<Boolean> = MutableLiveData()

    fun reloadImages() {
        doLoad {
            imagesRepository.reload()
        }
    }

    fun addImage() {
        doLoad {
            val currentList = getCurrentList()
            imagesRepository.appendOne(currentList)
        }
    }

    private fun doLoad(loader: () -> List<ImagesPage>) {
        loadingState.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                loader.invoke()
            }.onSuccess {
                imagesList.postValue(UiResult.success(it))
            }.onFailure {
                imagesList.postValue(UiResult.error(errorDispatcher.dispatchError(it)))
            }
            loadingState.postValue(false)
        }
    }

    private fun getCurrentList(): List<ImagesPage> {
        return imagesList.value?.data ?: emptyList()
    }

    fun onCreate(bundle: Bundle?) {
        val saved = bundle?.getParcelableArrayList<ImagesPage>(KEY_LOADED_IMAGES)
        if (saved != null) {
            imagesList.value = UiResult.success(saved)
        } else {
            reloadImages()
        }
    }

    fun onSaveState(bundle: Bundle) {
        val currentList = getCurrentList()
        bundle.putParcelableArrayList(KEY_LOADED_IMAGES, ArrayList(currentList))
    }

    companion object {
        private const val KEY_LOADED_IMAGES = "key_loaded_images"
    }
}