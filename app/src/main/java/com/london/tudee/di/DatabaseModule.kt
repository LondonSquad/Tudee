package com.london.tudee.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.london.tudee.data.local.roomdb.TudeeDatabase
import com.london.tudee.data.local.roomdb.defaultCategory
import com.london.tudee.data.mappers.convertToCategoryDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

const val DATABASE_NAME = "TudeeDatabase"

@Module
class DatabaseModule {
    @Single
    fun provideDatabase(context: Context): TudeeDatabase {
        return Room.databaseBuilder(context, TudeeDatabase::class.java, DATABASE_NAME)
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    CoroutineScope(Dispatchers.IO).launch {
                        val dao =
                            Room.databaseBuilder(context, TudeeDatabase::class.java, DATABASE_NAME)
                                .build().categoryDao()
                        defaultCategory(context).forEach { category ->
                            dao.insert(category.convertToCategoryDto())
                        }
                    }
                }
            }).build()
    }

    @Single
    fun provideCategoryDao(database: TudeeDatabase) = database.categoryDao()

    @Single
    fun provideTaskDao(database: TudeeDatabase) = database.taskDao()
}