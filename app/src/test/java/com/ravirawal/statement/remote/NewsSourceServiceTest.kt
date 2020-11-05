package com.ravirawal.statement.remote

import com.ravirawal.statement.data_source.remote.NewsApiService
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

@RunWith(JUnit4::class)
class NewsSourceServiceTest : BaseServiceTest() {
    private lateinit var service: NewsApiService

    @Before
    @Throws(IOException::class)
    fun createService() {
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/").toString())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApiService::class.java)
    }

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun getExploreData() = runBlocking {
        enqueueResponse("article.json")
        val response =
            service.getAllExploreNews(
                query = "apple",
                sortBy = "popularity",
                pageSize = 5,
                page = 1
            )
                .body()
                ?: return@runBlocking

        mockWebServer.takeRequest()

        assertThat(response, notNullValue())
        assertThat(response.status, `is`("ok"))

        assertThat(response.totalResults, `is`(88794))

        val articles = response.articles
        assertThat(articles, notNullValue())

        val article1 = articles[0]
        assertThat(article1, notNullValue())
        assertThat(article1.author, `is`("Brendan Hesse"))
        assertThat(article1.title, `is`("How to Watch Apple TV's New MTV-Style Music Channel"))
        assertThat(
            article1.description,
            `is`("Apple just launched Apple Music TV, a new Apple TV channel that plays round-the-clock music videos, live concerts, and other exclusive music-based content. It’s a free add-on for anyone with the Apple Music or Apple TV apps, though it’s exclusive to the US fo…")
        )
        assertThat(article1.publishedAt, `is`("2020-10-21T14:30:00Z"))
        assertThat(
            article1.url,
            `is`("https://lifehacker.com/how-to-watch-apple-tvs-new-mtv-style-music-channel-1845429371")
        )
    }

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun getSourcesData() = runBlocking {
        enqueueResponse("source.json")
        val response =
            service.getAllSources(
            )
                .body()
                ?: return@runBlocking

        mockWebServer.takeRequest()

        assertThat(response, notNullValue())
        assertThat(response.status, `is`("ok"))

        val sources = response.sources
        assertThat(sources, notNullValue())

        val source = sources?.get(0)
        assertThat(source, notNullValue())
        assertThat(source?.id, `is`("google-news-in",))
        assertThat(source?.name, `is`("Google News (India)"))
        assertThat(
            source?.description,
            `is`("Comprehensive, up-to-date India news coverage, aggregated from sources all over the world by Google News.")
        )
        assertThat(source?.url, `is`("https://news.google.com"))
        assertThat(source?.language, `is`("en"))
        assertThat(source?.country, `is`("in"))


    }
}