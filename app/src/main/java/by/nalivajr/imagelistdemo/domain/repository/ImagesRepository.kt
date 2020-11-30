package by.nalivajr.imagelistdemo.domain.repository

import by.nalivajr.imagelistdemo.domain.model.ImageInfo
import by.nalivajr.imagelistdemo.domain.model.LoadingImagesDataHolder

interface ImagesRepository {

    fun reload()

    fun init(): LoadingImagesDataHolder

    fun loadOne(): ImageInfo

    companion object {
        const val PAGE_SIZE = 70
        const val PRELOAD_COUNT = 140
    }
}