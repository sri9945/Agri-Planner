package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = AgriGreenPrimaryDark,
    onPrimary = AgriGreenOnPrimaryDark,
    primaryContainer = AgriGreenContainerDark,
    onPrimaryContainer = AgriGreenOnContainerDark,
    secondary = AgriAmberSecondaryDark,
    onSecondary = AgriAmberOnSecondaryDark,
    secondaryContainer = AgriAmberContainerDark,
    onSecondaryContainer = AgriAmberOnContainerDark,
    tertiary = AgriClayTertiaryDark,
    onTertiary = AgriClayOnTertiaryDark,
    tertiaryContainer = AgriClayContainerDark,
    onTertiaryContainer = AgriClayOnContainerDark,
    background = AgriBackgroundDark,
    onBackground = AgriOnBackgroundDark,
    surface = AgriSurfaceDark,
    onSurface = AgriOnSurfaceDark,
    surfaceVariant = AgriSurfaceVariantDark,
    onSurfaceVariant = AgriOnSurfaceVariantDark,
    outline = AgriOutlineDark
)

private val LightColorScheme = lightColorScheme(
    primary = AgriGreenPrimary,
    onPrimary = AgriGreenOnPrimary,
    primaryContainer = AgriGreenContainer,
    onPrimaryContainer = AgriGreenOnContainer,
    secondary = AgriAmberSecondary,
    onSecondary = AgriAmberOnSecondary,
    secondaryContainer = AgriAmberContainer,
    onSecondaryContainer = AgriAmberOnContainer,
    tertiary = AgriClayTertiary,
    onTertiary = AgriClayOnTertiary,
    tertiaryContainer = AgriClayContainer,
    onTertiaryContainer = AgriClayOnContainer,
    background = AgriBackgroundLight,
    onBackground = AgriOnBackgroundLight,
    surface = AgriSurfaceLight,
    onSurface = AgriOnSurfaceLight,
    surfaceVariant = AgriSurfaceVariantLight,
    onSurfaceVariant = AgriOnSurfaceVariantLight,
    outline = AgriOutlineLight
)

@Composable
fun AgriPlanTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Keep tailored brand green/amber
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

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
