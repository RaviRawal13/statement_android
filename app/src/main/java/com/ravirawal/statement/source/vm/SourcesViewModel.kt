package com.ravirawal.statement.source.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ravirawal.statement.base.BaseViewModel
import com.ravirawal.statement.model.NewsResponse
import com.ravirawal.statement.model.SourcesItem
import com.ravirawal.statement.network.Output
import com.ravirawal.statement.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SourcesViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : BaseViewModel() {
    val news: MutableLiveData<Output<List<SourcesItem?>?>> = MutableLiveData()

//    fun fetchSources(inOnline: Boolean) {
//        news.value = Output.LOAD()
//        viewModelScope.launch(Dispatchers.IO) {
//            val sources = newsRepository.getAllSources(inOnline)
//            news.postValue(sources)
//        }
//    }

    fun fetchSources( isOnline: Boolean): LiveData<Output<List<SourcesItem?>?>> {
        return newsRepository.getSourcesFlow(isOnline)
            .asLiveData(viewModelScope.coroutineContext) // Use viewModel scope for auto cancellation

    }
}