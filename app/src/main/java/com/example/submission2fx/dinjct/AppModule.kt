package com.example.submission2fx.dinjct

import com.example.submission2fx.details.DetailViewModel
import com.example.submission2fx.GhiblViewModel
import com.example.submission2fx.home.SearchViewModel
import com.example.submission2fx.core.domain.usecase.GhibliAppInteractor
import com.example.submission2fx.core.domain.usecase.IGhibliAppUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<IGhibliAppUseCase> { GhibliAppInteractor(get()) }
}

@ExperimentalCoroutinesApi
@FlowPreview
val viewModelModule = module {
    viewModel { GhiblViewModel(get()) }
    viewModel { SearchViewModel(get()) }
    viewModel { DetailViewModel(get()) }

}