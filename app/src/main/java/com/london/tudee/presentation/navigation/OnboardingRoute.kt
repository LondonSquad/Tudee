package com.london.tudee.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.london.tudee.presentation.screens.onboarding.OnBoardingHorizontalPager
import com.london.tudee.presentation.screens.onboarding.OnBoardingRoot

fun NavGraphBuilder.onboardingRoute(navController: NavController){
    composable<Screen.Onboarding>{
        OnBoardingRoot(
            onSkip = {
                navController.navigateToHomeScreen(popUp = true)
            },
            onCompleted = {
                navController.navigateToHomeScreen(popUp = true)
            }
        )
    }
}

fun NavController.navigateToOnboardingScreen(){
    navigate(route = Screen.Onboarding)
}