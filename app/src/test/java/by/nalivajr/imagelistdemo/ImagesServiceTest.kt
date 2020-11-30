package by.nalivajr.imagelistdemo

import by.nalivajr.imagelistdemo.api.ImagesAPI
import by.nalivajr.imagelistdemo.api.ImagesApiService
import by.nalivajr.imagelistdemo.api.ImagesApiServiceImpl
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.ResponseBody
import okhttp3.internal.http.RealResponseBody
import okio.Buffer
import org.junit.Assert
import org.junit.Test

import org.junit.Before
import org.mockito.Mockito
import retrofit2.Call
import retrofit2.Response
import java.net.HttpURLConnection

class ImagesServiceTest {

    private lateinit var target: ImagesApiService

    @Before
    fun setUp() {
        val apiMock = Mockito.mock(ImagesAPI::class.java)
        val callMock = Mockito.mock(Call::class.java) as Call<ResponseBody>
        val stubRequest = Request.Builder().url(STUB_IMAGE_URL).build()
        val rawResponse = okhttp3.Response.Builder()
            .code(HttpURLConnection.HTTP_OK)
            .protocol(Protocol.HTTP_2)
            .request(stubRequest)
            .message("")
            .build()
        val stubResponseBody: ResponseBody = RealResponseBody("image/png", 100, Buffer())
        val stubResponse = Response.success(stubResponseBody, rawResponse)
        Mockito.`when`(callMock.execute()).thenReturn(stubResponse)
        Mockito.`when`(apiMock.getImage()).thenReturn(callMock)
        target = ImagesApiServiceImpl(apiMock)
    }

    @Test
    fun load() {
        val limit = 2
        val loadedImages = target.loadImages(limit)
        Assert.assertEquals(limit, loadedImages.size)
        Assert.assertEquals(limit, loadedImages.filter { it.url == STUB_IMAGE_URL }.size )
    }

    companion object {
        private val STUB_IMAGE_URL = "http://stub/"
    }
}