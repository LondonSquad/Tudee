package com.london.tudee.di

import com.london.tudee.presentation.screens.home.HomeViewModel
import com.london.tudee.presentation.screens.task.confirm_delete_task.ConfirmDeleteTaskViewModel
import com.london.tudee.presentation.screens.category.category_details.CategoryDetailsViewModel
import com.london.tudee.presentation.screens.tasks.TasksScreenViewModel
import com.london.tudee.presentation.screens.category.CategoriesViewModel
import com.london.tudee.presentation.screens.category.edit_category.EditCategoryScreenViewModel
import com.london.tudee.presentation.screens.category.create_category.CreateCategoryScreenViewModel
import com.london.tudee.presentation.screens.category.delete_category.DeleteCategoryScreenViewModel
import com.london.tudee.presentation.screens.onboarding.OnBoardingViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::TasksScreenViewModel)
    viewModelOf(::ConfirmDeleteTaskViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::CategoryDetailsViewModel)
    viewModelOf(::CategoriesViewModel)
    viewModelOf(::EditCategoryScreenViewModel)
    viewModelOf(::CreateCategoryScreenViewModel)
    viewModelOf(::DeleteCategoryScreenViewModel)
    viewModelOf(::OnBoardingViewModel)
}