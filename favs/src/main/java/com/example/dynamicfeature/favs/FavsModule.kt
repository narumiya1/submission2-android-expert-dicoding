package com.example.dynamicfeature.favs

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoriteModule = module {
    viewModel {
        FavsViewModel(get())
    }
}