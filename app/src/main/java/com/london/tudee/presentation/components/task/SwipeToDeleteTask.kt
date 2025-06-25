package com.london.tudee.presentation.components.task

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.london.tudee.R
import com.london.tudee.domain.entities.Priority
import com.london.tudee.domain.entities.Task
import com.london.tudee.domain.entities.TaskStatus
import com.london.tudee.presentation.design_system.theme.ThemePreviews
import com.london.tudee.presentation.design_system.theme.TudeeTheme
import kotlinx.datetime.Clock
import kotlin.math.roundToInt


@Composable
fun SwipeToDeleteTask(
    modifier: Modifier = Modifier,
    task: Task,
    iconResId: String,
    onDeleteClick: () -> Unit
) {
    var rawOffsetX by remember { mutableFloatStateOf(0f) }
    val swipeThreshold = 200f

    val animatedOffsetX by animateFloatAsState(
        targetValue = rawOffsetX,
        animationSpec = tween(durationMillis = 300),
        label = "SwipeAnimation"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(111.dp)
    ) {
        AnimatedVisibility(
            visible = animatedOffsetX < 0f,
            enter = fadeIn(animationSpec = tween(300)),
            exit = fadeOut(animationSpec = tween(300))
        ) {
            DeleteBackground(onDeleteClick = {
                rawOffsetX = 0f
                onDeleteClick()
            })
        }

        TaskItem(
            task = task,
            modifier = Modifier
                .offset { IntOffset(animatedOffsetX.roundToInt(), 0) }
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onDragEnd = {
                            rawOffsetX = if (rawOffsetX <= -swipeThreshold) -swipeThreshold else 0f
                        }
                    ) { _, dragAmount ->
                        rawOffsetX = (rawOffsetX + dragAmount).coerceIn(-swipeThreshold, 0f)
                    }
                },
            hasDate = true,
            iconResId = iconResId,
            isSelected = true
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

@ThemePreviews
@Composable
fun SwipeToDeleteTaskPreview() {
    TudeeTheme {
        SwipeToDeleteTask(
            task = Task(
                id = 1,
                title = "Buy groceries",
                description = "Milk, Bread, Eggs",
                timeStamp = Clock.System.now(),
                priority = Priority.HIGH,
                categoryId = 1,
                taskStatus = TaskStatus.TODO
            ),
            onDeleteClick = {},
            iconResId = ""
        )
    }
}

