package com.london.tudee.di


import com.london.tudee.presentation.screens.task.confirm_delete_task.ConfirmDeleteTaskViewModel
import com.london.tudee.presentation.screens.task.task_details.TaskDetailsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { ConfirmDeleteTaskViewModel(get()) }
    viewModel { TaskDetailsViewModel(get(), get()) }
}