package com.london.tudee.presentation.screens.tasks

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
) {
    var showBottomSheet by remember { mutableStateOf(false) }

    TudeeBottomSheetScreen(
        showBottomSheet = true,
        onDismiss = { showBottomSheet = true },
        screenContent = {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .background(TudeeTheme.colors.background),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

            }
        },
        bottomSheetContent = {
            AddOrEditTaskDetails(
                title = title,
            )
        },
        bottomSheetActions = {

            TudeePrimaryButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(buttonText),
                isDisabled = title.equals(null),
                onClick = { },
            )

            Spacer(modifier = Modifier.height(12.dp))

            TudeeSecondaryButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.cancel),
                onClick = { },
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
        )
    }
}