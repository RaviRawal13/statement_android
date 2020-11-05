package com.ravirawal.statement.headlines.data_source

import androidx.paging.PageKeyedDataSource
import com.ravirawal.statement.data_source.database.OfflineSource
import com.ravirawal.statement.data_source.remote.OnlineSources
import com.ravirawal.statement.model.TopHeadlines
import com.ravirawal.statement.network.Output
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class NewsPageDataSource(
    private val source: String,
    private val isAscending: Boolean = true,
    private val offlineDataSource: OfflineSource,
    private val onlineDataSource: OnlineSources,
    private val coroutineScope: CoroutineScope

) : PageKeyedDataSource<Int, TopHeadlines>() {
    private var supervisorJob = SupervisorJob()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, TopHeadlines>
    ) {
        fetchData(source, isAscending, 1, params.requestedLoadSize) {
            callback.onResult(it, null, 2)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, TopHeadlines>) {
        val page = params.key
        fetchData(source, isAscending, page, params.requestedLoadSize) {
            callback.onResult(it, page + 1)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, TopHeadlines>) {

        val page = params.key
        fetchData(source, isAscending, page, params.requestedLoadSize) {
            callback.onResult(it, page - 1)
        }
    }

    private fun fetchData(
        source: String,
        isAscending: Boolean = true,
        page: Int,
        pageSize: Int,
        callback: (List<TopHeadlines>) -> Unit
    ) {

        coroutineScope.launch(getJobErrorHandler() + supervisorJob) {

            val response = onlineDataSource.getTopHeadlines(source, page, pageSize)

            if (response is Output.Success) {
                val results = response.data ?: emptyList()
                offlineDataSource.insertTopHeadlines(results)
                callback(if (isAscending) {
                    results.sortedBy { it.publishedAt }
                } else {
                    results.sortedByDescending { it.publishedAt }
                })

            } else if (response is Output.Failure) {
                postError(response.message ?: "")
            }
        }
    }

    private fun getJobErrorHandler() = CoroutineExceptionHandler { _, e ->
        postError(e.message ?: e.toString())
    }

    private fun postError(message: String) {
    }
}
