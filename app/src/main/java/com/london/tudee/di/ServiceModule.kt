package com.london.tudee.di

import com.london.tudee.data.services.CategoriesServicesImpl
import com.london.tudee.data.services.TasksServicesImpl
import com.london.tudee.domain.services.CategoryService
import com.london.tudee.domain.services.TaskService
import org.koin.dsl.module

val serviceModule = module {
    single<CategoryService> { CategoriesServicesImpl(get()) }
    single<TaskService> { TasksServicesImpl(get()) }
}