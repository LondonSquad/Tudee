package com.london.tudee.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.london.tudee.presentation.design_system.theme.ThemePreviews
import com.london.tudee.presentation.design_system.theme.TudeeTheme
import com.london.tudee.presentation.screens.home.TaskStatusUiState


@Composable
fun TaskStatusSlider(
    taskStatusUiState: TaskStatusUiState,
    modifier: Modifier = Modifier,
    note: String?,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(92.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = modifier
                    .weight(0.7f)
                    .padding(vertical = 15.dp)
            ) {
                Row {
                    Text(
                        text = taskStatusUiState.title,
                        style = TudeeTheme.typography.titleSmall,
                        color = TudeeTheme.colors.title,
                        lineHeight = 20.sp
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Image(
                        modifier = Modifier
                            .size(20.dp),
                        painter = painterResource(id = taskStatusUiState.emoji),
                        contentDescription = null,
                    )
                }

                Text(
                    text = taskStatusUiState.subtitle,
                    style = TudeeTheme.typography.bodySmall,
                    color = TudeeTheme.colors.body,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 17.sp
                )
                Text(
                    text = note ?: "",
                    style = TudeeTheme.typography.bodySmall,
                    color = TudeeTheme.colors.body,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 17.sp
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Box(
                modifier = Modifier
                    .weight(0.17f)
                    .fillMaxHeight(),
                contentAlignment = Alignment.CenterEnd
            ) {
                Box(
                    modifier = Modifier
                        .size(65.dp)
                        .background(
                            color = TudeeTheme.colors.primary.copy(alpha = .2f),
                            shape = TudeeTheme.shapes.circle
                        )
                )

                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(61.dp)
                        .align(Alignment.Center)
                        .padding(bottom = 8.dp)
                ) {
                    Image(
                        painter = painterResource(id = taskStatusUiState.tudeePicture),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }
            }
        }
    }
}

@ThemePreviews
@Composable
fun TaskStatusSliderPreview() {

    TaskStatusSlider(
        note = "Tudee is watching. back to work!!!",
        taskStatusUiState = TaskStatusUiState(),
        modifier = Modifier.padding(start = 6.dp)
    )
}

