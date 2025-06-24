package com.london.tudee.presentation.components.date

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.CardColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

typealias DateBadgeStyle = @Composable () -> DateBadgeStyleValues

data class DateBadgeStyleValues(
    val shape: Shape,
    val colors: CardColors,
    val iconSize: Dp,
    val iconColor: Color,
    val textStyle: TextStyle,
    val contentPadding: PaddingValues = PaddingValues(0.dp),
    val iconTextSpacing: Dp = 2.dp
)

