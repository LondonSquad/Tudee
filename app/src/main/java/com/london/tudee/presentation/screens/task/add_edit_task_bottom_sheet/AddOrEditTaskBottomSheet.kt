package com.london.tudee.presentation.screens.task.add_edit_task_bottom_sheet

import android.util.Log
import androidx.annotation.StringRes
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
    @StringRes title: Int,
    @StringRes buttonText: Int,
    screenContent: @Composable () -> Unit,
    viewModel: AddOrEditTaskViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Box(modifier = Modifier.fillMaxSize()) {
        TudeeBottomSheetScreen(
            showBottomSheet = uiState.showBottomSheet,
            onDismiss = {
                viewModel.hideBottomSheet()
            },
            screenContent = { screenContent() },
            bottomSheetContent = {
                AddOrEditTaskDetails(
                    modifier = modifier,
                    title = title,
                    viewModel = viewModel,
                    categories = uiState.categories
                )
            },
            bottomSheetActions = {
                TudeePrimaryButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(buttonText),
                    isDisabled = !uiState.isFormValid || uiState.isLoading,
                    isLoading = uiState.isLoading,
                    onClick = {
                        viewModel.saveTask()
                        Log.d("AddOrEditTaskBottomSheet", "AddOrEditTaskBottomSheet: $uiState")
                    },
                )

                Spacer(modifier = Modifier.height(12.dp))

                TudeeSecondaryButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.cancel),
                    onClick = {
                        viewModel.hideBottomSheet()
                    },
                )
            }
        )

        // Show SnackBar at the top of the screen
        if (uiState.successMessage != null || uiState.errorMessage != null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopCenter
            ) {
                when {
                    uiState.successMessage != null -> {
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
                    uiState.errorMessage != null -> {
                        SnackBar(
                            modifier = Modifier.offset(y = 56.dp),
                            message = R.string.some_error_happened,
                            iconPainter = painterResource(id = R.drawable.snack_bar_error),
                            iconTint = TudeeTheme.colors.errorVariant,
                        )
                    }
                }
            }

            // Auto-hide snackbar after 3 seconds
            LaunchedEffect(uiState.successMessage, uiState.errorMessage) {
                delay(3000)
                viewModel.clearMessages()
            }
        }
    }
}

@ThemePreviews
@Composable
fun PreviewAddOrEditTaskBottomSheet() {
    TudeeTheme {
        AddOrEditTaskBottomSheet(
            title = R.string.task_title,
            buttonText = R.string.add,
            screenContent = {}
        )
    }
}