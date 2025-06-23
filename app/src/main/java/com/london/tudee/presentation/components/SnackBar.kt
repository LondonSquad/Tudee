package com.london.tudee.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.london.tudee.R
import com.london.tudee.presentation.design_system.theme.ThemePreviews
import com.london.tudee.presentation.design_system.theme.TudeeTheme

@Composable
fun SnackBar(
    modifier: Modifier = Modifier,
    @StringRes message: Int = R.string.successfully,
    iconPainter: Painter,
    iconTint: Color = TudeeTheme.colors.greenAccent,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 16.dp)
            .shadow(
                elevation = 4.dp,
                shape = TudeeTheme.shapes.small,
                ambientColor = Color(0x1F000000)
            )
            .clip(TudeeTheme.shapes.small)
            .background(TudeeTheme.colors.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = 8.dp,
                    end = 12.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(TudeeTheme.shapes.extraSmall)
                    .background(TudeeTheme.colors.greenVariant),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = iconPainter,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = iconTint
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = stringResource(message),
                style = TudeeTheme.typography.bodyMedium,
                color = TudeeTheme.colors.body,
                lineHeight = 20.sp
            )
        }
    }
}

@ThemePreviews
@Composable
fun SnackBarPreview() {
    Box(modifier = Modifier.fillMaxSize()) {
        SnackBar(
            modifier = Modifier
                .offset(y = 56.dp),
            message = R.string.successfully,
            iconPainter = painterResource(R.drawable.snack_bar_container),
        )
    }
}