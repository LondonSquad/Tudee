package com.london.tudee.di

import com.london.tudee.presentation.screens.task.add_edit_task_bottom_sheet.AddOrEditTaskViewModel
import com.london.tudee.presentation.screens.task.confirm_delete_task.ConfirmDeleteTaskViewModel
import org.koin.dsl.module

val viewModule = module {
    single { ConfirmDeleteTaskViewModel(get()) }
    single { AddOrEditTaskViewModel(get(),get()) }
}