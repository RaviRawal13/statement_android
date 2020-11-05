package com.ravirawal.statement.data_source.database

import androidx.room.*
import com.ravirawal.statement.model.SourcesItem


@Dao
interface SourcesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSourcesList(article: List<SourcesItem>): List<Long>

    @Query("SELECT * FROM  sources")
    fun getAllSources(): List<SourcesItem>

    @Query("DELETE FROM sources")
    suspend fun deleteAllSources()


}