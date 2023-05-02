package com.example.personalcoach.ui.theme

import androidx.compose.ui.graphics.Color

val baseLightPalette = ExtendedJetColors(
    primaryBackground = Color(0xFFFFFFFF),
    primaryText = Color(0xFF252525),
    secondaryBackground = Color(0xFFF0F1F2),
    secondaryText = Color(0x80000000),
    tintColor = Color(0xFF6C63FF),
    clickableText = Color(0xFF6C63FF),
    logoBackground = Color(0xF3000000),
    inputText = Color(0xFF9CA3AF),
    inputBackground = Color(0xFFEEF2F5)
)

val baseDarkPalette = ExtendedJetColors(
    primaryBackground = Color(0xFF232820),
    primaryText = Color(0xFFF2F4F5),
    secondaryBackground = Color(0xFF191E23),
    secondaryText = Color(0xCC7A8A99),
    tintColor = Color(0xFF6C63FF),
    clickableText = Color(0xFF6C63FF),
    logoBackground = Color(0xCCFFFFFF),
    inputText = Color(0xFF9CA3AF),
    inputBackground = Color(0xFFEEF2F5)
)

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

//Figma colors
val PrimaryBlue = Color(0xFF6C63FF)
val Secondary = Color(0xFF9292FD)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)


val purpleLightPalette = baseLightPalette.copy(
    tintColor = Color(0xFFD580FF)
)

val purpleDarkPalette = baseDarkPalette.copy(
    tintColor = Color(0xFFAD57D9)
)

val orangeLightPalette = baseLightPalette.copy(
    tintColor = Color(0xFFFF6619)
)

val orangeDarkPalette = baseDarkPalette.copy(
    tintColor = Color(0xFFFF974D)
)

val blueLightPalette = baseLightPalette.copy(
    tintColor = PrimaryBlue
)

val blueDarkPalette = baseDarkPalette.copy(
    tintColor = Secondary
)

val redLightPalette = baseLightPalette.copy(
    tintColor = Color(0xFFE84479)
)

val redDarkPalette = baseDarkPalette.copy(
    tintColor = Color(0xFFD5204A)
)