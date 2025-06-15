package com.london.tudee.presentation.components.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.LottieDynamicProperty
import com.airbnb.lottie.compose.rememberLottieComposition
import com.airbnb.lottie.compose.rememberLottieDynamicProperties
import com.airbnb.lottie.model.KeyPath
import com.london.tudee.R
import com.london.tudee.presentation.design_system.theme.ThemePreviews
import com.london.tudee.presentation.design_system.theme.TudeeTheme

@Composable
fun TudeePrimaryButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    isDisabled: Boolean = false,
    isInError: Boolean = false,
    isLoading: Boolean = false
) {
    if (isDisabled.not()) {
        Row(
            modifier = modifier
                .height(56.dp)
                .clip(TudeeTheme.shapes.circle)
                .background(
                    brush = Brush.verticalGradient(
                        if (isInError.not()) TudeeTheme.colors.primaryGradient
                        else listOf(TudeeTheme.colors.errorVariant, TudeeTheme.colors.errorVariant)
                    )
                )
                .clickable(onClick = onClick)
                .padding(horizontal = 24.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                text = text,
                style = TudeeTheme.typography.labelLarge,
                color = if (isInError.not()) TudeeTheme.colors.onPrimary else TudeeTheme.colors.error,
            )
            if (isLoading) {
                LoadingLottieAnimation(
                    modifier = Modifier
                        .size(24.dp)
                        .padding(start = 8.dp),
                    color = TudeeTheme.colors.onPrimary
                )
            }
        }
    } else {
        Row(
            modifier = modifier
                .height(56.dp)
                .clip(TudeeTheme.shapes.circle)
                .background(
                    TudeeTheme.colors.disabled
                )
                .padding(horizontal = 24.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                text = text,
                style = TudeeTheme.typography.labelLarge,
                color = TudeeTheme.colors.stroke,
            )
        }
    }
}

@Composable
fun LoadingLottieAnimation(modifier: Modifier = Modifier, color: Color) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.lottie_loading))
    val dynamicProperties = rememberLottieDynamicProperties(
        LottieDynamicProperty(
            property = LottieProperty.COLOR, value = color.toArgb(), keyPath = KeyPath("**")
        )
    )
    LottieAnimation(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        modifier = modifier.alpha(0.87f),
        dynamicProperties = dynamicProperties,
        )
}

@Composable
@ThemePreviews
fun PreviewTudeePrimaryButton() {
    TudeeTheme {
        TudeePrimaryButton(
            {}, "Submit", isLoading = true
        )
    }
}