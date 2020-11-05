package com.ravirawal.statement.util

import com.ravirawal.statement.model.Article
import com.ravirawal.statement.model.Source
import com.ravirawal.statement.model.TopHeadlines

object TopHeadlinesFactory {


    fun fetchTopHeadlinesList(): List<TopHeadlines> {
        val article = TopHeadlines(
            author = "Author name",
            content = "Content",
            description = "Content description",
            publishedAt = "2020-11-02T00:03:31.593",
            source = Source("bbc-news"),
            title = "Content title",
            url = "www.google.com",
            urlToImage = "www.google.com",
        )

        val article2 = TopHeadlines(
            author = "Author name2",
            content = "Content2",
            description = "Content description2",
            publishedAt = "2020-11-01T00:03:31.593",
            source = Source("bbc-news"),
            title = "Content title2",
            url = "www.google2.com",
            urlToImage = "www.google2.com",
        )

        return arrayListOf(article, article2)
    }
}