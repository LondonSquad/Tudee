package com.london.tudee.presentation.screens.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
            listOf(
                R.drawable.vector_7dark,
                R.drawable.vector_9dark,
                R.drawable.vector_10dark
            )
        } else {
            listOf(
                R.drawable.vector_7,
                R.drawable.vector_9,
                R.drawable.vector_10
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(TudeeTheme.colors.overlay)
        ) {
            Image(
                modifier = Modifier.align(Alignment.TopEnd),
                painter = painterResource(id = photo[OnboardingPages.FIRST_PAGE]),
                contentDescription = null,
            )
            Image(
                modifier = Modifier.align(Alignment.TopEnd),
                painter = painterResource(id = photo[OnboardingPages.SECOND_PAGE]),
                contentDescription = null,
            )
            Image(
                modifier = Modifier.align(Alignment.CenterEnd).offset(y = (-90).dp),
                painter = painterResource(id = photo[OnboardingPages.THIRD_PAGE]),
                contentDescription = null
            )
        }
    }
}

@Preview
@Composable
private fun OnboardingFlowPreview() {
    TudeeTheme {
        OnBoardingBackground()
    }
}