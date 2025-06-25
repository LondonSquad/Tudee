package com.london.tudee.di

import com.london.tudee.presentation.screens.home.HomeViewModel
import com.london.tudee.presentation.screens.task.view_tasks.CategoryDetailsViewModel
import com.london.tudee.presentation.screens.tasks.TasksScreenViewModel
import com.london.tudee.presentation.screens.categories.CategoriesViewModel
import com.london.tudee.presentation.screens.categories.CreateCategoryViewModel
import com.london.tudee.presentation.screens.categories.EditCategoryScreenViewModel
import com.london.tudee.presentation.screens.categories.crud.CreateCategoryScreenViewModel
import com.london.tudee.presentation.screens.categories.crud.DeleteCategoryScreenViewModel
import com.london.tudee.presentation.screens.onboarding.OnBoardingViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::TasksScreenViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::CategoryDetailsViewModel)
    viewModelOf(::CategoriesViewModel)
    viewModelOf(::CreateCategoryViewModel)
    viewModelOf(::EditCategoryScreenViewModel)
    viewModelOf(::CreateCategoryScreenViewModel)
    viewModelOf(::DeleteCategoryScreenViewModel)
    viewModelOf(::OnBoardingViewModel)
}