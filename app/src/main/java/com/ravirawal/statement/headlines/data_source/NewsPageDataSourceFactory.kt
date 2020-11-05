package com.ravirawal.statement.headlines.data_source

import androidx.paging.DataSource
import androidx.paging.PagedList
import com.ravirawal.statement.data_source.database.OfflineSource
import com.ravirawal.statement.data_source.remote.OnlineSources
import com.ravirawal.statement.model.TopHeadlines
import kotlinx.coroutines.CoroutineScope

class NewsPageDataSourceFactory(
    private val source: String,
    private val offlineDataSource: OfflineSource,
    private val onlineDataSource: OnlineSources,
    private val scope: CoroutineScope
) : DataSource.Factory<Int, TopHeadlines>() {

    private var isAscending: Boolean = true

    override fun create(): DataSource<Int, TopHeadlines> {
        return NewsPageDataSource(
            this.source,
            isAscending,
            offlineDataSource,
            onlineDataSource,
            scope
        )
    }

    fun onSortBy(isAscending: Boolean = true) {
        this.isAscending = isAscending
    }

    companion object {
        private const val PAGE_SIZE = 20

        fun pagedListConfig() = PagedList.Config.Builder()
            .setInitialLoadSizeHint(PAGE_SIZE)
            .setPageSize(PAGE_SIZE)
            .setEnablePlaceholders(true)
            .build()
    }
}
