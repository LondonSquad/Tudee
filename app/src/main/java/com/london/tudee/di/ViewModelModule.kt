package com.london.tudee.di

import com.london.tudee.presentation.screens.task.add_edit_task_bottom_sheet.AddOrEditTaskViewModel
import com.london.tudee.presentation.screens.home.HomeViewModel
import com.london.tudee.presentation.screens.task.confirm_delete_task.ConfirmDeleteTaskViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    single { ConfirmDeleteTaskViewModel(get()) }
    single { AddOrEditTaskViewModel(get(),get()) }
    viewModel { HomeViewModel(get(), get()) }
}