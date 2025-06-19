package com.london.tudee.presentation.screens.onboarding

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.london.tudee.R

val OnBoardingContent = listOf(
    OnboardingPage(
        title = R.string.on_bording_title_1,
        body = R.string.on_bording_body_1,
        image = R.drawable.on_boarding1,
    ), OnboardingPage(
        title = R.string.on_bording_title_2,
        body = R.string.on_bording_body_2,
        image = R.drawable.on_boarding2
    ), OnboardingPage(
        title = R.string.on_bording_title_3,
        body = R.string.on_bording_body_3,
        image = R.drawable.on_boarding3,
        hasSkipButton = false
    )
)

data class OnboardingPage(
    @StringRes val title: Int,
    @StringRes val body: Int,
    @DrawableRes val image: Int,
    val hasSkipButton: Boolean = true,
)