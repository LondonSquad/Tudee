package com.london.tudee.presentation.screens.task.add_edit_task_bottom_sheet

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
                onTitleValueChange = {
                    viewModel.updateTitle(it)
                },
                onDescriptionValueChange = {
                    viewModel.updateDescription(it)
                },
                onSelectedDateChange = {
                    viewModel::updateSelectedDate
                },
                onSelectedPriorityChange = {
                    viewModel::updateSelectedPriority
                },
                onSelectedCategoryChange = {
                    viewModel::updateSelectedCategory
                },

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
    uiState.successMessage?.let {
        SnackBar(
            modifier = Modifier.offset(y = (-56).dp),
            message = R.string.add_task_successfully,
            iconPainter = painterResource(id = R.drawable.snack_bar_container)
        )
    }
    uiState.errorMessage?.let {
        SnackBar(
            modifier = Modifier.offset(y = (-56).dp),
            message = R.string.some_error_happened,
            iconPainter = painterResource(id = R.drawable.snack_bar_error),
            iconTint = TudeeTheme.colors.errorVariant,
        )
    }
}

@ThemePreviews
@Composable
fun PreviewAddOrEditTaskBottomSheet() {
    TudeeTheme {
        AddOrEditTaskBottomSheet(
            title = R.string.task_title,
            buttonText = R.string.add,
            screenContent = {

            }
        )
    }
}