package com.ravirawal.statement.repository


import com.ravirawal.statement.data_source.database.OfflineSource
import com.ravirawal.statement.data_source.remote.OnlineSources
import com.ravirawal.statement.model.Article
import com.ravirawal.statement.model.SourcesItem
import com.ravirawal.statement.network.Error
import com.ravirawal.statement.network.Output
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

const val OFFLINE = "offline"
const val ONLINE = "online"

@Singleton
class NewsRepository @Inject constructor(
    private val offlineDataSource: OfflineSource,
    private val onlineDataSource: OnlineSources
) {

    fun getNewsFlow(
        query: String,
        isOnline: Boolean,
        pageSize: Int,
        page: Int
    ): Flow<Output<List<Article>?>> {
        val aa = flow {
            emit(Output.LOAD())
            val articlesDb = offlineDataSource.getAllExploreNews()
            if (articlesDb.isNotEmpty())
                emit(Output.Success<List<Article>?>(articlesDb, OFFLINE, 0))

            if (isOnline) {
                val response = onlineDataSource.getExploreNews(
                    query = query,
                    sortBy = "publishedAt",
                    pageSize = pageSize,
                    page = page
                )
                if (response.isSuccess && response is Output.Success) {
                    response.data?.let {
//                        offlineDataSource.deleteAllNews()
                        offlineDataSource.insertExploreNews(it)
                    }
                    emit(response)
                } else {
                    (response as? Output.Failure)?.let {
                        if (articlesDb.isNullOrEmpty()) {
                            emit(it)
                        }
                    }
                }
            } else {
                if (articlesDb.isEmpty())
                    emit(
                        Output.Failure<List<Article>?>(
                            Error.NetworkConnection,
                            "No internet connection",
                            2
                        )
                    )
            }
        }.flowOn(Dispatchers.IO)
        return aa
    }

//    suspend fun getAllNews(query: String): Output<List<NewsResponse.Article>?> {
//        return if (context.isOnline()) {
//            val response = onlineDataSource.getAllNews(query)
//            return if (response.isSuccess) {
//                offlineDataSource.deleteAllNews()
//                (response as? Output.Success)?.data?.let {
//                    offlineDataSource.cacheArticles(
//                        it
//                    )
//                }
//                response
//            } else {
//                Output.Success(offlineDataSource.getArticles(), "", 0)
//            }
//        } else {
//            Output.Success(offlineDataSource.getArticles(), "", 0)
//        }
//    }

    fun getSourcesFlow(isOnline: Boolean): Flow<Output<List<SourcesItem?>?>> {
        return flow {
            emit(Output.LOAD())
            val articlesDb = offlineDataSource.getSources()
            if (articlesDb.isNotEmpty())
                emit(Output.Success<List<SourcesItem?>?>(articlesDb, OFFLINE, 0))

            if (isOnline) {
                val response = onlineDataSource.getSources()
                if (response.isSuccess && response is Output.Success) {
                    response.data?.let {
                        offlineDataSource.deleteAllSources()
                        offlineDataSource.insert(it.filterNotNull())
                    }
                    emit(response)
                } else {
                    (response as? Output.Failure)?.let {
                        if (articlesDb.isNullOrEmpty()) {
                            emit(it)
                        }
                    }
                }
            } else {
                if (articlesDb.isEmpty())
                    emit(
                        Output.Failure<List<SourcesItem?>?>(
                            Error.NetworkConnection,
                            "No internet connection",
                            2
                        )
                    )
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun clearExploreCache() {
        offlineDataSource.deleteAllExploreNews()
    }

//    suspend fun getAllSources(isOnline: Boolean): Output<List<SourcesItem?>?> {
//        return if (isOnline) {
//            val response = onlineDataSource.getSources()
//            if (response.isSuccess) {
//                offlineDataSource.deleteAllSources()
//                (response as? Output.Success)?.data?.filterNotNull()?.let {
//                    offlineDataSource.cacheSources(
//                        it
//                    )
//                }
//                response
//            } else {
//                Output.Success(offlineDataSource.getSources(), "", 0)
//            }
//        } else {
//            Output.Success(offlineDataSource.getSources(), "", 0)
//        }
//    }

//    suspend fun getTopHeadlines(query: String, isOnline: Boolean): Output<List<TopHeadlines>?> {
//        return if (isOnline) {
//            val response = onlineDataSource.getTopHeadlines(query)
//            return if (response.isSuccess) {
//                offlineDataSource.deleteAllTopHeadlines()
//                (response as? Output.Success)?.data?.let {
//                    offlineDataSource.cacheTopHeadlines(
//                        it
//                    )
//                }
//                response
//            } else {
//                Output.Success(offlineDataSource.getTopHeadlines(), "", 0)
//            }
//        } else {
//            Output.Success(offlineDataSource.getTopHeadlines(), "", 0)
//        }
//    }

}

