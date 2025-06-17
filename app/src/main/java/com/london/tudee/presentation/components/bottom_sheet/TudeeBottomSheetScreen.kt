package com.london.tudee.presentation.components.bottom_sheet

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.london.tudee.R
import com.london.tudee.presentation.components.buttons.TudeePrimaryButton
import com.london.tudee.presentation.components.buttons.TudeeSecondaryButton
import com.london.tudee.presentation.design_system.theme.ThemePreviews
import com.london.tudee.presentation.design_system.theme.TudeeTheme

/**
 * A reusable component that provides a screen with bottom sheet functionality.
 * Handles overlay, click-outside-to-dismiss, and proper animations.
 *
 * @param showBottomSheet Whether the bottom sheet should be visible
 * @param onDismiss Callback when the bottom sheet should be dismissed (click outside or back gesture)
 * @param screenContent The main screen content that will be dimmed when bottom sheet is shown
 * @param bottomSheetContent The content inside the bottom sheet
 * @param bottomSheetActions The action buttons at the bottom of the sheet
 * @param modifier Modifier for the root container
 */
@Composable
fun TudeeBottomSheetScreen(
    showBottomSheet: Boolean,
    onDismiss: () -> Unit,
    screenContent: @Composable () -> Unit,
    bottomSheetContent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    bottomSheetActions: @Composable ColumnScope.() -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .navigationBarsPadding()
    ) {
        screenContent()

        AnimatedVisibility(
            visible = showBottomSheet,
            enter = fadeIn(animationSpec = tween(300)),
            exit = fadeOut(animationSpec = tween(300))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        onDismiss()
                    }
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            TudeeBottomSheet(
                visible = showBottomSheet,
                onDismiss = onDismiss,
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {},
                content = bottomSheetContent,
                actions = bottomSheetActions
            )
        }
    }
}

@ThemePreviews
@Preview
@Composable
private fun RegularBottomSheetScreenPreview() {
    TudeeTheme {
        var showBottomSheet by remember { mutableStateOf(false) }
        
        TudeeBottomSheetScreen(
            showBottomSheet = showBottomSheet,
            onDismiss = { showBottomSheet = false },
            screenContent = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(TudeeTheme.colors.background)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Regular Bottom Sheet",
                        style = TudeeTheme.typography.headlineLarge,
                        color = TudeeTheme.colors.title
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "This demonstrates a regular bottom sheet that sizes to its content.",
                        style = TudeeTheme.typography.bodyMedium,
                        color = TudeeTheme.colors.body
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    TudeePrimaryButton(
                        text = R.string.show_bottom_sheet,
                        onClick = { showBottomSheet = true },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            bottomSheetContent = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Bottom Sheet Content",
                        style = TudeeTheme.typography.headlineMedium,
                        color = TudeeTheme.colors.title
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "This is a test bottom sheet. Click outside to dismiss it or use the buttons below.",
                        style = TudeeTheme.typography.bodyMedium,
                        color = TudeeTheme.colors.body
                    )
                }
            },
            bottomSheetActions = {
                TudeePrimaryButton(
                    text = R.string.confirm,
                    onClick = { showBottomSheet = false },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                TudeeSecondaryButton(
                    text = "Cancel",
                    onClick = { showBottomSheet = false },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        )
    }
}

@ThemePreviews
@Preview
@Composable
private fun ScrollableBottomSheetScreenPreview() {
    TudeeTheme {
        var showBottomSheet by remember { mutableStateOf(false) }
        
        TudeeBottomSheetScreen(
            showBottomSheet = showBottomSheet,
            onDismiss = { showBottomSheet = false },
            screenContent = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(TudeeTheme.colors.background)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Scrollable Bottom Sheet",
                        style = TudeeTheme.typography.headlineLarge,
                        color = TudeeTheme.colors.title
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "This demonstrates a bottom sheet with scrollable content that exceeds 75% of screen height.",
                        style = TudeeTheme.typography.bodyMedium,
                        color = TudeeTheme.colors.body
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    TudeePrimaryButton(
                        text = R.string.show_bottom_sheet,
                        onClick = { showBottomSheet = true },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            bottomSheetContent = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Scrollable Bottom Sheet",
                        style = TudeeTheme.typography.headlineMedium,
                        color = TudeeTheme.colors.title
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Text(
                        text = "This bottom sheet has lots of content to demonstrate scrolling behavior when content exceeds 75% of screen height.",
                        style = TudeeTheme.typography.bodyMedium,
                        color = TudeeTheme.colors.body
                    )
                    
                    repeat(20) { index ->
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Content Item ${index + 1}: This is a sample content item to fill up the bottom sheet and demonstrate the scrolling functionality. The bottom sheet should become scrollable when this content exceeds 75% of the screen height.",
                            style = TudeeTheme.typography.bodySmall,
                            color = TudeeTheme.colors.body
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    Text(
                        text = "End of scrollable content. You should be able to scroll through all items above.",
                        style = TudeeTheme.typography.bodyMedium,
                        color = TudeeTheme.colors.title
                    )
                }
            },
            bottomSheetActions = {
                TudeePrimaryButton(
                    text = R.string.confirm,
                    onClick = { showBottomSheet = false },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                TudeeSecondaryButton(
                    text = "Cancel",
                    onClick = { showBottomSheet = false },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        )
    }
}