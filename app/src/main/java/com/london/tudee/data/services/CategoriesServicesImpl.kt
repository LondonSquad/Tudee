package com.london.tudee.data.services

import com.london.tudee.data.local.room_db.dao.CategoryDao
import com.london.tudee.data.mappers.convertToCategory
import com.london.tudee.data.mappers.convertToCategoryDto
import com.london.tudee.domain.entities.Category
import com.london.tudee.domain.services.Services
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CategoriesServicesImpl(
    private val categoryDao: CategoryDao
) : Services<Category> {

    override suspend fun add(service: Category) {
        return categoryDao.insert(service.convertToCategoryDto())
    }

    override suspend fun edit(service: Category) {
        return categoryDao.update(service.convertToCategoryDto())
    }

    override suspend fun delete(service: Category) {
        return categoryDao.delete(service.convertToCategoryDto())
    }

    override suspend fun getAll(): Flow<List<Category>> {
        return categoryDao.getAll().map { list ->
            list.map { it.convertToCategory() }
        }
    }

    override suspend fun getById(id: Int): Category {
        return categoryDao.getById(id).convertToCategory()
    }
}