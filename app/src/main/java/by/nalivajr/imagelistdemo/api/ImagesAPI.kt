package by.nalivajr.imagelistdemo.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET

interface ImagesAPI {

    @GET("200/200/")
    fun getImage(): Call<ResponseBody>

    companion object {
        const val BASE_URL = "http://loremflickr.com/"
    }
}