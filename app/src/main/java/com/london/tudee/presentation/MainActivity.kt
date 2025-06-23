package com.london.tudee.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.london.tudee.presentation.design_system.theme.TudeeTheme
import com.london.tudee.presentation.screens.home.HomeScreen
import com.london.tudee.presentation.screens.onboarding.OnBoardingHorizontalPager
import com.london.tudee.presentation.screens.onboarding.OnBoardingViewModel
import com.london.tudee.presentation.screens.task.view_tasks.CategoryDetailsScreen
import org.koin.androidx.compose.koinViewModel
import com.london.tudee.presentation.screens.onboarding.OnBoardingRoot

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
                            onClickSkip = {onboardingViewModel.markOnboardingSeen() }
                        )
                    } else HomeScreen {  }
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
=======
                OnBoardingRoot(
                    onCompleted = {},
                    onSkip ={}
                )
            }
        }
    }
}