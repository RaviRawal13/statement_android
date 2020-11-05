package com.ravirawal.statement.data_source.database

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ravirawal.statement.model.TopHeadlines

@Dao
interface TopHeadLinesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTopHeadlinesList(top_headlines: List<TopHeadlines>): List<Long>

    @Query("SELECT * FROM  top_headlines")
    fun getAllTopHeadlines(): List<TopHeadlines>

    @Query("DELETE FROM top_headlines")
    suspend fun deleteAllTopHeadlines()

    @Query("SELECT * FROM top_headlines WHERE source = :source")
    fun getPagedTopHeadlines(source: String): DataSource.Factory<Int, TopHeadlines>
}