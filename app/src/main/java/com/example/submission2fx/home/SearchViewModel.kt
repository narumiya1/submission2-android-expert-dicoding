package com.example.submission2fx.home


import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.submission2fx.core.domain.usecase.IGhibliAppUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*

@FlowPreview
@ExperimentalCoroutinesApi
class SearchViewModel(private val movieAppUseCase: IGhibliAppUseCase) : ViewModel() {

    private val queryChannel = ConflatedBroadcastChannel<String>()

    fun setSearchQuery(search: String) {
        // queryChannel.offer(search)
        // ganti offer ke trySend krena deprecated
        queryChannel.trySend(search).isSuccess
    }

    val movieResult = queryChannel.asFlow()
        .debounce(300)
        .distinctUntilChanged()
        .filter {
            it.trim().isNotEmpty()
        }
        .flatMapLatest {
            movieAppUseCase.getSearchMovies(it)
        }.asLiveData()


}
