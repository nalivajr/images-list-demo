package by.nalivajr.imagelistdemo.di

import by.nalivajr.imagelistdemo.api.ImagesApiService
import by.nalivajr.imagelistdemo.api.ImagesApiServiceImpl
import by.nalivajr.imagelistdemo.domain.errorhandling.ErrorDispatcher
import by.nalivajr.imagelistdemo.domain.errorhandling.ErrorDispatcherImpl
import by.nalivajr.imagelistdemo.domain.repository.ImagesRepository
import by.nalivajr.imagelistdemo.domain.repository.impl.ImagesRepositoryImpl
import org.koin.dsl.module

val domainModule = module {

    single<ImagesApiService> { ImagesApiServiceImpl(get()) }

    single<ImagesRepository> { ImagesRepositoryImpl(get()) }

    single<ErrorDispatcher> { ErrorDispatcherImpl(get()) }
}