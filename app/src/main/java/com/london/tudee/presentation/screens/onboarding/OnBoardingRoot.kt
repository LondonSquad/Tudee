package com.london.tudee.presentation.screens.onboarding

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.london.tudee.presentation.screens.home.HomeScreen
import com.london.tudee.presentation.screens.splash.SplashScreen
import dev.burnoo.compose.rememberpreference.rememberBooleanPreference

@Composable
fun OnBoardingRoot() {
    var isOnboardingCompleted by rememberBooleanPreference(
        keyName = "onboardingKey",
        initialValue = null,
        defaultValue = false,
    )

    when (isOnboardingCompleted) {
        null -> SplashScreen()
        false -> OnBoardingHorizontalPager(
            onCompleted = { isOnboardingCompleted = true },
            onClickSkip = { isOnboardingCompleted = true },
        )

        true -> HomeScreen(onArrowClicked = {})
    }
}
