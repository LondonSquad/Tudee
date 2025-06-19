package com.london.tudee.di

import com.london.tudee.data.services.CategoriesServicesImpl
import com.london.tudee.data.services.TasksServicesImpl
import com.london.tudee.domain.entities.Category
import com.london.tudee.domain.entities.Task
import com.london.tudee.domain.services.CategoryService
import com.london.tudee.domain.services.Services
import com.london.tudee.domain.services.TaskService
import org.koin.dsl.module

val serviceModule = module {
    single<Services<Category>> { CategoriesServicesImpl(get()) }
    single<Services<Task>> { TasksServicesImpl(get()) }
    single<TaskService> { TasksServicesImpl(get()) }
    single<CategoryService> { CategoriesServicesImpl(get()) }
}