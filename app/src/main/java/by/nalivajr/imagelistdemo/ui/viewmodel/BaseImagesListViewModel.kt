package by.nalivajr.imagelistdemo.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import by.nalivajr.imagelistdemo.ui.model.UiResult

abstract class BaseImagesListViewModel<T>() : ViewModel() {

    abstract val imagesList: LiveData<UiResult<T>>
    abstract val loadingState: LiveData<Boolean>
}