package com.london.tudee.presentation.navigation

import com.london.tudee.domain.entities.Task


sealed class ActiveBottomSheet {
    data class TaskDetails(val task: Task) : ActiveBottomSheet()
    data class EditTask(val task: Task) : ActiveBottomSheet()
    object AddTask : ActiveBottomSheet()
    object DeleteTask : ActiveBottomSheet()
    object AddCategory : ActiveBottomSheet()
    object EditCategory : ActiveBottomSheet()
    object None : ActiveBottomSheet()
}
