package com.london.tudee.presentation.screens.tasks

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.london.tudee.R
import com.london.tudee.presentation.design_system.theme.ThemePreviews
import com.london.tudee.presentation.design_system.theme.TudeeTheme

@Composable
fun EmptyTasksScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.width(330.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .height(160.dp)
                .align(Alignment.CenterEnd)
                .background(color = Color.Transparent)
        ) {
            CircularContainer(
                modifier = Modifier.align(Alignment.BottomEnd)
            )

            MessageBox(modifier = Modifier.align(Alignment.TopEnd))
        }
    }
}

@Composable
fun CircularContainer(modifier: Modifier = Modifier) {
    Box(modifier = modifier, contentAlignment = Alignment.BottomEnd) {

        Box(
            modifier = Modifier
                .padding(end = 5.dp, bottom = 3.dp)
                .size(144.dp)
                .border(
                    color = TudeeTheme.colors.overlay,
                    shape = CircleShape,
                    width = 1.dp
                )
        )

        Box(
            modifier = Modifier
                .size(136.dp)
                .clip(CircleShape)
                .background(color = TudeeTheme.colors.overlay)
        )

        Image(
            painter = painterResource(R.drawable.empty_task_tudee),
            contentDescription = "Empty Task",
            modifier = Modifier
                .size(width = 107.dp, height = 100.dp)
                .padding(bottom = 3.dp)
        )

        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 19.dp, top = 17.dp)
                .width(23.dp)
                .height(34.dp)
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(4.dp)
                    .clip(CircleShape)
                    .background(TudeeTheme.colors.surfaceHigh)
            )

            Box(
                modifier = Modifier
                    .padding(bottom = 9.dp, end = 5.dp)
                    .align(Alignment.BottomEnd)
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(TudeeTheme.colors.surfaceHigh)
            )

            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .size(14.dp)
                    .clip(CircleShape)
                    .background(TudeeTheme.colors.surfaceHigh)
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 21.6.dp, bottom = 11.2.dp)
                .size(14.4.dp)
                .clip(CircleShape)
                .background(TudeeTheme.colors.overlay)
        )
    }
}

@Composable
fun MessageBox(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .padding(end = 127.dp)
            .width(203.dp)
            .height(74.dp)
            .shadow(
                elevation = 12.dp,
                shape = RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp,
                    bottomStart = 16.dp,
                    bottomEnd = 2.dp,
                ),
                spotColor = Color(0x0A000000)
            )
            .clip(
                shape = RoundedCornerShape(
                    topEnd = 12.dp,
                    topStart = 12.dp,
                    bottomEnd = 2.dp,
                    bottomStart = 12.dp,
                )
            )
            .background(TudeeTheme.colors.surfaceHigh)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(R.string.no_task_today),
                style = TudeeTheme.typography.titleSmall,
                color = TudeeTheme.colors.body,
                modifier = Modifier.padding(start = 12.dp, top = 8.dp)
            )
            Text(
                text = stringResource(R.string.tap_to_add_task),
                style = TudeeTheme.typography.labelSmall,
                color = TudeeTheme.colors.hint,
                modifier = Modifier.padding(start = 12.dp, top = 4.dp)
            )
        }
    }
}

@Composable
@ThemePreviews
fun EmptyTasksScreenPreview() {
    TudeeTheme {
        EmptyTasksScreen()
    }
}
