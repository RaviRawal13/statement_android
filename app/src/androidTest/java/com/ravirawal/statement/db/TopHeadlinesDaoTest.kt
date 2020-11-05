package com.ravirawal.statement.db

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ravirawal.statement.data_source.database.HomeNewsDataBase
import com.ravirawal.statement.util.TopHeadlinesFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
open class TopHeadlinesDaoTest {

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
    fun insertTopHeadlinesData() {
        val topHeadlinesList = TopHeadlinesFactory.fetchTopHeadlinesList()
        runBlocking {
            withContext(Dispatchers.IO) {
                homeNewsDataBase.getTopHeadlinesDao().insertTopHeadlinesList(topHeadlinesList)
                val topHeadlines = homeNewsDataBase.getTopHeadlinesDao().getAllTopHeadlines()
                assert(topHeadlines.isNotEmpty())
            }
        }

    }

    @Test
    fun getTopHeadlinesData() {
        val topHeadlinesList = TopHeadlinesFactory.fetchTopHeadlinesList()

        runBlocking {
            withContext(Dispatchers.IO) {
                homeNewsDataBase.getTopHeadlinesDao().insertTopHeadlinesList(topHeadlinesList)
                val topHeadlines = homeNewsDataBase.getTopHeadlinesDao().getAllTopHeadlines()
                assert(
                    topHeadlines == topHeadlinesList.sortedWith(
                        compareBy(
                            { it.url },
                            { it.url })
                    )
                )

            }
        }
    }

    @Test
    fun clearTopHeadlinesData() {
        val topHeadlinesList = TopHeadlinesFactory.fetchTopHeadlinesList()
        runBlocking {
            withContext(Dispatchers.IO) {
                homeNewsDataBase.getTopHeadlinesDao().insertTopHeadlinesList(topHeadlinesList)

                homeNewsDataBase.getTopHeadlinesDao().deleteAllTopHeadlines()

                assert(homeNewsDataBase.getTopHeadlinesDao().getAllTopHeadlines().isEmpty())

            }
        }
    }

}