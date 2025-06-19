package com.london.tudee.presentation.components.date

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.london.tudee.R
import com.london.tudee.presentation.design_system.theme.ThemePreviews
import com.london.tudee.presentation.design_system.theme.TudeeTheme

@Composable
fun DateBadge(
    modifier: Modifier = Modifier,
    dateText: String,
    shape: Shape,
    colors: CardColors,
    iconSize: Dp,
    textSize: TextUnit,
    textStyle: TextStyle,
    lineHeight: TextUnit,
    iconColor: Color,
    textColor: Color,
    contentPadding: PaddingValues
) {
    Card(
        modifier = modifier,
        shape = shape,
        colors = colors
    ) {
        Row(
            modifier = Modifier
                .wrapContentWidth()
                .fillMaxHeight()
                .padding(contentPadding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                modifier = Modifier
                    .padding(top = 2.dp, bottom = 2.dp, end = 2.dp)
                    .size(iconSize),
                painter = painterResource(id = R.drawable.ic_calendar_favorite),
                contentDescription = "Calendar Icon",
                tint = iconColor
            )
            Text(
                text = dateText,
                style = textStyle,
                fontSize = textSize,
                lineHeight = lineHeight,
                textAlign = TextAlign.Center,
                color = textColor
            )
        }
    }
}

@ThemePreviews
@Composable
fun PreviewDateBadge() {
    TudeeTheme {
        DateBadge(
            modifier = Modifier.height(28.dp),
            shape = TudeeTheme.shapes.circle,
            colors = CardDefaults.cardColors(containerColor = TudeeTheme.colors.surface),
            dateText = "12-03-2025",
            iconSize = 12.dp,
            textSize = 13.sp,
            textStyle = TudeeTheme.typography.labelSmall,
            lineHeight = 16.sp,
            iconColor = TudeeTheme.colors.body,
            textColor = TudeeTheme.colors.body,
            contentPadding = PaddingValues(vertical = 6.dp, horizontal = 8.dp)
        )
    }
}

@ThemePreviews
@Composable
fun PreviewDateBadgeHomeScreen() {
    TudeeTheme {
        DateBadge(
            modifier = Modifier.height(17.dp),
            shape = RectangleShape,
            colors = CardDefaults.cardColors(containerColor = TudeeTheme.colors.surfaceHigh),
            dateText = "today,12-03-2025",
            iconSize = 16.dp,
            textSize = 14.sp,
            textStyle = TudeeTheme.typography.labelMedium,
            lineHeight = 16.sp,
            iconColor = TudeeTheme.colors.body,
            textColor = TudeeTheme.colors.body,
            contentPadding = PaddingValues(vertical = 0.dp, horizontal = 8.dp)
        )
    }
}

