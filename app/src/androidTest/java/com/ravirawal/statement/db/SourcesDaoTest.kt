package com.ravirawal.statement.db

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ravirawal.statement.data_source.database.HomeNewsDataBase
import com.ravirawal.statement.util.SourcesFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
open class SourcesDaoTest {

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
    fun insertSourcesList() {
        val sourceList = SourcesFactory.fetchSourcesList()
        runBlocking {
            withContext(Dispatchers.IO) {
                homeNewsDataBase.getSourcesDao().insertSourcesList(sourceList)
                val sources = homeNewsDataBase.getSourcesDao().getAllSources()
                assert(sources.isNotEmpty())
            }
        }

    }

    @Test
    fun getSourcesData() {
        val sourceList = SourcesFactory.fetchSourcesList()
        runBlocking {
            withContext(Dispatchers.IO) {
                homeNewsDataBase.getSourcesDao().insertSourcesList(sourceList)
                val sources = homeNewsDataBase.getSourcesDao().getAllSources()
                assert(sources == sourceList.sortedWith(compareBy({ it.id }, { it.id })))

            }
        }
    }

    @Test
    fun clearSourcesData() {
        val sourceList = SourcesFactory.fetchSourcesList()
        runBlocking {
            withContext(Dispatchers.IO) {
                homeNewsDataBase.getSourcesDao().insertSourcesList(sourceList)
                homeNewsDataBase.getSourcesDao().deleteAllSources()
                assert(homeNewsDataBase.getSourcesDao().getAllSources().isEmpty())
            }
        }
    }

}