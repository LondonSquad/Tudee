package com.london.tudee.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.london.tudee.R
import com.london.tudee.presentation.components.buttons.TudeeTextButton
import com.london.tudee.presentation.design_system.theme.ThemePreviews
import com.london.tudee.presentation.design_system.theme.TudeeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePicker(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TudeeTextButton(
                    text = stringResource(R.string.clear),
                    onClick = {
                        onDateSelected(null)
                    },
                    modifier = Modifier
                        .padding(start = 16.dp)
                )

                Spacer(modifier = Modifier.weight(1f))

                TudeeTextButton(
                    text = stringResource(R.string.cancel),
                    onClick = onDismiss,
                    modifier = Modifier.padding(end = 34.dp)
                )

                TudeeTextButton(
                    text = stringResource(R.string.ok),
                    onClick = {
                        onDateSelected(datePickerState.selectedDateMillis)
                        onDismiss()
                    },
                    modifier = Modifier
                        .padding(end = 24.dp)
                )
            }
        },
        colors = DatePickerDefaults.colors(
            containerColor = TudeeTheme.colors.surface
        )
    ) {
        DatePicker(
            state = datePickerState,
            colors = DatePickerDefaults.colors(
                containerColor = TudeeTheme.colors.surface,
                titleContentColor = TudeeTheme.colors.title,
                headlineContentColor = TudeeTheme.colors.title,
                navigationContentColor = TudeeTheme.colors.title,
                weekdayContentColor = TudeeTheme.colors.body,
                dayContentColor = TudeeTheme.colors.body,
                dayInSelectionRangeContentColor = TudeeTheme.colors.onPrimary,
                dayInSelectionRangeContainerColor = TudeeTheme.colors.primary,
                todayContentColor = TudeeTheme.colors.primary,
                todayDateBorderColor = TudeeTheme.colors.primary,
                selectedDayContentColor = TudeeTheme.colors.onPrimary,
                selectedDayContainerColor = TudeeTheme.colors.primary,
                yearContentColor = TudeeTheme.colors.body,
                selectedYearContentColor = TudeeTheme.colors.onPrimary,
                selectedYearContainerColor = TudeeTheme.colors.primary
            )
        )
    }
}

@ThemePreviews
@Composable
fun Prev() {
    TudeeTheme {
        DatePicker(
            onDateSelected = {},
            onDismiss = {}
        )
    }
}
