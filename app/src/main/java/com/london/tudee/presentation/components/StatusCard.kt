package com.london.tudee.presentation.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.london.tudee.R
import com.london.tudee.presentation.design_system.theme.ThemePreviews
import com.london.tudee.presentation.design_system.theme.TudeeTheme

@Composable
fun StatusCard(
    backgroundColor: Color,
    @DrawableRes statusIcon: Int,
    tasksNumber: Int,
    @StringRes taskStatusName: Int,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = TudeeTheme.shapes.medium
            )
            .height(112.dp)
            .clip(shape = TudeeTheme.shapes.medium)
            .fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(
                    start = 12.dp,
                    top = 12.dp,
                    bottom = 12.dp
                )
        ) {
            Image(
                imageVector = ImageVector.vectorResource(statusIcon),
                contentDescription = "$statusIcon",
                modifier = Modifier
                    .background(
                        color = Color.White.copy(0.24f),
                        shape = TudeeTheme.shapes.extraSmall
                    )
                    .border(
                        width = 1.dp,
                        color = Color.White.copy(0.12f),
                        shape = TudeeTheme.shapes.extraSmall
                    )
                    .padding(8.dp)
            )

            Column {
                Text(
                    text = "$tasksNumber",
                    color = TudeeTheme.colors.onPrimary,
                    style = TudeeTheme.typography.headlineMedium,
                    modifier = Modifier.height(28.dp)
                )

                Text(
                    text = stringResource(taskStatusName),
                    style = TudeeTheme.typography.labelSmall,
                    color = TudeeTheme.colors.caption,
                    modifier = Modifier.height(16.dp)
                )
            }
        }

        Image(
            painter = painterResource(R.drawable.overview_stat_icon_container),
            contentDescription = stringResource(R.string.Circle),
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = 40.dp, y = (-41).dp)
        )
    }
}

@ThemePreviews
@Composable
fun PreviewGreenStatusCard() {
    TudeeTheme {
        StatusCard(
            backgroundColor = TudeeTheme.colors.greenAccent,
            statusIcon = R.drawable.file_verified,
            tasksNumber = 3,
            taskStatusName = R.string.Done,
        )
    }
}

@ThemePreviews
@Composable
fun PreviewYellowStatusCard() {
    TudeeTheme {
        StatusCard(
            backgroundColor = TudeeTheme.colors.yellowAccent,
            statusIcon = R.drawable.file_pin,
            tasksNumber = 16,
            taskStatusName = R.string.In_Progress,
        )
    }
}

@ThemePreviews
@Composable
fun PreviewStatusCard() {
    TudeeTheme {
        StatusCard(
            backgroundColor = TudeeTheme.colors.purpleAccent,
            statusIcon = R.drawable.file_unknown,
            tasksNumber = 1,
            taskStatusName = R.string.To_Do,
        )
    }
}