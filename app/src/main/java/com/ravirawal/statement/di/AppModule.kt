package com.ravirawal.statement.di

import android.content.Context
import android.content.SharedPreferences
import com.ravirawal.statement.StatementApp
import com.ravirawal.statement.di.vm.ViewModelModule
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(
    includes = [
        ApiModule::class,
        ViewModelModule::class,
        DatabaseModule::class,
    ]
)
class AppModule {

    @Provides
    @Singleton
    fun provideSharedPrefs(app: StatementApp): SharedPreferences =
        app.getSharedPreferences("st_sharedpreferences", Context.MODE_PRIVATE)
}