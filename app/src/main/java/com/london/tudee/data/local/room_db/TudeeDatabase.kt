package com.london.tudee.data.local.room_db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.london.tudee.R
import com.london.tudee.data.local.room_db.converter.Converters
import com.london.tudee.data.local.room_db.dao.CategoryDao
import com.london.tudee.data.local.room_db.dao.TaskDao
import com.london.tudee.data.local.room_db.entities.CategoryDto
import com.london.tudee.data.local.room_db.entities.TaskDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [TaskDto::class, CategoryDto::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class TudeeDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun categoryDao(): CategoryDao

    companion object {
        @Volatile
        private var instance: TudeeDatabase? = null
        private const val DATABASE_NAME = "TudeeDatabase"

        fun getInstance(context: Context): TudeeDatabase {
            return instance ?: synchronized(this) {
                buildDatabase(context.applicationContext).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): TudeeDatabase {
            return Room.databaseBuilder(
                context, TudeeDatabase::class.java, DATABASE_NAME
            ).addCallback(object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    CoroutineScope(Dispatchers.IO).launch {
                        val builtDb = getInstance(context)
                        insertSampleTask(builtDb)
                    }
                }
            }).build()
        }

        private fun insertSampleTask(db: TudeeDatabase) {
            CoroutineScope(Dispatchers.IO).launch {
                defaultCategory.forEach {
                    db.categoryDao().insert(it)
                }
            }
        }
    }
}
