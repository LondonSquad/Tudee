package com.london.tudee.presentation.screens.tasks

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.london.tudee.R
import com.london.tudee.presentation.components.TudeeTextField
import com.london.tudee.presentation.components.priority.Priority
import com.london.tudee.presentation.components.priority.PrioritySelector
import com.london.tudee.presentation.design_system.theme.ThemePreviews
import com.london.tudee.presentation.design_system.theme.TudeeTheme

@Composable
fun AddOrEditTaskDetails(
    modifier: Modifier = Modifier,
    @StringRes title: Int,
){
    var selectedPriority by remember { mutableStateOf(Priority.HIGH) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(TudeeTheme.colors.surface)
    ) {
        Text(
            text = stringResource(title),
            style = TudeeTheme.typography.titleLarge,
            color = TudeeTheme.colors.title,
        )
        Spacer(modifier.height(12.dp))
        TudeeTextField(
            icon = R.drawable.add_task_icon,
            hint = R.string.task_title,
            value = "",
            onValueChange = {}
        )
        Spacer(modifier.height(16.dp))
        TudeeTextField(
            multiLined = true,
            hint = R.string.description,
            value = "",
            onValueChange = {}
        )
        Spacer(modifier.height(16.dp))
        // date picker from calender
        TudeeTextField(
            icon = R.drawable.add_date_icon,
            hint = R.string.task_title,
            value = "",
            onValueChange = {}
        )
        Spacer(modifier.height(16.dp))
        Text(
            text = stringResource(R.string.priority),
            style = TudeeTheme.typography.titleMedium,
            color = TudeeTheme.colors.title
        )
        Spacer(modifier.height(8.dp))
        PrioritySelector(
            selectedPriority = selectedPriority,
            onPrioritySelected = { newPriority ->
                selectedPriority = newPriority
            }
        )
        Spacer(modifier.height(16.dp))
        Text(
            text = stringResource(R.string.category_title),
            style = TudeeTheme.typography.titleMedium,
            color = TudeeTheme.colors.title
        )
        Spacer(modifier.height(8.dp))
        // Here is the category (Abdo Magdy)
        Spacer(modifier.height(16.dp))
    }
}

@ThemePreviews
@Composable
fun PreviewAddOrEditTaskDetails() {
    TudeeTheme {
        AddOrEditTaskDetails(
            title = R.string.add_new_task,
        )
    }
}