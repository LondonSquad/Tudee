package com.london.tudee.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.london.tudee.R
import com.london.tudee.presentation.design_system.theme.ThemePreviews
import com.london.tudee.presentation.design_system.theme.TudeeTheme

@Composable
fun DateBadge(
    modifier: Modifier = Modifier,
    dateText: String
) {
    Card(
        modifier = modifier
            .height(28.dp),
        shape = TudeeTheme.shapes.circle,
        colors = CardDefaults.cardColors(containerColor = TudeeTheme.colors.surface)
    ) {
        Row(
            modifier = Modifier
                .wrapContentWidth()
                .fillMaxHeight()
                .padding(vertical = 6.dp, horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                modifier = Modifier
                    .padding(top = 2.dp, bottom = 2.dp, end = 2.dp)
                    .size(12.dp),
                painter = painterResource(id = R.drawable.ic_calendar_favorite),
                contentDescription = "Calendar Icon",
                tint = TudeeTheme.colors.body
            )
            Text(
                text = dateText,
                style = TudeeTheme.typography.labelSmall,
                lineHeight = 16.sp,
                textAlign = TextAlign.Start,
                color = TudeeTheme.colors.body
            )
        }
    }
}

@ThemePreviews
@Composable
fun PreviewDateBadge() {
    TudeeTheme {
        DateBadge(dateText = "12-03-2025")
    }
}
