package com.ravirawal.statement.util

import com.ravirawal.statement.model.SourcesItem

object SourcesFactory {


    fun fetchSourcesList(): List<SourcesItem> {
        val sourcesItem = SourcesItem(
            country = "in",
            name = "BBC NEWS",
            description = "bbc news channel description",
            language = "en",
            id="bbc-news",
            url = "www.bbc.com"
            )

        val sourcesItem2 = SourcesItem(
            country = "us",
            name = "ABC NEWS",
            description = "ABC news channel description",
            language = "en",
            id="abc-news",
            url = "www.abc.com"
        )


        return arrayListOf(sourcesItem, sourcesItem2)
    }
}