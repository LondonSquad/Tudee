package com.london.tudee.presentation.screens.splash

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.res.ResourcesCompat
import com.london.tudee.R
import com.london.tudee.presentation.design_system.theme.TudeeTheme

@Composable
fun TudeeLogoText(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val text = "Tudee"

    val fillColor = TudeeTheme.colors.onPrimary.toArgb()
    val strokeColor = TudeeTheme.colors.primary.toArgb()

    val typeface = remember {
        ResourcesCompat.getFont(context, R.font.cherry_bomb_regular)
    }

    val paint = remember {
        android.graphics.Paint().apply {
            isAntiAlias = true
            textSize = 200f
            this.typeface = typeface
        }
    }

    Canvas(
        modifier = modifier
            .fillMaxWidth()
    ) {
        val x = (size.width - paint.measureText(text)) / 2
        val y = size.height / 2 + paint.textSize / 3

        paint.style = android.graphics.Paint.Style.STROKE
        paint.strokeWidth = 18f
        paint.color = strokeColor
        drawContext.canvas.nativeCanvas.drawText(text, x, y, paint)

        paint.style = android.graphics.Paint.Style.FILL
        paint.color = fillColor
        drawContext.canvas.nativeCanvas.drawText(text, x, y, paint)
    }
}