package com.london.tudee.presentation.screens.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(TudeeTheme.colors.overlay)
        ) {
            Image(
                painter = painterResource(id = R.drawable.vector7), contentDescription = null
            )
            Image(
                modifier = Modifier.align(Alignment.TopEnd),
                painter = painterResource(id = R.drawable.vector9),
                contentDescription = null,

                )
            Image(
                modifier = Modifier.align(Alignment.CenterEnd),
                painter = painterResource(id = R.drawable.vector10),
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