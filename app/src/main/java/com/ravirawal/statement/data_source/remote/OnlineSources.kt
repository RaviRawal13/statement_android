package com.ravirawal.statement.data_source.remote


import com.ravirawal.statement.model.Article
import com.ravirawal.statement.model.SourcesItem
import com.ravirawal.statement.model.TopHeadlines
import com.ravirawal.statement.network.Error
import com.ravirawal.statement.network.Output
import com.ravirawal.statement.repository.ONLINE


class OnlineSources(private val service: IApiHelper) {

    suspend fun getExploreNews(
        query: String,
        sortBy: String = "publishedAt", pageSize: Int, page: Int
    ): Output<List<Article>?> {
        val response = service.getAllExploreNews(query, sortBy = sortBy, pageSize, page)
        return if (response.isSuccessful) {
            Output.Success(response.body()?.articles, ONLINE, 0)
        } else {
            Output.Failure(
                Error.APIError,
                response.message(),
                response.code()
            )
        }
    }

    suspend fun getSources(): Output<List<SourcesItem?>?> {
        val response = service.getAllSources()
        return if (response.isSuccessful) {
            Output.Success(response.body()?.sources, ONLINE, 0)
        } else {
            Output.Failure<List<SourcesItem?>?>(
                Error.APIError,
                response.message(),
                response.code()
            )
        }
    }

    suspend fun getTopHeadlines(
        source: String,
        page: Int? = null,
        pageSize: Int? = null
    ): Output<List<TopHeadlines>?> {
        val response = service.getTopHeadLinesBySource(source, page, pageSize)
        return if (response.isSuccessful) {
            Output.Success(response.body()?.articles, ONLINE, 0)
        } else {
            Output.Failure(
                Error.APIError,
                response.message(),
                response.code()
            )
        }
    }

}
