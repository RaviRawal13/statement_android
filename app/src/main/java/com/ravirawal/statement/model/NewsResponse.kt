package com.ravirawal.statement.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.*
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class NewsResponse(
    var articles: ArrayList<Article>,
    val status: String,
    val totalResults: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.createTypedArrayList(Article)?: arrayListOf(),
        parcel.readString() ?: "",
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(articles)
        parcel.writeString(status)
        parcel.writeInt(totalResults)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NewsResponse> {
        override fun createFromParcel(parcel: Parcel): NewsResponse {
            return NewsResponse(parcel)
        }

        override fun newArray(size: Int): Array<NewsResponse?> {
            return arrayOfNulls(size)
        }
    }
}

@Entity(
    tableName = "explore_news",
    indices = [Index(
        value = ["title", "publishedAt"],
        unique = true
    )]
)
data class Article(
    @ColumnInfo(name = "author")
    @field:SerializedName("author")
    var author: String? = null,

    @ColumnInfo(name = "content")
    @field:SerializedName("content")
    val content: String? = null,

    @ColumnInfo(name = "description")
    @field:SerializedName("description")
    val description: String? = null,

    @ColumnInfo(name = "publishedAt")
    @field:SerializedName("publishedAt")
    var publishedAt: String? = null,

    @Embedded
    val source: Source? = null,

    @ColumnInfo(name = "title")
    @field:SerializedName("title")
    val title: String? = null,

    @PrimaryKey
    @ColumnInfo(name = "url")
    @field:SerializedName("url")
    val url: String,

    @ColumnInfo(name = "urlToImage")
    @field:SerializedName("urlToImage")
    val urlToImage: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(Source::class.java.classLoader),
        parcel.readString(),
        parcel.readString() ?: "",
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(author)
        parcel.writeString(content)
        parcel.writeString(description)
        parcel.writeString(publishedAt)
        parcel.writeParcelable(source, flags)
        parcel.writeString(title)
        parcel.writeString(url)
        parcel.writeString(urlToImage)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Article> {
        override fun createFromParcel(parcel: Parcel): Article {
            return Article(parcel)
        }

        override fun newArray(size: Int): Array<Article?> {
            return arrayOfNulls(size)
        }
    }
}


data class Source(
    @ColumnInfo(name = "source_id")
    val id: String,
//    @ColumnInfo(name = "source_name")
//    val name: String
) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString() ?: "") {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Source> {
        override fun createFromParcel(parcel: Parcel): Source {
            return Source(parcel)
        }

        override fun newArray(size: Int): Array<Source?> {
            return arrayOfNulls(size)
        }
    }
}