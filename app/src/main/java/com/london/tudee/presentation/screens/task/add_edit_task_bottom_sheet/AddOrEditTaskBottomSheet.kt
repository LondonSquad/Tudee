package com.london.tudee.presentation.screens.task.add_edit_task_bottom_sheet

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import com.london.tudee.presentation.base.BaseCreateTaskInteractions
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
    uiState: AddOrEditTaskUiState,
    interactions: BaseCreateTaskInteractions
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
                        title = title,
                        uiState = uiState,
                        interactions = interactions,
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
                            interactions.saveTask()
                            Log.d("AddOrEditTaskBottomSheet", "AddOrEditTaskBottomSheet: $uiState")
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
