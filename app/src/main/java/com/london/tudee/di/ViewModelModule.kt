package com.london.tudee.di

import com.london.tudee.presentation.screens.home.HomeViewModel
import com.london.tudee.presentation.screens.onboarding.OnBoardingViewModel
import com.london.tudee.presentation.screens.onboarding.OnboardingPreferences
import com.london.tudee.presentation.screens.task.confirm_delete_task.ConfirmDeleteTaskViewModel
import com.london.tudee.presentation.screens.task.task_details.TaskDetailsViewModel
import com.london.tudee.presentation.screens.task.view_tasks.EditTaskViewModel
import com.london.tudee.presentation.screens.tasks.TasksScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.scope.get
import org.koin.dsl.module

val viewModelModule = module {
    single { ConfirmDeleteTaskViewModel(get()) }
    viewModel { TasksScreenViewModel(get(), get()) }
    viewModel { ConfirmDeleteTaskViewModel(get()) }
    viewModel { TaskDetailsViewModel(get(), get()) }
    viewModel { HomeViewModel(get(), get()) }
    viewModel { EditTaskViewModel(get(), get()) }
    single { OnboardingPreferences(get()) }
    viewModel { OnBoardingViewModel(get()) }

}