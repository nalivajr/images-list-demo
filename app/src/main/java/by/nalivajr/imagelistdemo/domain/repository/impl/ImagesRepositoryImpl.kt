package by.nalivajr.imagelistdemo.domain.repository.impl

import by.nalivajr.imagelistdemo.api.ImagesApiService
import by.nalivajr.imagelistdemo.domain.model.ImagesPage
import by.nalivajr.imagelistdemo.domain.repository.ImagesRepository
import kotlin.math.min

class ImagesRepositoryImpl(
    private val imagesApiService: ImagesApiService
) : ImagesRepository {

    override fun reload(): List<ImagesPage> {
        val loadedImages = imagesApiService.loadImages(ImagesRepository.PRELOAD_COUNT).toMutableList()
        val resultPages = mutableListOf<ImagesPage>()
        while (loadedImages.isNotEmpty()) {
            val portion = loadedImages.subList(0, min(loadedImages.size, ImagesRepository.PAGE_SIZE))
            resultPages.add(ImagesPage(portion.toList()))
            loadedImages.removeAll(portion)
        }
        return resultPages
    }

    override fun appendOne(source: List<ImagesPage>): List<ImagesPage> {
        val newImage = imagesApiService.loadImages(1).first()
        val pageToAdd = source.find { it.images.size < ImagesRepository.PAGE_SIZE }
        val resultPages = source.toMutableList()
        if (pageToAdd == null) {
            resultPages.add(ImagesPage(listOf(newImage)))
        } else {
            val newImagesList = pageToAdd.images.toMutableList()
            newImagesList.add(newImage)
            resultPages.remove(pageToAdd)
            resultPages.add(pageToAdd.copy(images = newImagesList))
        }
        return resultPages
    }
}