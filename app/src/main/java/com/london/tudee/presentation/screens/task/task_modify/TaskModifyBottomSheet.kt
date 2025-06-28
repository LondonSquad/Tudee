package com.london.tudee.presentation.screens.task.task_modify

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.london.tudee.R
import com.london.tudee.presentation.components.buttons.TudeePrimaryButton
import com.london.tudee.presentation.components.buttons.TudeeSecondaryButton
import com.london.tudee.presentation.design_system.theme.TudeeTheme
import com.london.tudee.presentation.screens.task.TaskModifyInteractions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskModifyBottomSheet(
    modifier: Modifier = Modifier,
    uiState: TaskModifyUiState,
    interactions: TaskModifyInteractions,
    onShowDatePicker: () -> Unit,
    onHideDatePicker: () -> Unit,
    onDismissRequest: () -> Unit
) {

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = true
        ),
        windowInsets = WindowInsets(0),
        dragHandle = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(TudeeTheme.colors.surface),
                contentAlignment = Alignment.Center
            ) {
                BottomSheetDefaults.DragHandle()
            }
        },
        containerColor = TudeeTheme.colors.surface
    ) {
        TaskModifyDetails(
            modifier = modifier,
            title = if (uiState.isEditMode) R.string.edit_task else R.string.add_new_task,
            uiState = uiState,
            interactions = interactions,
            categories = uiState.categories,
            onShowDatePicker = onShowDatePicker,
            onHideDatePicker = onHideDatePicker
        )

        TudeePrimaryButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, start = 16.dp, end = 16.dp),
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp, start = 16.dp, end = 16.dp),
            text = stringResource(R.string.cancel),
            onClick = onDismissRequest,
        )
    }
}