package com.ravirawal.statement.data_source.remote

import com.ravirawal.statement.model.NewsResponse
import com.ravirawal.statement.model.SourcesResponse
import com.ravirawal.statement.model.TopHeadlinesResponse
import retrofit2.Response

interface IApiHelper {
    suspend fun getAllExploreNews(
        query: String,
        sortBy: String,
        pageSize: Int,
        page: Int
    ): Response<NewsResponse>

    suspend fun getAllSources(): Response<SourcesResponse>

    suspend fun getTopHeadLinesBySource(
        sources: String,
        page: Int? = null,
        pageSize: Int? = null
    ): Response<TopHeadlinesResponse>

    class ApiHelperImpl(private val newsApiService: NewsApiService) : IApiHelper {
        override suspend fun getAllExploreNews(query: String, sortBy: String, pageSize: Int, page: Int) =
            newsApiService.getAllExploreNews(query, sortBy, pageSize, page)

        override suspend fun getAllSources(): Response<SourcesResponse> =
            newsApiService.getAllSources()

        override suspend fun getTopHeadLinesBySource(
            sources: String,
            page: Int?,
            pageSize: Int?
        ): Response<TopHeadlinesResponse> {
            return newsApiService.getTopHeadLinesBySource(sources, pageSize, page)
        }
    }
}
