package com.london.tudee.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.london.tudee.R
import com.london.tudee.presentation.design_system.theme.ThemePreviews
import com.london.tudee.presentation.design_system.theme.TudeeTheme

@Composable
fun PriorityBadge(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    iconResId: Int,
    textResId: Int
) {
    Card(
        modifier = modifier.wrapContentSize(),
        shape = TudeeTheme.shapes.circle,
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Row(
            modifier = Modifier
                .height(28.dp)
                .padding(vertical = 6.dp)
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                modifier = Modifier
                    .size(12.dp)
                    .padding(end = 2.dp),
                painter = painterResource(id = iconResId),
                contentDescription = "Priority Icon",
                tint = TudeeTheme.colors.onPrimary
            )
            Text(
                modifier = modifier.height(16.dp),
                text = stringResource(id = textResId),
                style = TudeeTheme.typography.labelSmall,
                lineHeight = 16.sp,
                letterSpacing = 0.sp,
                textAlign = TextAlign.Start,
                color = TudeeTheme.colors.onPrimary
            )
        }
    }
}

@ThemePreviews
@Composable
fun PreviewPriorityBadgeHigh() {
    TudeeTheme {
        PriorityBadge(
            modifier = Modifier,
            backgroundColor = TudeeTheme.colors.pinkAccent,
            iconResId = R.drawable.ic_priority_high,
            textResId = R.string.high
        )
    }
}

@ThemePreviews
@Composable
fun PreviewPriorityBadgeMedium() {
    TudeeTheme {
        PriorityBadge(
            modifier = Modifier,
            backgroundColor = TudeeTheme.colors.yellowAccent,
            iconResId = R.drawable.ic_priority_medium,
            textResId = R.string.medium
        )
    }
}

@ThemePreviews
@Composable
fun PreviewPriorityBadgeLow() {
    TudeeTheme {
        PriorityBadge(
            modifier = Modifier,
            backgroundColor = TudeeTheme.colors.greenAccent,
            iconResId = R.drawable.ic_priority_low,
            textResId = R.string.low
        )
    }
}
