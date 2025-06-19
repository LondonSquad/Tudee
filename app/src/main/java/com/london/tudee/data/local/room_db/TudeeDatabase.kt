package com.london.tudee.data.local.room_db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.london.tudee.data.local.room_db.converter.Converters
import com.london.tudee.data.local.room_db.dao.CategoryDao
import com.london.tudee.data.local.room_db.dao.TaskDao
import com.london.tudee.data.local.room_db.dto.CategoryDto
import com.london.tudee.data.local.room_db.dto.TaskDto

@Database(entities = [TaskDto::class, CategoryDto::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class TudeeDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun categoryDao(): CategoryDao
}