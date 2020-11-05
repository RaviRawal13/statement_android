package com.ravirawal.statement.data_source.remote

import com.ravirawal.statement.model.NewsResponse
import com.ravirawal.statement.model.SourcesResponse
import com.ravirawal.statement.model.TopHeadlinesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("everything")
    suspend fun getAllExploreNews(
        @Query("q") query: String,
        @Query("sortBy") sortBy: String,
        @Query("pageSize") pageSize: Int? = null,
        @Query("page") page: Int? = null
    ): Response<NewsResponse>

    @GET("sources")
    suspend fun getAllSources(): Response<SourcesResponse>

    @GET("top-headlines")
    suspend fun getTopHeadLinesBySource(
        @Query("sources") sources: String,
        @Query("pageSize") pageSize: Int? = null,
        @Query("page") page: Int? = null
    ): Response<TopHeadlinesResponse>
}