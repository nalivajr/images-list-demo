package by.nalivajr.imagelistdemo.domain.repository

import by.nalivajr.imagelistdemo.domain.model.ImagesPage

interface ImagesRepository {

    fun reload(): List<ImagesPage>

    fun appendOne(source: List<ImagesPage>): List<ImagesPage>

    companion object {
        const val PAGE_SIZE = 70
        const val PRELOAD_COUNT = 140
    }
}