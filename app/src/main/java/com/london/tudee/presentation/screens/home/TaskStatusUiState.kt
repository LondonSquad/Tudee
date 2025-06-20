package com.london.tudee.presentation.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.london.tudee.R

data class TaskStatusUiState(
    val title: String = "",
    val subtitle: String = "",
    val emoji: Int = -1,
    val tudeePicture: Int = -1,
)


@Composable
fun getTaskStatus(
    allTasks: Int, doneTasks: Int, inProgressTasks: Int, toDoTasks: Int
): TaskStatusUiState {
    when{
        doneTasks == 0 && inProgressTasks == 0 && toDoTasks == 0 -> {
            return TaskStatusUiState(
                title = stringResource(R.string.Nothing_on_your_list),
                subtitle = stringResource(R.string.Fill_your_day_with_something_awesome_),
                emoji = R.drawable.bad_emoji,
                tudeePicture = R.drawable.tudee_warning
            )
        }

        doneTasks in 1..<allTasks ->  {
            return TaskStatusUiState(
                title = stringResource(R.string.Stay_working),
                subtitle = stringResource(R.string.task_progress),
                emoji = R.drawable.okay_status,
                tudeePicture = R.drawable.tudee_warning
            )
        }

        doneTasks > 0 && doneTasks == allTasks -> {
            return TaskStatusUiState(
                title = stringResource(R.string.Tadaa),
                subtitle = stringResource(R.string.encouragement_message),
                emoji = R.drawable.good_emoji,
                tudeePicture = R.drawable.tudee_motivation
            )
        }

        doneTasks == 0 && inProgressTasks == 0 && toDoTasks == allTasks -> {
            return TaskStatusUiState(
                title = stringResource(R.string.Zero_progress),
                subtitle = stringResource(R.string.blaming_message),
                emoji = R.drawable.poor_emoji,
                tudeePicture = R.drawable.tudee_complment
            )
        }

        else -> return TaskStatusUiState(
            title = "",
            subtitle = "",
            emoji = R.drawable.bad_emoji,
            tudeePicture = R.drawable.tudee_warning
        )
    }
}