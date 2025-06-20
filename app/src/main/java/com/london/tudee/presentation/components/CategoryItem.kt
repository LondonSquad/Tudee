package com.london.tudee.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.london.tudee.R
import com.london.tudee.presentation.design_system.theme.ThemePreviews
import com.london.tudee.presentation.design_system.theme.TudeeTheme

@Composable
fun CategoryItem(
    modifier: Modifier = Modifier,
    @DrawableRes iconRes: Int,
    title: String,
    tint: Color,
    count: Int? = null,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(78.dp)
                .background(
                    color = TudeeTheme.colors.surfaceHigh,
                    shape = TudeeTheme.shapes.circle
                )
                .padding(23.dp)
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = title,
                modifier = Modifier.size(32.dp),
                tint = tint
            )

            count?.let {
                Box(
                    modifier = Modifier
                        .width(36.dp)
                        .align(Alignment.TopEnd)
                        .offset(x = 20.dp, y = (-20).dp)
                        .background(
                            color = TudeeTheme.colors.surfaceLow,
                            shape = TudeeTheme.shapes.circle
                        )
                        .padding(horizontal = 6.dp, vertical = 2.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = count.toString(),
                        style = TudeeTheme.typography.labelSmall,
                        color = TudeeTheme.colors.hint
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = title,
            style = TudeeTheme.typography.labelSmall,
            color = TudeeTheme.colors.body
        )
    }
}

@ThemePreviews
@Composable
fun CategoryItemPreview() {
    TudeeTheme {
        Row(
            modifier = Modifier
                .background(TudeeTheme.colors.background)
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CategoryItem(
                iconRes = R.drawable.ic_entertainment,
                title = "Entertainment",
                tint = TudeeTheme.colors.yellowAccent,
                count = 33,
                onClick = {}
            )

            CategoryItem(
                iconRes = R.drawable.ic_event,
                title = "Event",
                tint = TudeeTheme.colors.pinkAccent,
                count = 2,
                onClick = {}
            )

            CategoryItem(
                iconRes = R.drawable.ic_budgeting,
                title = "Budgeting",
                tint = TudeeTheme.colors.purpleAccent,
                count = 23,
                onClick = {}
            )
        }
    }
}