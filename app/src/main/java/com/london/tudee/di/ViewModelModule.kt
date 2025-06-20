package com.london.tudee.di

import com.london.tudee.presentation.screens.home.HomeViewModel
import com.london.tudee.presentation.screens.task.confirm_delete_task.ConfirmDeleteTaskViewModel
import com.london.tudee.presentation.screens.task.view_tasks.EditTaskViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    single { ConfirmDeleteTaskViewModel(get()) }
    viewModel { HomeViewModel(get(), get()) }
    viewModel { EditTaskViewModel(get(), get()) }
}