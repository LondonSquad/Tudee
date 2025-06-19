package com.london.tudee.di

import com.london.tudee.presentation.screens.task.confirm_delete_task.ConfirmDeleteTaskViewModel
import org.koin.dsl.module

val viewModule = module {
    single { ConfirmDeleteTaskViewModel(get()) }
}