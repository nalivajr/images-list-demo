package by.nalivajr.imagelistdemo.api

import by.nalivajr.imagelistdemo.domain.model.ImageInfo
import kotlinx.coroutines.*
import java.util.concurrent.atomic.AtomicInteger

class ImagesApiServiceImpl(private val imagesAPI: ImagesAPI) : ImagesApiService {

    private val idGenerator = AtomicInteger()

    override fun loadImages(limit: Int): List<ImageInfo> {
        var res: List<ImageInfo> = emptyList()
        runBlocking {
            try {
                res = List(limit) { async(Dispatchers.IO) { loadImage() } }.awaitAll()
            } catch (e: Throwable) {
                throw e
            }
        }
        return res
    }

    private fun loadImage(): ImageInfo {
        val response = imagesAPI.getImage().execute()
        val path = response.raw().request.url.toString()
        return ImageInfo(idGenerator.incrementAndGet(), path)
    }
}