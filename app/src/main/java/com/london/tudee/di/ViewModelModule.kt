package com.london.tudee.di

import com.london.tudee.presentation.screens.home.HomeViewModel
import com.london.tudee.presentation.screens.task.confirm_delete_task.ConfirmDeleteTaskViewModel
import com.london.tudee.presentation.screens.task.task_details.TaskDetailsViewModel
import com.london.tudee.presentation.screens.task.view_tasks.EditTaskViewModel
import com.london.tudee.presentation.screens.tasks.TasksScreenViewModel
import com.london.tudee.presentation.screens.categories.CategoriesViewModel
import com.london.tudee.presentation.screens.categories.CreateCategoryViewModel
import com.london.tudee.presentation.screens.categories.EditCategoryScreenViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::TasksScreenViewModel)
    viewModelOf(::ConfirmDeleteTaskViewModel)
    viewModelOf(::TaskDetailsViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::EditTaskViewModel)
    viewModelOf(::CategoriesViewModel)
    viewModelOf(::CreateCategoryViewModel)
    viewModelOf(::EditCategoryScreenViewModel)
}