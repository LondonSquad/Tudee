package com.london.tudee.di

import android.os.Build
import androidx.annotation.RequiresApi
import com.london.tudee.presentation.screens.task.confirm_delete_task.ConfirmDeleteTaskViewModel
import com.london.tudee.presentation.screens.tasks.TasksScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@RequiresApi(Build.VERSION_CODES.O)
val viewModule = module {
    single { ConfirmDeleteTaskViewModel(get()) }
    viewModel { TasksScreenViewModel(get(), get()) }
}