package com.london.tudee.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.london.tudee.R
import com.london.tudee.presentation.design_system.theme.ThemePreviews
import com.london.tudee.presentation.design_system.theme.TudeeTheme

@Composable
fun TopAppBar(
    @StringRes title: Int,
    onBackClick: () -> Unit,
    onClickAction: () -> Unit,
    modifier: Modifier = Modifier,
    isFilter: Boolean,
    navigationIcon: @Composable (onBackClick: () -> Unit) -> Unit = {
        IconButton(
            onClick = it,
            modifier = Modifier
                .then(
                    if (LocalLayoutDirection.current == LayoutDirection.Rtl)
                        Modifier.rotate(180f)
                    else Modifier
                )
                .border(
                    1.dp,
                    TudeeTheme.colors.stroke,
                    TudeeTheme.shapes.circle
                )
        ) {
                Icon(
                    painter = painterResource(R.drawable.back_arrow),
                    contentDescription = stringResource(R.string.back_arrow),
                    tint = TudeeTheme.colors.body
                )
        }
    },
    actions: @Composable (onClickAction: () -> Unit) -> Unit = {
        if (isFilter) {
            IconButton(
                onClick = it,
                modifier = Modifier
                    .then(
                        if (LocalLayoutDirection.current == LayoutDirection.Rtl)
                            Modifier.rotate(180f)
                        else Modifier
                    )
                    .border(
                        1.dp,
                        TudeeTheme.colors.stroke,
                        TudeeTheme.shapes.circle
                    )
            ) {
                Icon(
                    painter = painterResource(R.drawable.filter_icon),
                    contentDescription = stringResource(R.string.filter_icon),
                    tint = TudeeTheme.colors.body
                )
            }
        }
    }
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(TudeeTheme.colors.surfaceHigh)
            .padding(vertical = 12.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        navigationIcon(onBackClick)
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = stringResource(title),
            style = TudeeTheme.typography.titleLarge,
            color = TudeeTheme.colors.title,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(8.dp))
        actions(onClickAction)
    }
}

@ThemePreviews
@Composable
private fun TopAppBarPreview() {
    TudeeTheme {
        TopAppBar(
            title = R.string.app_name,
            onBackClick = {},
            onClickAction = {},
            isFilter = true
        )
    }
}