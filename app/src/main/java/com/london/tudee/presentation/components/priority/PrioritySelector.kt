package com.london.tudee.presentation.components.priority

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.london.tudee.domain.entities.Priority
import com.london.tudee.presentation.design_system.theme.ThemePreviews

@Composable
fun PrioritySelector(
    modifier: Modifier = Modifier,
    selectedPriority: Priority,
    onPrioritySelected: (Priority) -> Unit
) {
    Row(
        modifier = modifier.wrapContentWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Priority.entries.forEach {
            PriorityBadge(
                priority = it,
                isSelected = it == selectedPriority,
                onClick = onPrioritySelected
            )
        }
    }
}

@ThemePreviews
@Composable
fun PrioritySelectorPreview() {
    PrioritySelector(
        selectedPriority = Priority.MEDIUM,
        onPrioritySelected = {}
    )
}
