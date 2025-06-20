package com.london.tudee.presentation.components.bottom_navigation_bar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.london.tudee.R
import com.london.tudee.presentation.design_system.theme.TudeeTheme


@Composable
fun NavigationTestScreen(
    color: Color
) {
    TudeeTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = color
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row {
                    Text(
                        text = stringResource(R.string.hello_from),
                        color = TudeeTheme.colors.title,
                        style = TudeeTheme.typography.titleLarge
                    )
                    Text(
                        text = stringResource(R.string.app_name),
                        color = TudeeTheme.colors.primaryVariant,
                        style = TudeeTheme.typography.cherryBomb
                    )
                }
            }
        }
    }
}