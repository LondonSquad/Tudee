package com.london.tudee.di

import com.london.tudee.data.local.roomdb.dao.CategoryDao
import com.london.tudee.data.local.roomdb.dao.TaskDao
import com.london.tudee.data.services.CategoriesServicesImpl
import com.london.tudee.data.services.TasksServicesImpl
import com.london.tudee.domain.services.CategoryService
import com.london.tudee.domain.services.TaskService
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single


@Module(includes = [DatabaseModule::class])
@ComponentScan("com.london.tudee")
class AppModule{
    @Single
    fun provideCategoryService(dao: CategoryDao): CategoryService = CategoriesServicesImpl(dao)
    @Single
    fun provideTaskService(dao: TaskDao): TaskService = TasksServicesImpl(dao)
}