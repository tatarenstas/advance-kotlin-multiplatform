package ua.edu.chnu.kkn.advancedkotlinmultiplatform.presentation

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.Font
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.resources.Res
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.resources.manrope
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.resources.literata

internal val Ink = Color(0xFF172437)
internal val Cream = Color(0xFFF8F6F0)
internal val Mint = Color(0xFFA7D8C7)
internal val Coral = Color(0xFFF1A58B)

private val LightColors = lightColorScheme(
    primary = Ink,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFE3EEE9),
    onPrimaryContainer = Ink,
    secondary = Color(0xFF57786D),
    onSecondary = Color.White,
    background = Cream,
    onBackground = Ink,
    surface = Color.White,
    onSurface = Ink,
    outline = Color(0xFF8A9690),
)

private val DarkColors = darkColorScheme(
    primary = Mint,
    onPrimary = Ink,
    primaryContainer = Color(0xFF294B46),
    onPrimaryContainer = Color(0xFFE0F4EB),
    secondary = Coral,
    onSecondary = Ink,
    background = Color(0xFF111B27),
    onBackground = Color(0xFFF4F3EB),
    surface = Color(0xFF1B2938),
    onSurface = Color(0xFFF4F3EB),
    outline = Color(0xFF91A6A1),
)

@Composable
internal fun DaymarkTheme(darkTheme: Boolean, content: @Composable () -> Unit) {
    val bodyFont = FontFamily(Font(Res.font.manrope, FontWeight.Normal))
    val displayFont = FontFamily(Font(Res.font.literata, FontWeight.Normal))
    val base = Typography()
    val type = base.copy(
        displayLarge = base.displayLarge.copy(fontFamily = displayFont, fontWeight = FontWeight.SemiBold),
        headlineLarge = base.headlineLarge.copy(fontFamily = displayFont, fontWeight = FontWeight.SemiBold),
        headlineMedium = base.headlineMedium.copy(fontFamily = displayFont, fontWeight = FontWeight.SemiBold),
        titleLarge = base.titleLarge.copy(fontFamily = bodyFont, fontWeight = FontWeight.SemiBold),
        titleMedium = base.titleMedium.copy(fontFamily = bodyFont, fontWeight = FontWeight.SemiBold),
        bodyLarge = base.bodyLarge.copy(fontFamily = bodyFont),
        bodyMedium = base.bodyMedium.copy(fontFamily = bodyFont),
        labelLarge = base.labelLarge.copy(fontFamily = bodyFont, fontWeight = FontWeight.SemiBold),
        labelMedium = base.labelMedium.copy(fontFamily = bodyFont, fontWeight = FontWeight.SemiBold),
    )
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = type,
        content = content,
    )
}
