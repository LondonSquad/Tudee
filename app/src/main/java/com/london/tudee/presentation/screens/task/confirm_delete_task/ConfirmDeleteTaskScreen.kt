package com.london.tudee.presentation.screens.task.confirm_delete_task

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.london.tudee.R
import com.london.tudee.presentation.components.bottom_sheet.TudeeBottomSheetScreen
import com.london.tudee.presentation.components.buttons.TudeeNegativeButton
import com.london.tudee.presentation.components.buttons.TudeeSecondaryButton
import com.london.tudee.presentation.design_system.theme.ThemePreviews
import com.london.tudee.presentation.design_system.theme.TudeeTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun ConfirmDeleteTaskScreen(
    viewModel: ConfirmDeleteTaskViewModel = koinViewModel(),
    onTaskDeleted: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    if (!uiState.isVisible) return

    TudeeBottomSheetScreen(
        showBottomSheet = true,
        onDismiss = { viewModel.dismissDialog() },
        screenContent = {},
        bottomSheetContent = {
            ConfirmDeleteBottomSheetContent()
        },
        bottomSheetActions = {
            ConfirmDeleteBottomSheetActions(
                onDelete = {
                    viewModel.deleteTask(
                        onSuccess = {
                            viewModel.dismissDialog()
                            onTaskDeleted()
                        }
                    )
                },
                onCancel = {
                    viewModel.dismissDialog()
                }
            )
        }
    )
}

@Composable
private fun ConfirmDeleteBottomSheetContent() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(TudeeTheme.colors.surface)
            .padding(bottom = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.delete_task_title),
            style = TudeeTheme.typography.headlineMedium,
            color = TudeeTheme.colors.title,
            modifier = Modifier.align(Alignment.Start)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = stringResource(R.string.delete_task_message),
            style = TudeeTheme.typography.bodyMedium,
            color = TudeeTheme.colors.body,
            modifier = Modifier.align(Alignment.Start)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Image(
            painter = painterResource(R.drawable.tudee_delete),
            contentDescription = null,
            modifier = Modifier.size(width = 107.dp, height = 100.dp)
        )
    }
}

@Composable
private fun ConfirmDeleteBottomSheetActions(
    onDelete: () -> Unit,
    onCancel: () -> Unit
) {
    Column(
        modifier = Modifier.background(TudeeTheme.colors.surface)
    ) {
        TudeeNegativeButton(
            text = stringResource(R.string.delete),
            onClick = onDelete,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))
        TudeeSecondaryButton(
            text = stringResource(R.string.cancel),
            onClick = onCancel,
            modifier = Modifier.fillMaxWidth()
        )
    }

    //@ThemePreviews
    @Composable
    fun ConfirmDeleteTaskScreenPreview() {
        TudeeTheme {
//        ConfirmDeleteTaskScreen(
//            onDeleteConfirmed = {},
//        )
        }
    }

    //@ThemePreviews
    @Composable
    fun ConfirmDeleteBottomSheetContentPreview() {
        TudeeTheme {
            ConfirmDeleteBottomSheetContent()
        }
    }

   // @ThemePreviews
    @Composable
    fun ConfirmDeleteBottomSheetActionsPreview() {
        TudeeTheme {
            ConfirmDeleteBottomSheetActions(
                onDelete = {},
                onCancel = {}
            )
        }
    }
}
