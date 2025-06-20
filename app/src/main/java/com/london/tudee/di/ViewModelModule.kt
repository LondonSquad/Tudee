package com.london.tudee.di

import android.os.Build
import androidx.annotation.RequiresApi
import com.london.tudee.presentation.screens.home.HomeViewModel

import com.london.tudee.presentation.screens.task.confirm_delete_task.ConfirmDeleteTaskViewModel
import com.london.tudee.presentation.screens.tasks.TasksScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import com.london.tudee.presentation.screens.task.view_tasks.EditTaskViewModel
import org.koin.core.module.dsl.viewModel
import com.london.tudee.presentation.screens.task.task_details.TaskDetailsViewModel
import org.koin.dsl.module

@RequiresApi(Build.VERSION_CODES.O)
val viewModelModule = module {
    single { ConfirmDeleteTaskViewModel(get()) }
    viewModel { TasksScreenViewModel(get(), get()) }
    viewModel { ConfirmDeleteTaskViewModel(get()) }
    viewModel { TaskDetailsViewModel(get(), get()) }
    viewModel { HomeViewModel(get(), get()) }
    viewModel { EditTaskViewModel(get(), get()) }
}