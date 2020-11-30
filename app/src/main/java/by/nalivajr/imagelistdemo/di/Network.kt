package by.nalivajr.imagelistdemo.di

import by.nalivajr.imagelistdemo.api.ImagesAPI
import com.google.gson.Gson
import com.squareup.picasso.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val TIMEOUT_SECONDS = 60L
private const val CONNECT_TIMEOUT_SECONDS = 15L

const val BASE_API_URL = ImagesAPI.BASE_URL

val networkModule = module {

    single { okHttpClient() }

    factory { getRetrofit(BASE_API_URL, get(), get()) }

    single { gson() }

    single { imagesApi(get()) }
}

private fun okHttpClient(): OkHttpClient {
    return okHttpClientBuilder().build()
}

private fun okHttpClientBuilder(): OkHttpClient.Builder {
    val okHttpClientBuilder = OkHttpClient.Builder()
    if (BuildConfig.DEBUG) {
        okHttpClientBuilder.addInterceptor(HttpLoggingInterceptor().also {
            it.level = HttpLoggingInterceptor.Level.BODY
        })
    }

    okHttpClientBuilder.readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
    okHttpClientBuilder.writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
    okHttpClientBuilder.connectTimeout(CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
    return okHttpClientBuilder
}

private fun getRetrofit(baseUrl: String, okHttpClient: OkHttpClient, gson: Gson): Retrofit {
    return Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .build()
}

fun gson() = Gson()

fun imagesApi(retrofit: Retrofit): ImagesAPI {
    return retrofit.create(ImagesAPI::class.java)
}