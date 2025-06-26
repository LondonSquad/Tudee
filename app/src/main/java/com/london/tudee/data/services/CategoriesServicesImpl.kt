package com.london.tudee.data.services

import com.london.tudee.data.local.roomdb.dao.CategoryDao
import com.london.tudee.data.mappers.convertToCategory
import com.london.tudee.data.mappers.convertToCategoryDto
import com.london.tudee.domain.entities.Category
import com.london.tudee.domain.services.CategoryService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CategoriesServicesImpl(
    private val categoryDao: CategoryDao
) : CategoryService {

    override suspend fun edit(service: Category) = categoryDao.update(service.convertToCategoryDto())

    override suspend fun delete(service: Category) = categoryDao.delete(service.convertToCategoryDto())


    override suspend fun getById(id: Int): Category = categoryDao.getById(id).convertToCategory()


    override fun getIconResById(id: Int): String = categoryDao.getIconResById(id)

    override suspend fun add(service: Category) {
        val category = service.copy(id = 0)
        return categoryDao.insert(category.convertToCategoryDto())
    }

    override suspend fun getAll(): Flow<List<Category>> {
        return categoryDao.getAll().map { categoryDtoList ->
            categoryDtoList.map { categoryDto -> categoryDto.convertToCategory() }
        }
    }
}
