package com.london.tudee.di

import com.london.tudee.data.local.roomdb.dao.CategoryDao
import com.london.tudee.data.local.roomdb.dao.TaskDao
import com.london.tudee.data.services.CategoriesServicesImpl
import com.london.tudee.data.services.TasksServicesImpl
import com.london.tudee.domain.services.CategoryService
import com.london.tudee.domain.services.Services
import com.london.tudee.domain.services.TaskService
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
class ServiceModule {
    @Single
    fun categoryServiceInterface(dao: CategoryDao): CategoryService = CategoriesServicesImpl(dao)
    @Single
    fun taskServiceInterface(dao: TaskDao): TaskService = TasksServicesImpl(dao)
}