package com.ravirawal.statement.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ravirawal.statement.BuildConfig
import com.ravirawal.statement.data_source.remote.NewsApiService
import com.ravirawal.statement.data_source.remote.IApiHelper
import com.ravirawal.statement.data_source.remote.OnlineSources
import com.ravirawal.statement.network.ServiceHelper
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


const val ENDPOINT = "https://newsapi.org/v2/"
const val API_KEY = "apiKey"
const val LANGUAGE = "language"
const val EN = "en"

@Module
class ApiModule {

    @Provides
    @Singleton
    fun providesOnlineSources() =
        OnlineSources(ServiceHelper.getAPIHelper())

}