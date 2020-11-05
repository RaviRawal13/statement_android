package com.ravirawal.statement.data_source.database

import androidx.room.*
import com.ravirawal.statement.model.Article

@Dao
interface HomeNewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExploreNews(article: List<Article>): List<Long>

    @Query("SELECT * FROM  explore_news")
    fun getAllExploreNews(): List<Article>

//    @Query("SELECT * FROM  explore_news")
//    fun getAllExploreNewsFlow(): Flow<List<NewsResponse.Article>>

    @Query("DELETE FROM explore_news")
    suspend fun deleteAllExploreNews()

}