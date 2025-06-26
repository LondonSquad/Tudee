package com.london.tudee.presentation.components.date

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.london.tudee.R
import com.london.tudee.presentation.design_system.theme.ThemePreviews
import com.london.tudee.presentation.design_system.theme.TudeeTheme

@Composable
fun DateBadge(
    dateText: String,
    isVisible: Boolean,
    modifier: Modifier = Modifier,
    dateBadgeStyle: DateBadgeStyleValues
) {
    if (!isVisible) return
    Card(
        modifier = modifier,
        shape = dateBadgeStyle.shape,
        colors = dateBadgeStyle.colors
    ) {
        Row(
            modifier = Modifier
                .padding(dateBadgeStyle.contentPadding)
                .wrapContentWidth()
                .fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                modifier = Modifier
                    .padding(vertical = 2.dp)
                    .align(Alignment.CenterVertically)
                    .size(dateBadgeStyle.iconSize),
                painter = painterResource(id = R.drawable.ic_calendar_favorite),
                contentDescription = "Calendar Icon",
                tint = dateBadgeStyle.iconColor
            )
            Spacer(modifier = Modifier.width(dateBadgeStyle.iconTextSpacing))
            Text(
                modifier = Modifier.align(Alignment.CenterVertically),
                text = dateText,
                style = dateBadgeStyle.textStyle,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@ThemePreviews
@Composable
private fun PreviewDateBadge() {
    TudeeTheme {
        DateBadge(
            modifier = Modifier.height(28.dp),
            dateText = "12-03-2025",
            dateBadgeStyle = DateBadgeStyleValues(
                shape = TudeeTheme.shapes.circle,
                colors = CardDefaults.cardColors(containerColor = TudeeTheme.colors.surface),
                iconSize = 12.dp,
                iconColor = TudeeTheme.colors.body,
                textStyle = TudeeTheme.typography.labelSmall.copy(
                    fontSize = 13.sp,
                    lineHeight = 16.sp,
                    color = TudeeTheme.colors.body
                ),
                contentPadding = PaddingValues(vertical = 6.dp, horizontal = 8.dp)
            ),
            isVisible = true
        )
    }
}

@ThemePreviews
@Composable
private fun PreviewDateBadgeHomeScreen() {
    TudeeTheme {
        DateBadge(
            modifier = Modifier.height(17.dp),
            dateText = "today,12-03-2025",
            dateBadgeStyle = DateBadgeStyleValues(
                shape = RectangleShape,
                colors = CardDefaults.cardColors(containerColor = TudeeTheme.colors.surfaceHigh),
                iconSize = 16.dp,
                iconColor = TudeeTheme.colors.body,
                textStyle = TudeeTheme.typography.labelMedium.copy(
                    fontSize = 14.sp,
                    lineHeight = 16.sp,
                    color = TudeeTheme.colors.body
                ),
                contentPadding = PaddingValues(vertical = 0.dp, horizontal = 8.dp)
            ),
            isVisible = false
        )
    }
}