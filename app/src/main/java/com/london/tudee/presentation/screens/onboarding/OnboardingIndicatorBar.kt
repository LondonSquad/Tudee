package com.london.tudee.presentation.screens.onboarding

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.london.tudee.presentation.design_system.theme.TudeeTheme

@Composable
fun OnboardingIndicatorBar(
    activeStep: Int, modifier: Modifier = Modifier, totalSteps: Int = 3, onClick: (Int) -> Unit = {}
) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        repeat(totalSteps) { index ->
            val animatedColor by animateColorAsState(
                targetValue = if (index == activeStep) TudeeTheme.colors.primary
                else TudeeTheme.colors.primaryVariant, label = "stepColorAnimation"
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(5.dp)
                    .clip(TudeeTheme.shapes.circle)
                    .background(animatedColor)
                    .background(animatedColor)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }) {
                        onClick(index)
                    })
            if (index < totalSteps - 1) Spacer(modifier = Modifier.width(10.dp))
        }
    }
}