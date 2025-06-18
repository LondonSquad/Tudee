package com.london.tudee.presentation.screens.tasks

import androidx.annotation.StringRes
import androidx.compose.foundation.background
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
import com.london.tudee.presentation.design_system.theme.ThemePreviews
import com.london.tudee.presentation.design_system.theme.TudeeTheme

@Composable
fun AddOrEditTaskBottomSheet(
    modifier: Modifier = Modifier,
    @StringRes title: Int,
    @StringRes buttonText: Int,
    onDismiss: () -> Unit,
    screenContent: @Composable () -> Unit
) {
    TudeeBottomSheetScreen(
        showBottomSheet = true,
        onDismiss = onDismiss,
        screenContent = { screenContent() },
        bottomSheetContent = {
            AddOrEditTaskDetails(
                modifier = modifier,
                title = title,
                onTitleValueChange = {},
                onDescriptionValueChange = {}
            )
        },
        bottomSheetActions = {
            TudeePrimaryButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(buttonText),
                isDisabled = false,
                onClick = onDismiss,
            )

            Spacer(modifier = Modifier.height(12.dp))

            TudeeSecondaryButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.cancel),
                onClick = onDismiss,
            )
        }
    )
}

@ThemePreviews
@Composable
fun PreviewAddOrEditTaskBottomSheet() {
    TudeeTheme {
        AddOrEditTaskBottomSheet(
            title = R.string.task_title,
            buttonText = R.string.add,
            onDismiss = {},
            screenContent = {

            }
        )
    }
}