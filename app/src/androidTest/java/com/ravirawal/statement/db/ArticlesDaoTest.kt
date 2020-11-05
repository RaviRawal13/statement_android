package com.ravirawal.statement.db

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ravirawal.statement.data_source.database.HomeNewsDataBase
import com.ravirawal.statement.util.ArticleFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
open class ArticlesDaoTest {

    private lateinit var homeNewsDataBase: HomeNewsDataBase

    @Before
    fun initDb() {
        homeNewsDataBase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            HomeNewsDataBase::class.java
        ).build()
    }

    @After
    fun closeDb() {
        homeNewsDataBase.close()
    }

    @Test
    fun insertArticleSaveData() {
        val articleList = ArticleFactory.fetchArticlesList()
        runBlocking {
            withContext(Dispatchers.IO) {
                homeNewsDataBase.getHomeNewsDao().insertExploreNews(articleList)
                val articles = homeNewsDataBase.getHomeNewsDao().getAllExploreNews()
                assert(articles.isNotEmpty())
            }
        }

    }

    @Test
    fun getArticlesRetrievesData() {
        val articleList = ArticleFactory.fetchArticlesList()
        runBlocking {
            withContext(Dispatchers.IO) {
                homeNewsDataBase.getHomeNewsDao().insertExploreNews(articleList)
                val articles = homeNewsDataBase.getHomeNewsDao().getAllExploreNews()
                assert(articles == articleList.sortedWith(compareBy({ it.url }, { it.url })))

            }
        }
    }

    @Test
    fun clearArticlesData() {
        val articleList = ArticleFactory.fetchArticlesList()
        runBlocking {
            withContext(Dispatchers.IO) {
                homeNewsDataBase.getHomeNewsDao().insertExploreNews(articleList)
                homeNewsDataBase.getHomeNewsDao().deleteAllExploreNews()

                assert(homeNewsDataBase.getHomeNewsDao().getAllExploreNews().isEmpty())

            }
        }
    }

}