package com.london.tudee.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.london.tudee.presentation.screens.onboarding.OnBoardingHorizontalPager

fun NavGraphBuilder.onboardingRoute(navController: NavController){
    composable<Screen.Onboarding>{
//        OnBoardingRoot(
//            onSkip = {
//                navController.navigateToHomeScreen(popUp = true)
//            },
//            onCompleted = {
//                navController.navigateToHomeScreen(popUp = true)
//            }
//        )
        OnBoardingHorizontalPager(
            onClickSkip = {
                navController.navigateToHomeScreen()
            }
        )
    }
}

fun NavController.navigateToOnboardingScreen(){
    navigate(route = Screen.Onboarding)
}