package com.example.personalcoach.ui.theme


import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp

data class ExtendedJetColors(
    val primaryText: Color,
    val primaryBackground: Color,
    val secondaryText: Color,
    val secondaryBackground: Color,
    val tintColor: Color,
    val clickableText: Color,
    val logoBackground: Color,
    val inputText: Color,
    val inputBackground: Color
)

data class ExtendedJetTypography(
    val heading: TextStyle,
    val body: TextStyle,
    val toolbar: TextStyle
)

data class ExtendedJetShape(
    val padding: Dp,
    val cornersStyle: RoundedCornerShape
)

object ExtendedJetTheme{
    internal val colors: ExtendedJetColors
    @Composable
    internal get() = LocalJetColors.current

    internal val typography: ExtendedJetTypography
    @Composable
    internal get() = LocalJetTypography.current

    internal val shapes: ExtendedJetShape
    @Composable
    internal get() = LocalJetShape.current
}

enum class ExtendedJetStyle{
    Purple, Blue, Orange, Red
}

enum class ExtendedJetSize{
    Small, Medium, Big
}

enum class ExtendedJetCorners{
    Float, Rounded
}


internal val LocalJetTypography = staticCompositionLocalOf<ExtendedJetTypography> {
    error("No font provided")
}

internal val LocalJetColors = staticCompositionLocalOf<ExtendedJetColors> {
    error("No colors provided")
}

internal val LocalJetShape = staticCompositionLocalOf<ExtendedJetShape> {
    error("No shapes provided")
}