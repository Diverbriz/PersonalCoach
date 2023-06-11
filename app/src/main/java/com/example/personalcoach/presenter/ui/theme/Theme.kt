package com.example.personalcoach.presenter.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.ViewCompat


private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryBlue,
    secondary = Secondary,
    tertiary = Pink40

//    backgroundC = Color(0xFFFFFBFE),
//    surface = Color(0xFFFFFBFE),
//    onPrimary = Color.White,
//    onSecondary = Color.White,
//    onTertiary = Color.White,
//    onBackground = Color(0xFF1C1B1F),
//    onSurface = Color(0xFF1C1B1F),

)

@Composable
fun MainTheme(
    style: ExtendedJetStyle = ExtendedJetStyle.Purple,
    textSize: ExtendedJetSize = ExtendedJetSize.Medium,
    paddingSize: ExtendedJetSize = ExtendedJetSize.Medium,
    corners: ExtendedJetCorners = ExtendedJetCorners.Rounded,
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
){
    val colors = when(!darkTheme){
        true -> {
            when(style){
                ExtendedJetStyle.Purple -> purpleLightPalette
                ExtendedJetStyle.Blue -> blueLightPalette
                ExtendedJetStyle.Orange -> orangeLightPalette
                ExtendedJetStyle.Red -> redLightPalette
            }
        }
        false -> {
            when(style){
                ExtendedJetStyle.Purple -> purpleDarkPalette
                ExtendedJetStyle.Blue -> blueDarkPalette
                ExtendedJetStyle.Orange -> orangeDarkPalette
                ExtendedJetStyle.Red -> redDarkPalette
            }
        }
    }
    val typography = ExtendedJetTypography(
        heading = TextStyle(
            fontSize = when(textSize){
                ExtendedJetSize.Small -> 24.sp
                ExtendedJetSize.Medium -> 28.sp
                ExtendedJetSize.Big -> 36.sp
            },
            fontWeight = FontWeight.Bold,
            fontFamily = montserattBold
        ),
        body = TextStyle(
            fontSize = when(textSize){
                ExtendedJetSize.Small -> 14.sp
                ExtendedJetSize.Medium -> 16.sp
                ExtendedJetSize.Big -> 18.sp
            },
            fontWeight = FontWeight.Normal,
            fontFamily = montserattLight
        ),
        toolbar = TextStyle(
            fontSize = when(textSize){
                ExtendedJetSize.Small -> 14.sp
                ExtendedJetSize.Medium -> 16.sp
                ExtendedJetSize.Big -> 18.sp
            },
            fontWeight = FontWeight.Medium,
            fontFamily = montserattBlack
        )
    )

    val shapes = ExtendedJetShape(
        padding = when(paddingSize){
            ExtendedJetSize.Small -> 12.dp
            ExtendedJetSize.Medium -> 16.dp
            ExtendedJetSize.Big -> 20.dp
        },
        cornersStyle = when(corners){
            ExtendedJetCorners.Rounded -> RoundedCornerShape(8.dp)
            ExtendedJetCorners.Float -> RoundedCornerShape(0.dp)
        }
    )

    CompositionLocalProvider(
        LocalJetColors provides colors,
        LocalJetTypography provides typography,
        LocalJetShape provides shapes,
        content = content
    )
}

@Composable
fun PreviewTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),

    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            (view.context as Activity).window.statusBarColor = colorScheme.primary.toArgb()
            ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}