package com.london.tudee.presentation.screens.task.task_modify

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.london.tudee.R
import com.london.tudee.presentation.components.bottom_sheet.TudeeBottomSheetScreen
import com.london.tudee.presentation.components.buttons.TudeePrimaryButton
import com.london.tudee.presentation.components.buttons.TudeeSecondaryButton
import com.london.tudee.presentation.screens.task.TaskModifyInteractions

@Composable
fun TaskModifyBottomSheet(
    modifier: Modifier = Modifier,
    screenContent: @Composable () -> Unit,
    uiState: TaskModifyUiState,
    interactions: TaskModifyInteractions,
    onHideBottomSheet: () -> Unit,
    onShowDatePicker: () -> Unit,
    onHideDatePicker: () -> Unit,
) {

    Box(modifier = Modifier.fillMaxSize()) {
        TudeeBottomSheetScreen(
            showBottomSheet = uiState.showBottomSheet,
            onDismiss = onHideBottomSheet,
            screenContent = { screenContent() },
            bottomSheetContent = {
                TaskModifyDetails(
                    modifier = modifier,
                    title = if (uiState.isEditMode) R.string.edit_task else R.string.add_new_task,
                    uiState = uiState,
                    interactions = interactions,
                    categories = uiState.categories,
                    onShowDatePicker = onShowDatePicker,
                    onHideDatePicker = onHideDatePicker
                )
            },
            bottomSheetActions = {
                TudeePrimaryButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = if (uiState.isEditMode) stringResource(R.string.edit_task)
                    else stringResource(R.string.add),
                    isDisabled = !uiState.isFormValid || uiState.isLoading,
                    isLoading = uiState.isLoading,
                    onClick = {
                        interactions.saveTask()
                    },
                )

                Spacer(modifier = Modifier.height(12.dp))

                TudeeSecondaryButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.cancel),
                    onClick = onHideBottomSheet,
                )
            }
        )
    }
}