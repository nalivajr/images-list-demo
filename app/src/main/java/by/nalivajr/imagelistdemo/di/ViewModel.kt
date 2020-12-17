package by.nalivajr.imagelistdemo.di

import by.nalivajr.imagelistdemo.ui.viewmodel.ImagesListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        ImagesListViewModel(get(), get())
    }
}