package com.ravirawal.statement.headlines.vm

import androidx.lifecycle.*
import androidx.paging.PagedList
import com.ravirawal.statement.base.BaseViewModel
import com.ravirawal.statement.headlines.data_source.NewsPageDataSource
import com.ravirawal.statement.model.TopHeadlines
import com.ravirawal.statement.network.Output
import com.ravirawal.statement.repository.HeadlinesRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TopHeadlinesViewModel @Inject constructor(
    private val headlinesRepository: HeadlinesRepository
) : BaseViewModel() {

    private lateinit var newsListData: LiveData<PagedList<TopHeadlines>>

    fun newsList(
        connectivityAvailable: Boolean,
        source: String = "bbc-news",
    ): LiveData<PagedList<TopHeadlines>> {
        if (!this::newsListData.isInitialized) {
            newsListData =
                headlinesRepository.observePagedNews(
                    connectivityAvailable,
                    source,
                    viewModelScope
                )
        }
        return newsListData
    }

    private var isAscending = true
    fun onSortBy(isAscending: Boolean = true) {
        if (this.isAscending != isAscending) {
            this.isAscending = isAscending
            headlinesRepository.onSortBy(isAscending)
            newsListData.value?.dataSource?.invalidate()
        }
    }
}