package com.london.tudee.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.london.tudee.presentation.design_system.theme.ThemePreviews
import com.london.tudee.presentation.design_system.theme.TudeeTheme
import com.london.tudee.presentation.screens.home.HomeScreen
import com.london.tudee.presentation.screens.onboarding.OnBoardingHorizontalPager
import com.london.tudee.presentation.screens.onboarding.OnBoardingViewModel
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TudeeTheme {
                val onboardingViewModel: OnBoardingViewModel = koinViewModel()
                val shouldShowOnboarding by onboardingViewModel.shouldShowOnboarding.collectAsState()

                shouldShowOnboarding.let { showOnboarding ->
                    if (showOnboarding) {
                        OnBoardingHorizontalPager(
                            onClickSkip = {
                                onboardingViewModel.markOnboardingSeen()
                                Log.d(
                                    "test",
                                    "onCreate: ${onboardingViewModel.shouldShowOnboarding.value}")
                            }
                        )
                    } else HomeScreen()
                }
            }
        }
    }
}

@ThemePreviews
@Composable
fun PreviewTestScreen() {
    TudeeTheme {
        TestScreen()
    }
}

@Composable
fun TestScreen() {
    //val isDark by remember { mutableStateOf(false) }
    //  TudeeTheme (isDarkMode = isDark){}
    TudeeTheme {
        Surface(
            modifier = Modifier.fillMaxSize(), color = TudeeTheme.colors.primary
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row {
                    Text(
                        text = "Hello from ",
                        color = TudeeTheme.colors.title,
                        style = TudeeTheme.typography.titleLarge
                    )
                    Text(
                        text = "Tudee",
                        color = TudeeTheme.colors.primaryVariant,
                        style = TudeeTheme.typography.cherryBomb
                    )
                }


                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { /*isDark = !isDark*/ }, colors = ButtonDefaults.buttonColors(
                        contentColor = TudeeTheme.colors.title,
                        containerColor = TudeeTheme.colors.pinkAccent
                    ), shape = TudeeTheme.shapes.medium
                ) {
                    Text(
                        text = if (isSystemInDarkTheme()) "Switch to Light" else "Switch to Dark",
                        color = TudeeTheme.colors.onPrimary,
                        style = TudeeTheme.typography.labelSmall
                    )
                }
            }
        }
    }
}