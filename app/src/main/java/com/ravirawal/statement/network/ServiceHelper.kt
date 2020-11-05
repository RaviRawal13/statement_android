package com.ravirawal.statement.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ravirawal.statement.BuildConfig
import com.ravirawal.statement.data_source.remote.IApiHelper
import com.ravirawal.statement.data_source.remote.NewsApiService
import com.ravirawal.statement.di.API_KEY
import com.ravirawal.statement.di.EN
import com.ravirawal.statement.di.ENDPOINT
import com.ravirawal.statement.di.LANGUAGE
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ServiceHelper {

    fun getOkHttp(): OkHttpClient {
        val builder = OkHttpClient.Builder()

        builder.readTimeout(90, TimeUnit.SECONDS)
        builder.connectTimeout(60, TimeUnit.SECONDS)

        val loggingInterceptor = HttpLoggingInterceptor()

        if (BuildConfig.DEBUG) {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }

        builder.addInterceptor(loggingInterceptor)
        builder.addInterceptor { chain ->
            var request = chain.request()
            val url =
                request.url.newBuilder().addQueryParameter(API_KEY, BuildConfig.API_KEY)
                    .addQueryParameter(
                        LANGUAGE, EN
                    ).build()

            request = request.newBuilder().url(url).build()

            chain.proceed(request)

        }
        return builder.build()
    }

    fun getRetrofit(
    ): Retrofit {
        return Retrofit.Builder()
            .client(getOkHttp())
            .baseUrl(ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
    }

    fun getNewsApiService(
    ): NewsApiService {
        return getRetrofit().create(NewsApiService::class.java)
    }

    fun getAPIHelper(
    ): IApiHelper {
        return IApiHelper.ApiHelperImpl(getNewsApiService())
    }

}