package com.london.tudee.data.local.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.london.tudee.data.local.roomdb.converter.Converters
import com.london.tudee.data.local.roomdb.dao.CategoryDao
import com.london.tudee.data.local.roomdb.dao.TaskDao
import com.london.tudee.data.local.roomdb.dto.CategoryDto
import com.london.tudee.data.local.roomdb.dto.TaskDto

@Database(
    entities = [TaskDto::class, CategoryDto::class],
    version = DataBaseVersion.VERSION_1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class TudeeDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun categoryDao(): CategoryDao
}
private object DataBaseVersion {
    const val VERSION_1 = 1
}