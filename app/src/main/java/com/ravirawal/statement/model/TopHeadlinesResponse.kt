package com.ravirawal.statement.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TopHeadlinesResponse(
    @field:SerializedName("articles")
    var articles: List<TopHeadlines>,
    @field:SerializedName("status")
    val status: String,
    @field:SerializedName("totalResults")
    val totalResults: Int
) : Serializable

@Entity(
    tableName = "top_headlines"
)
data class TopHeadlines(
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

    @ColumnInfo(name = "source")
    @field:SerializedName("source")
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

    companion object CREATOR : Parcelable.Creator<TopHeadlines> {
        override fun createFromParcel(parcel: Parcel): TopHeadlines {
            return TopHeadlines(parcel)
        }

        override fun newArray(size: Int): Array<TopHeadlines?> {
            return arrayOfNulls(size)
        }
    }
}
