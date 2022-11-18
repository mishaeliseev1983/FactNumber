package com.melyseev.factnumber.data.cache

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.melyseev.factnumber.data.cache.NumberCache.Companion.TABLE_NUMBERS
import java.util.*

@Entity(tableName = TABLE_NUMBERS)
data class NumberCache(
    @PrimaryKey @ColumnInfo(name = ID) val id: String,
    @ColumnInfo(name = FACT) val fact: String,
    @ColumnInfo(name = DATE) val date: Long,
    ){
    companion object {
        const val TABLE_NUMBERS= "TABLE_NUMBERS"
        const val ID = "ID"
        const val FACT = "FACT"
        const val DATE = "DATE"
    }
}

