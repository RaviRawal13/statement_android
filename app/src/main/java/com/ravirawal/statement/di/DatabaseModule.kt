package com.ravirawal.statement.di

import androidx.room.Room
import com.ravirawal.statement.StatementApp
import com.ravirawal.statement.data_source.database.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun providesHomeNewsDatabase(app: StatementApp) = Room.databaseBuilder(
        app,
        HomeNewsDataBase::class.java,
        APP_DATABASE_NAME
    ).build()

    @Provides
    @Singleton
    fun providesHomeNewsDao(database: HomeNewsDataBase) = database.getHomeNewsDao()

    @Provides
    @Singleton
    fun providesSourcesDao(database: HomeNewsDataBase) = database.getSourcesDao()

    @Provides
    @Singleton
    fun providesTopHeadlinesDao(database: HomeNewsDataBase) = database.getTopHeadlinesDao()

    @Provides
    @Singleton
    fun providesOfflineSourcesRoomBased(
        homeNewsDao: HomeNewsDao,
        sourcesDao: SourcesDao,
        topHeadLinesDao: TopHeadLinesDao
    ) =
        OfflineSource(homeNewsDao, sourcesDao, topHeadLinesDao)

}