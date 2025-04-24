package com.jesushz.mastermeme.core.presentation.designsystem.theme

import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val MasterMemeColorScheme = darkColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,
    background = Background,
    onBackground = OnBackground,
    surface = Surface,
    onSurface = OnSurface
)

@Composable
fun MasterMemeTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = MasterMemeColorScheme,
        typography = Typography,
        content = content
    )
}
