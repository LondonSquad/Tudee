package com.london.tudee.di

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.london.tudee.data.local.roomdb.TudeeDatabase
import com.london.tudee.data.local.roomdb.defaultCategory
import com.london.tudee.data.mappers.convertToCategoryDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

const val DATABASE_NAME = "TudeeDatabase"

val databaseModule = module {
    single {
        Room.databaseBuilder(androidContext(), TudeeDatabase::class.java, DATABASE_NAME)
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    CoroutineScope(Dispatchers.IO).launch {
                        val dao = get<TudeeDatabase>().categoryDao()
                        defaultCategory(androidContext()).forEach { category ->
                            dao.insert(category.convertToCategoryDto())
                        }
                    }
                }
            }).build()
    }
    single { get<TudeeDatabase>().taskDao() }
    single { get<TudeeDatabase>().categoryDao() }
}