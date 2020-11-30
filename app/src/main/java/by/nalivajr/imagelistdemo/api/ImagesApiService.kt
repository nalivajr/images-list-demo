
package by.nalivajr.imagelistdemo.api

import by.nalivajr.imagelistdemo.domain.model.ImageInfo

interface ImagesApiService {

    fun loadImages(limit: Int): List<ImageInfo>

}