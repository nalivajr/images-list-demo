package by.nalivajr.imagelistdemo.di

import by.nalivajr.imagelistdemo.ui.viewmodel.ImagesListViewModel
import by.nalivajr.imagelistdemo.ui.viewmodel.PagedImagesListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        PagedImagesListViewModel(get(), get())
    }

    viewModel {
        ImagesListViewModel(get(), get())
    }
}