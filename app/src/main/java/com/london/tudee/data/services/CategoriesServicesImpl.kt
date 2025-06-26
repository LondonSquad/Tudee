package com.london.tudee.data.services

import com.london.tudee.data.exception.AddCategoryException
import com.london.tudee.data.exception.DeleteCategoryException
import com.london.tudee.data.exception.EditCategoryException
import com.london.tudee.data.exception.GetAllCategoriesException
import com.london.tudee.data.exception.GetCategoryIconException
import com.london.tudee.data.exception.CategoryNotFoundException

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

    override suspend fun add(service: Category) {
        return runCatching {
            val category = service.copy(id = 0)
            categoryDao.insert(category.convertToCategoryDto())
        }.getOrElse {
            throw AddCategoryException()
        }
    }

    override suspend fun edit(service: Category) {
        return runCatching {
            categoryDao.update(service.convertToCategoryDto())
        }.getOrElse {
            throw EditCategoryException()
        }
    }

    override suspend fun delete(service: Category) {
        return runCatching {
            categoryDao.delete(service.convertToCategoryDto())
        }.getOrElse {
            throw DeleteCategoryException()
        }
    }

    override suspend fun getAll(): Flow<List<Category>> {
        return runCatching {
            categoryDao.getAll().map { categoryDtoList ->
                categoryDtoList.map { categoryDto -> categoryDto.convertToCategory() }
            }
        }.getOrElse {
            throw GetAllCategoriesException()
        }
    }

    override suspend fun getById(id: Int): Category {
        return runCatching {
            categoryDao.getById(id).convertToCategory()
        }.getOrElse {
            throw CategoryNotFoundException()
        }
    }


    override fun getIconResById(id: Int): String {
        return runCatching {
            categoryDao.getIconResById(id)
        }.getOrElse {
            throw GetCategoryIconException()
        }
    }
}