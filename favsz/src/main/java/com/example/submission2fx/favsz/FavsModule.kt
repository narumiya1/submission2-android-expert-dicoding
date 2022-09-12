package com.example.submission2fx.favsz

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoriteModule = module {
    viewModel {
        FavsViewModel(get())
    }
}