package com.ravirawal.statement.data_source.database

import androidx.room.TypeConverter
import com.ravirawal.statement.model.Source

class Converters {

    @TypeConverter
    fun fromSource(source: Source): String = source.id

    @TypeConverter
    fun toSource(id: String): Source =
        Source(id)
}