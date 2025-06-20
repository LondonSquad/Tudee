package com.london.tudee.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.rememberNavController
import com.london.tudee.presentation.design_system.theme.ThemePreviews
import com.london.tudee.presentation.design_system.theme.TudeeTheme
import com.london.tudee.presentation.screens.home.HomeScreen
import com.london.tudee.presentation.screens.onboarding.OnBoardingHorizontalPager
import com.london.tudee.presentation.screens.onboarding.OnBoardingViewModel
import org.koin.androidx.compose.koinViewModel
import com.london.tudee.presentation.navigation.Screen
import com.london.tudee.presentation.navigation.TudeeNavHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TudeeTheme {
                val navController = rememberNavController()
                val onboardingViewModel: OnBoardingViewModel = koinViewModel()
                val shouldShowOnboarding by onboardingViewModel.shouldShowOnboarding.collectAsState()
                TudeeNavHost(
                    navController = navController,
                    startDestination = Screen.Main.route
                )

//                shouldShowOnboarding.let { showOnboarding ->
//                    TudeeNavHost(
//                        navController = navController,
//                        startDestination =
//                            if (showOnboarding)  Screen.Onboarding.route
////                            {
////                                Screen.Onboarding.route
////
////                                OnBoardingHorizontalPager(
////                                    onClickSkip = {
////                                        onboardingViewModel.markOnboardingSeen()
////                                        Log.d(
////                                            "test",
////                                            "onCreate: ${onboardingViewModel.shouldShowOnboarding.value}"
////                                        )
////                                    }
////                                )
////                            }
//                            else Screen.Main.route
//                    )
//                }
            }
        }
    }

}
