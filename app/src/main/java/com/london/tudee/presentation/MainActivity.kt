package com.london.tudee.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import com.london.tudee.R
import com.london.tudee.presentation.design_system.theme.TudeeTheme
import com.london.tudee.presentation.screens.tasks.AddOrEditTaskBottomSheet

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }

    @Composable
    fun MainScreen() {
        var showBottomSheet by remember { mutableStateOf(false) }
        val mainScreenContent: @Composable () -> Unit = {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = TudeeTheme.colors.primary
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
                        onClick = { showBottomSheet = true },
                        colors = ButtonDefaults.buttonColors(
                            contentColor = TudeeTheme.colors.title,
                            containerColor = TudeeTheme.colors.pinkAccent
                        ),
                        shape = TudeeTheme.shapes.medium
                    ) {
                        Text(
                            text = "Show Task Bottom Sheet",
                            color = TudeeTheme.colors.onPrimary,
                            style = TudeeTheme.typography.labelSmall
                        )
                    }
                }
            }
        }

        TudeeTheme {
            Box(modifier = Modifier.fillMaxSize()) {
                mainScreenContent()

                if (showBottomSheet) {
                    AddOrEditTaskBottomSheet(
                        title = R.string.task_title,
                        buttonText = R.string.add,
                        onDismiss = { showBottomSheet = false },
                        screenContent = mainScreenContent
                    )
                }
            }
        }
    }
}