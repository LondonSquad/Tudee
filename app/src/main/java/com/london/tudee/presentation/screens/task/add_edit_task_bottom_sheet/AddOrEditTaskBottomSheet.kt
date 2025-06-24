package com.london.tudee.presentation.screens.task.add_edit_task_bottom_sheet

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import com.london.tudee.presentation.base.HomeInteractions
import com.london.tudee.presentation.components.bottom_sheet.TudeeBottomSheetScreen
import com.london.tudee.presentation.components.buttons.TudeePrimaryButton
import com.london.tudee.presentation.components.buttons.TudeeSecondaryButton

@Composable
fun AddOrEditTaskBottomSheet(
    modifier: Modifier = Modifier,
    screenContent: @Composable () -> Unit,
    uiState: AddOrEditTaskUiState,
    interactions: HomeInteractions
) {

    AnimatedVisibility(
        visible = uiState.showBottomSheet,
        enter = slideInVertically(
            animationSpec = tween(300)
        ) {
            it
        },
        exit = slideOutVertically(
            animationSpec = tween(300)
        ) {
            it
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            TudeeBottomSheetScreen(
                showBottomSheet = uiState.showBottomSheet,
                onDismiss = {
                    interactions.hideBottomSheet()
                },
                screenContent = { screenContent() },
                bottomSheetContent = {
                    AddOrEditTaskDetails(
                        modifier = modifier,
                        title = if (uiState.isEditMode) R.string.edit_task else R.string.add_new_task,
                        uiState = uiState,
                        interactions = interactions,
                        categories = uiState.categories
                    )
                },
                bottomSheetActions = {
                    TudeePrimaryButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = if (uiState.isEditMode) stringResource(R.string.edit_task) else stringResource(
                            R.string.add
                        ),
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
                        onClick = {
                            interactions.hideBottomSheet()
                        },
                    )
                }
            )
        }
    }
}
