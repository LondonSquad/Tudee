package com.london.tudee.presentation.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.london.tudee.presentation.screens.onboarding.OnBoardingHorizontalPager

@Composable
fun TudeeNavHost(navController: NavHostController, startDestination: String) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(Screen.Onboarding.route) {
//            OnboardingScreen(
//                onNavigateToMainsScreenButtonClicked = {
//                    navController.navigate(Screen.Main.route) {
//                        popUpTo(Screen.Onboarding.route) { inclusive = true }
//                    }
//                }
//            )

//                OnBoardingHorizontalPager(
//                    onClickSkip = {
//                        onboardingViewModel.markOnboardingSeen()
//                        Log.d(
//                            "test",
//                            "onCreate: ${onboardingViewModel.shouldShowOnboarding.value}"
//                        )
//                    }
//                )
        }

        composable(Screen.Main.route) {
            MainScreen()
        }
    }
}
