package com.london.tudee.di



import com.london.tudee.presentation.screens.task.task_details.TaskDetailsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { TaskDetailsViewModel(get(), get()) }
}