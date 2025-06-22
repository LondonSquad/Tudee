package com.london.tudee.presentation.screens.task.add_edit_task_bottom_sheet

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.london.tudee.R
import com.london.tudee.presentation.components.SnackBar
import com.london.tudee.presentation.components.bottom_sheet.TudeeBottomSheetScreen
import com.london.tudee.presentation.components.buttons.TudeePrimaryButton
import com.london.tudee.presentation.components.buttons.TudeeSecondaryButton
import com.london.tudee.presentation.design_system.theme.ThemePreviews
import com.london.tudee.presentation.design_system.theme.TudeeTheme
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddOrEditTaskBottomSheet(
    modifier: Modifier = Modifier,
    taskId: Int? = null,
    uiState: AddOrEditTaskUiState,
    interaction: AddOrEditInteraction
) {

    Box(modifier = Modifier.fillMaxSize()) {
        TudeeBottomSheetScreen(
            showBottomSheet = uiState.showBottomSheet,
            onDismiss = {
                interaction.hideBottomSheet()
            },
            screenContent = { },
            bottomSheetContent = {
                AddOrEditTaskDetails(
                    modifier = modifier,
                    title = if(taskId != null) R.string.edit_task else R.string.add_new_task,
                    interaction = interaction,
                    categories = uiState.categories,
                    uiState = uiState
                )
            },
            bottomSheetActions = {
                TudeePrimaryButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = if (taskId != null) stringResource(R.string.edit_task) else stringResource(R.string.add),
                    isDisabled = !uiState.isFormValid || uiState.isLoading,
                    isLoading = uiState.isLoading,
                    onClick = {
                        interaction.saveTask()
                    },
                )

                Spacer(modifier = Modifier.height(12.dp))

                TudeeSecondaryButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.cancel),
                    onClick = {
                        interaction.hideBottomSheet()
                    },
                )
            }
        )

        if (uiState.stateMessage != null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopCenter
            ) {
                when {
                    uiState.stateMessage != null -> {
                        SnackBar(
                            modifier = Modifier.offset(y = 56.dp),
                            message = if (uiState.isEditMode)
                                R.string.edit_task_successfully
                            else
                                R.string.add_task_successfully,
                            iconPainter = painterResource(id = R.drawable.snack_bar_container),
                            iconTint = TudeeTheme.colors.greenAccent
                        )
                    }
                    uiState.stateMessage != null -> {
                        SnackBar(
                            modifier = Modifier.offset(y = 56.dp),
                            message = R.string.some_error_happened,
                            iconPainter = painterResource(id = R.drawable.snack_bar_error),
                            iconTint = TudeeTheme.colors.errorVariant,
                        )
                    }
                }
            }

            LaunchedEffect(uiState.stateMessage) {
                delay(3000)
                interaction.clearMessages()
            }
        }
    }
}

//@ThemePreviews
//@Composable
//fun PreviewAddOrEditTaskBottomSheet() {
//    TudeeTheme {
//        AddOrEditTaskBottomSheet(
//            title = R.string.task_title,
//            buttonText = R.string.add,
//            screenContent = {},
//            uiState = AddOrEditTaskUiState(),
//            interaction = object : AddOrEditInteraction {
//
//            }
//        )
//    }
//}