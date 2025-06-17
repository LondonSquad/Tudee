package com.london.tudee.presentation.design_system.text_style

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.london.tudee.R

val nunitoFont = FontFamily(
    Font(R.font.nunito_regular, FontWeight.Normal),
    Font(R.font.nunito_regular, FontWeight.Medium),
    Font(R.font.nunito_semibold, FontWeight.SemiBold),
    Font(R.font.nunito_bold, FontWeight.Bold),
    Font(R.font.nunito_light, FontWeight.Light),
    Font(R.font.nunito_extralight, FontWeight.ExtraLight),
)
val cherryBombFont = FontFamily(
    Font(R.font.cherry_bomb_regular, FontWeight.Normal),
    Font(R.font.cherry_bomb_regular, FontWeight.Medium),
    Font(R.font.cherry_bomb_regular, FontWeight.SemiBold),
    Font(R.font.cherry_bomb_regular, FontWeight.Bold),
    Font(R.font.cherry_bomb_regular, FontWeight.Light),
    Font(R.font.cherry_bomb_regular, FontWeight.ExtraLight),
)
val ibmPlexSansArabicFont = FontFamily(
    Font(R.font.ibm_plex_sans_arabic_regular, FontWeight.Normal),
    Font(R.font.ibm_plex_sans_arabic_medium, FontWeight.Medium),
    Font(R.font.ibm_plex_sans_arabic_semi_bold, FontWeight.SemiBold),
    Font(R.font.ibm_plex_sans_arabic_bold, FontWeight.Bold),
    Font(R.font.ibm_plex_sans_arabic_light, FontWeight.Light),
    Font(R.font.ibm_plex_sans_arabic_extra_light, FontWeight.ExtraLight),
)

val TudeeTextStyle = TudeeTypography(
    headlineLarge = TextStyle(fontFamily = nunitoFont, fontWeight = FontWeight.SemiBold, fontSize = 28.sp),
    headlineMedium = TextStyle(fontFamily = nunitoFont, fontWeight = FontWeight.SemiBold, fontSize = 24.sp),
    headlineSmall = TextStyle(fontFamily = nunitoFont, fontWeight = FontWeight.SemiBold, fontSize = 20.sp),
    titleLarge = TextStyle(fontFamily = nunitoFont, fontWeight = FontWeight.Medium, fontSize = 20.sp),
    titleMedium = TextStyle(fontFamily = nunitoFont, fontWeight = FontWeight(500), fontSize = 18.sp , lineHeight = 22.sp),
    titleSmall = TextStyle(fontFamily = nunitoFont, fontWeight = FontWeight.Medium, fontSize = 16.sp),
    bodyLarge = TextStyle(fontFamily = nunitoFont, fontWeight = FontWeight.Normal, fontSize = 18.sp),
    bodyMedium = TextStyle(fontFamily = nunitoFont, fontWeight = FontWeight.Normal, fontSize = 16.sp),
    bodySmall = TextStyle(fontFamily = nunitoFont, fontWeight = FontWeight.Normal, fontSize = 14.sp),
    labelLarge = TextStyle(fontFamily = nunitoFont, fontWeight = FontWeight.Medium, fontSize = 16.sp),
    labelMedium = TextStyle(fontFamily = nunitoFont, fontWeight = FontWeight.Medium, fontSize = 14.sp),
    labelSmall = TextStyle(fontFamily = nunitoFont, fontWeight = FontWeight.Medium, fontSize = 12.sp),
    cherryBomb = TextStyle(fontFamily = cherryBombFont, fontWeight = FontWeight.Normal, fontSize = 18.sp),
)