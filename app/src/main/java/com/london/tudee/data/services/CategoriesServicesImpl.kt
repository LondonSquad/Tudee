package com.london.tudee.data.services

import com.london.tudee.data.local.room_db.TudeeDatabase
import com.london.tudee.data.mappers.convertToCategory
import com.london.tudee.data.mappers.convertToCategoryDto
import com.london.tudee.domain.entities.Category
import com.london.tudee.domain.services.Services
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CategoriesServicesImpl(
    private val db: TudeeDatabase
) : Services<Category> {

    override suspend fun add(service: Category): Boolean {
        return db.categoryDao().insert(service.convertToCategoryDto()) != -1L
    }

    override suspend fun edit(service: Category): Boolean {
        return db.categoryDao().update(service.convertToCategoryDto()) != -1
    }

    override suspend fun delete(service: Category): Boolean {
        return db.categoryDao().delete(service.convertToCategoryDto()) != -1
    }

    override suspend fun getAll(): Flow<List<Category>> {
        return db.categoryDao().getAll().map { list ->
            list.map { it.convertToCategory() }
        }
    }

    override suspend fun getById(id: Int): Category {
        return db.categoryDao().getById(id).convertToCategory()
    }
}