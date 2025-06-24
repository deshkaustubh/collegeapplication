package com.example.collegeapplication.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext


enum class GenderTheme { GIRL, BOY }

// If you want a custom DarkColorScheme, define it here or import it
val DarkColorScheme = darkColorScheme(
    // Example dark colors, replace with your custom ones
    primary = BluePrimary,
    onPrimary = BlueOnPrimary,
    secondary = BlueSecondary,
    onSecondary = BlueOnSecondary,
    tertiary = BlueTertiary,
    onTertiary = BlueOnTertiary,
    background = BlueBackground,
    onBackground = BlueOnBackground,
    surface = BlueSurface,
    onSurface = BlueOnSurface,
)

fun getColorScheme(gender: GenderTheme): ColorScheme {
    return when (gender) {
        GenderTheme.GIRL -> lightColorScheme(
            primary = PinkPrimary,
            onPrimary = PinkOnPrimary,
            primaryContainer = PinkPrimaryContainer,
            onPrimaryContainer = PinkOnPrimaryContainer,
            secondary = PinkSecondary,
            onSecondary = PinkOnSecondary,
            secondaryContainer = PinkSecondaryContainer,
            onSecondaryContainer = PinkOnSecondaryContainer,
            tertiary = PinkTertiary,
            onTertiary = PinkOnTertiary,
            tertiaryContainer = PinkTertiaryContainer,
            onTertiaryContainer = PinkOnTertiaryContainer,
            background = PinkBackground,
            onBackground = PinkOnBackground,
            surface = PinkSurface,
            onSurface = PinkOnSurface,
        )
        GenderTheme.BOY -> lightColorScheme(
            primary = BluePrimary,
            onPrimary = BlueOnPrimary,
            primaryContainer = BluePrimaryContainer,
            onPrimaryContainer = BlueOnPrimaryContainer,
            secondary = BlueSecondary,
            onSecondary = BlueOnSecondary,
            secondaryContainer = BlueSecondaryContainer,
            onSecondaryContainer = BlueOnSecondaryContainer,
            tertiary = BlueTertiary,
            onTertiary = BlueOnTertiary,
            tertiaryContainer = BlueTertiaryContainer,
            onTertiaryContainer = BlueOnTertiaryContainer,
            background = BlueBackground,
            onBackground = BlueOnBackground,
            surface = BlueSurface,
            onSurface = BlueOnSurface,
        )
    }
}

@Composable
fun CollegeApplicationTheme(
    gender: GenderTheme = GenderTheme.GIRL,
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
        else -> getColorScheme(gender)
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography, // Make sure AppTypography is defined in your theme
        content = content
    )
}