package com.london.tudee.presentation.design_system.color

import androidx.compose.ui.graphics.Color

data class TudeeColors(
    val primary: Color,
    val secondary: Color,
    val primaryVariant: Color,
    val background: Color,
    val surface: Color,
    val onPrimary: Color,
    val title: Color,
    val body: Color,
    val hint: Color,
    val stroke: Color,
    val surfaceLow: Color,
    val surfaceHigh: Color,
    val caption: Color,
    val card: Color,
    val cardStroke: Color,
    val disabled: Color,
    val error: Color,
    val errorVariant: Color,
    val greenAccent: Color,
    val greenVariant: Color,
    val yellowAccent: Color,
    val yellowVariant: Color,
    val purpleAccent: Color,
    val purpleVariant: Color,
    val pinkAccent: Color,
    val overlay: Color,
    val emojiTint: Color,
    val primaryGradient: List<Color>,
    val skyNightBackground:Color = Color(0xff151535),
    val linearMoodColor : List<Color> = listOf(Color(0xffE9F0FF), Color(0xffE0E9FE)),
    val upperSmallCircleMoon: Color = Color(0xffE9EFFF)
    val onPrimaryCard : Color,
    val onPrimaryStroke : Color,
)


