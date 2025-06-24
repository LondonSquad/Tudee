package com.london.tudee.di

import com.london.tudee.presentation.screens.home.HomeViewModel
import com.london.tudee.presentation.screens.task.confirm_delete_task.ConfirmDeleteTaskViewModel
import com.london.tudee.presentation.screens.task.taskdetails.TaskDetailsBottomSheetViewModel
import com.london.tudee.presentation.screens.task.view_tasks.CategoryDetailsViewModel
import com.london.tudee.presentation.screens.tasks.TasksScreenViewModel
import com.london.tudee.presentation.screens.categories.CategoriesViewModel
import com.london.tudee.presentation.screens.categories.CreateCategoryViewModel
import com.london.tudee.presentation.screens.categories.EditCategoryScreenViewModel
import com.london.tudee.presentation.screens.categories.crud.CreateCategoryScreenViewModel
import com.london.tudee.presentation.screens.categories.crud.DeleteCategoryScreenViewModel
import com.london.tudee.presentation.screens.onboarding.OnBoardingViewModel
import com.london.tudee.presentation.screens.task.add_edit_task_bottom_sheet.AddOrEditTaskViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import androidx.lifecycle.ViewModel
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.definition.Definition
import org.koin.core.definition.KoinDefinition
import org.koin.core.module.dsl.viewModel as fixedViewModel
import org.koin.core.qualifier.Qualifier

@Module
@ComponentScan("com.london.tudee")
class KoinModule


//
//val viewModelModule = module {
//    viewModelOf(::AddOrEditTaskViewModel)
//    viewModelOf(::TasksScreenViewModel)
//    viewModelOf(::ConfirmDeleteTaskViewModel)
//    viewModelOf(::TaskDetailsBottomSheetViewModel)
//    viewModelOf(::HomeViewModel)
//    viewModelOf(::CategoryDetailsViewModel)
//    viewModelOf(::CategoriesViewModel)
//    viewModelOf(::CreateCategoryViewModel)
//    viewModelOf(::EditCategoryScreenViewModel)
//    viewModelOf(::CreateCategoryScreenViewModel)
//    viewModelOf(::DeleteCategoryScreenViewModel)
//    viewModelOf(::OnBoardingViewModel)
//}
//
//
//inline fun <reified T : ViewModel> Module.viewModel(
//    qualifier: Qualifier? = null,
//    noinline definition: Definition<T>
//): KoinDefinition<T> = fixedViewModel(qualifier = qualifier, definition = definition)
