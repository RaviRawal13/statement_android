package com.ravirawal.statement.home.vm

import androidx.lifecycle.*
import com.ravirawal.statement.base.BaseViewModel
import com.ravirawal.statement.model.Article
import com.ravirawal.statement.model.NewsResponse
import com.ravirawal.statement.network.Output
import com.ravirawal.statement.repository.NewsRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : BaseViewModel() {

    var exploreScrollPosition = 0

    fun fetchNews(
        query: String,
        isOnline: Boolean,
        pageSize: Int = 50,
        page: Int = 1
    ): LiveData<Output<List<Article>?>> {
        return newsRepository.getNewsFlow(query, isOnline, pageSize, page)
            .asLiveData(viewModelScope.coroutineContext) // Use viewModel scope for auto cancellation

    }

    fun clearExploreCache() {
        GlobalScope.launch {
            newsRepository.clearExploreCache()
        }
    }

}