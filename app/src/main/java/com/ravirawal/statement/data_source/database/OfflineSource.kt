package com.ravirawal.statement.data_source.database

import androidx.paging.DataSource
import com.ravirawal.statement.model.Article
import com.ravirawal.statement.model.SourcesItem
import com.ravirawal.statement.model.TopHeadlines


class OfflineSource(
    private val homeDao: HomeNewsDao,
    private val sourcesDao: SourcesDao,
    private val topHeadLinesDao: TopHeadLinesDao
) {
    fun getAllExploreNews(): List<Article> = homeDao.getAllExploreNews()

//    fun getArticlesFlow(): Flow<List<NewsResponse.Article>> = homeDao.getAllArticlesFlow()


    suspend fun insertExploreNews(data: List<Article>) {
        homeDao.insertExploreNews(data)
    }

    suspend fun deleteAllExploreNews() {
        homeDao.deleteAllExploreNews()
    }

    fun getSources(): List<SourcesItem> = sourcesDao.getAllSources()

    suspend fun insert(data: List<SourcesItem>) {
        sourcesDao.insertSourcesList(data)
    }

    suspend fun deleteAllSources() {
        sourcesDao.deleteAllSources()
    }

//    fun getTopHeadlines(): List<TopHeadlines> = topHeadLinesDao.getAllTopHeadlines()

    fun getPagedTopHeadlines(source:String): DataSource.Factory<Int, TopHeadlines> = topHeadLinesDao.getPagedTopHeadlines(source)


    suspend fun insertTopHeadlines(data: List<TopHeadlines>) {
        topHeadLinesDao.insertTopHeadlinesList(data)
    }

//    suspend fun deleteAllTopHeadlines() {
//        topHeadLinesDao.deleteAllTopHeadlines()
//    }
}