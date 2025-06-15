package com.london.tudee.presentation.design_system.shape

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp

val TudeeThemeShape = TudeeShapes(
    extraSmall = TudeeShapeDefaults.ExtraSmall,
    small = TudeeShapeDefaults.Small,
    medium = TudeeShapeDefaults.Medium,
    large = TudeeShapeDefaults.Large,
    extraLarge = TudeeShapeDefaults.ExtraLarge,
    circle = TudeeShapeDefaults.Circle,
)

object TudeeShapeDefaults {
    val ExtraSmall = RoundedCornerShape(12.dp)

    val Small = RoundedCornerShape(16.dp)

    val Medium = RoundedCornerShape(20.dp)

    val Large = RoundedCornerShape(24.dp)

    val ExtraLarge = RoundedCornerShape(28.dp)

    val Circle = RoundedCornerShape(100.dp)
}