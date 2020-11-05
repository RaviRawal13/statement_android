package com.ravirawal.statement.data_source.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ravirawal.statement.model.Article
import com.ravirawal.statement.model.SourcesItem
import com.ravirawal.statement.model.TopHeadlines

const val APP_DATABASE_NAME = "statement.db"

@Database(entities = [Article::class, SourcesItem::class, TopHeadlines::class], version = 1 , exportSchema = false)
@TypeConverters(Converters::class)
abstract class HomeNewsDataBase : RoomDatabase() {
    abstract fun getHomeNewsDao(): HomeNewsDao

    abstract fun getTopHeadlinesDao(): TopHeadLinesDao

    abstract fun getSourcesDao(): SourcesDao

}