package com.london.tudee.presentation.components.task

import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.london.tudee.R
import com.london.tudee.presentation.components.priority.Priority
import com.london.tudee.presentation.design_system.theme.TudeeTheme
import kotlin.math.roundToInt

@Composable
fun SwipeToDeleteTask(
    modifier: Modifier = Modifier,
    task: Task,
) {
    var offsetX by remember { mutableFloatStateOf(0f) }
    var showBottomSheet by remember { mutableStateOf(false) }
    val swipeThreshold = 200f

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(123.dp)
    ) {
        if (offsetX < 0) {
            DeleteBackground(onDeleteClick = {
                showBottomSheet = true
            })
        }

        TaskItem(
            priority = task.priority,
            iconResId = task.iconResId,
            title = task.title,
            description = task.description,
            date = task.date,
            modifier = Modifier
                .offset { IntOffset(offsetX.roundToInt(), 0) }
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onDragEnd = {
                            offsetX = if (offsetX <= -swipeThreshold) -swipeThreshold else 0f
                        }
                    ) { _, dragAmount ->
                        offsetX = (offsetX + dragAmount).coerceIn(-swipeThreshold, 0f)
                    }
                }
        )
    }
}

@Composable
private fun DeleteBackground(onDeleteClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxSize(),
        shape = TudeeTheme.shapes.small,
        colors = CardDefaults.cardColors(
            containerColor = TudeeTheme.colors.errorVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onDeleteClick) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_delete_button),
                    contentDescription = "Delete Button",
                    tint = TudeeTheme.colors.error,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SwipeToDeleteTaskPreview() {
    TudeeTheme {
        SwipeToDeleteTask(
            task = Task(
                title = "Buy groceries",
                description = "Milk, Bread, Eggs",
                date = "18 June",
                priority = Priority.HIGH,
                iconResId = R.drawable.ic_education
            )
        )
    }
}

