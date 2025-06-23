package com.london.tudee.presentation.screens.onboarding

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.london.tudee.presentation.screens.splash.SplashScreen
import dev.burnoo.compose.rememberpreference.rememberBooleanPreference

@Composable
fun OnBoardingRoot(
    onCompleted: () -> Unit,
    onSkip: () -> Unit,
) {
    var isOnboardingCompleted by rememberBooleanPreference(
        keyName = "onboardingKey",
        initialValue = null,
        defaultValue = false,
    )
    when (isOnboardingCompleted) {
        null -> SplashScreen()
        false -> OnBoardingHorizontalPager(
            onCompleted = {
                isOnboardingCompleted = true
                onCompleted()
            },
            onClickSkip = {
                isOnboardingCompleted = true
                onSkip()
            },
        )

        true -> {
            LaunchedEffect(Unit) {
                onCompleted()
            }
        }
    }
}
