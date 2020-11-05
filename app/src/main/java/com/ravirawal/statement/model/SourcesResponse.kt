package com.ravirawal.statement.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import org.jetbrains.annotations.NotNull

data class SourcesResponse(

    @field:SerializedName("sources")
    val sources: List<SourcesItem?>? = null,

    @field:SerializedName("status")
    val status: String? = null
)

@Entity(
    tableName = "sources"
)
data class SourcesItem(

    @ColumnInfo(name = "country")
    @field:SerializedName("country")
    val country: String? = null,

    @ColumnInfo(name = "name")
    @field:SerializedName("name")
    val name: String? = null,

    @ColumnInfo(name = "description")
    @field:SerializedName("description")
    val description: String? = null,

    @ColumnInfo(name = "language")
    @field:SerializedName("language")
    val language: String? = null,

    @NotNull
    @PrimaryKey
    @field:SerializedName("id")
    val id: String,

    @ColumnInfo(name = "url")
    @field:SerializedName("url")
    val url: String? = null
)
