package by.nalivajr.imagelistdemo.domain.model

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import by.nalivajr.imagelistdemo.ui.model.UiResult

data class LoadingImagesDataHolder(
    val imagesList: LiveData<UiResult<PagedList<ImageInfo>>>,
    val loadingIndicatorData: LiveData<Boolean>
)