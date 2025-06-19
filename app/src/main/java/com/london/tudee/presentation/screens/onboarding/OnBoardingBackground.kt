package com.london.tudee.presentation.screens.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.london.tudee.R
import com.london.tudee.presentation.design_system.theme.TudeeTheme

@Composable
fun OnBoardingBackground() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(TudeeTheme.colors.surface)
    ) {
        val photo = if (isSystemInDarkTheme()) {
            listOf(R.drawable.vector7_dark,
            R.drawable.vector9_dark,
            R.drawable.vector10_dark)

        } else {
            listOf(
                R.drawable.vector7,
                R.drawable.vector9,
                R.drawable.vector10)
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(TudeeTheme.colors.overlay)
        ) {
            Image(
                painter = painterResource(id = photo[0]), contentDescription = null
            )
            Image(
                modifier = Modifier.align(Alignment.TopEnd),
                painter = painterResource(id = photo[1]),
                contentDescription = null,

                )
            Image(
                modifier = Modifier.align(Alignment.CenterEnd),
                painter = painterResource(id = photo[2]),
                contentDescription = null
            )
        }
    }
}

@Preview
@Composable
fun OnboardingFlowPreview() {
    TudeeTheme {
        OnBoardingBackground()
    }
}