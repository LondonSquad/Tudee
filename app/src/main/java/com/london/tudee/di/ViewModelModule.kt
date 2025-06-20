package com.london.tudee.di

import com.london.tudee.presentation.screens.home.HomeViewModel
import android.os.Build
import androidx.annotation.RequiresApi
import com.london.tudee.presentation.screens.task.confirm_delete_task.ConfirmDeleteTaskViewModel
import org.koin.core.module.dsl.viewModel
import com.london.tudee.presentation.screens.tasks.TasksScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    single { ConfirmDeleteTaskViewModel(get()) }
    viewModel { TasksScreenViewModel(get(), get()) }
    viewModel { HomeViewModel(get(), get()) }
}