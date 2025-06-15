package com.london.tudee.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.london.tudee.R
import com.london.tudee.presentation.design_system.theme.ThemePreviews
import com.london.tudee.presentation.design_system.theme.TudeeTheme

@Composable
fun HomeTopBar(
    isDarkMode: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(72.dp)
            .background(TudeeTheme.colors.primary)
            .padding(vertical = 12.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.tudee_avatar),
            contentDescription = stringResource(id = R.string.tudee_avatar)
        )
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = stringResource(id = R.string.tudee),
                style = TudeeTheme.typography.cherryBomb,
                color = TudeeTheme.colors.onPrimary
            )
            Text(
                text = stringResource(id = R.string.tudee_greeting),
                style = TudeeTheme.typography.labelSmall,
                color = TudeeTheme.colors.caption
            )
        }
        Switch(
            checked = isDarkMode,
            onCheckedChange = onCheckedChange
        )
    }
}

@ThemePreviews
@Composable
private fun HomeTopBarPreview() {
    TudeeTheme {
        HomeTopBar(isDarkMode = false, onCheckedChange = {})
    }
}