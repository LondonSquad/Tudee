package com.london.tudee.presentation.screens.splash
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.london.tudee.presentation.design_system.theme.ThemePreviews
import com.london.tudee.presentation.screens.onboarding.OnBoardingBackground


@ThemePreviews
@Composable
fun PreviewTestScreen() {
    SplashScreen()
}

@Preview
@Composable
fun SplashScreen() {
    Box {
        OnBoardingBackground()
        TudeeLogoText(modifier = Modifier.align(Alignment.Center))
    }
}

