package com.ravirawal.statement.repository

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.ravirawal.statement.data_source.database.OfflineSource
import com.ravirawal.statement.data_source.remote.OnlineSources
import com.ravirawal.statement.headlines.data_source.NewsPageDataSourceFactory
import com.ravirawal.statement.model.TopHeadlines
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HeadlinesRepository @Inject constructor(
    private val offlineDataSource: OfflineSource,
    private val onlineDataSource: OnlineSources,

    ) {

    private lateinit var dataSourceFactory: NewsPageDataSourceFactory
    private var isAscending = true

    fun onSortBy(isAscending: Boolean = true) {
        this.isAscending = isAscending
        dataSourceFactory.onSortBy(isAscending)
    }

    fun observePagedNews(
        connectivityAvailable: Boolean,
        source: String,
        coroutineScope: CoroutineScope
    ): LiveData<PagedList<TopHeadlines>> {
        return if (connectivityAvailable)
            observeRemotePagedNews(source, coroutineScope)
        else observeLocalPagedNews(source)
    }

    private fun observeRemotePagedNews(
        source: String,
        coroutineScope: CoroutineScope
    ): LiveData<PagedList<TopHeadlines>> {
        dataSourceFactory = NewsPageDataSourceFactory(
            source,
            offlineDataSource, onlineDataSource,
            coroutineScope
        )
        return LivePagedListBuilder(
            dataSourceFactory,
            NewsPageDataSourceFactory.pagedListConfig()
        ).build()
    }

    private fun observeLocalPagedNews(
        source: String,
    ): LiveData<PagedList<TopHeadlines>> {

        val dataSourceFactory =
            offlineDataSource.getPagedTopHeadlines(source)
        return LivePagedListBuilder(
            dataSourceFactory,
            NewsPageDataSourceFactory.pagedListConfig()
        ).build()
    }


}