package ua.edu.chnu.kkn.advancedkotlinmultiplatform.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import co.touchlab.kermit.Logger
import org.koin.compose.koinInject

@Composable
fun App() {
    val preferences: UserPreferences = koinInject()
    var showOnboarding by remember { mutableStateOf(!preferences.hasSeenOnboarding) }
    var useDarkTheme by remember { mutableStateOf(preferences.useDarkTheme) }
    var showApiDemo by remember { mutableStateOf(false) }

    DaymarkTheme(useDarkTheme) {
        Surface(
            modifier = androidx.compose.ui.Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            when {
                showOnboarding -> OnboardingScreen(
                    onFinish = {
                        preferences.hasSeenOnboarding = true
                        showOnboarding = false
                        Logger.withTag("Daymark").i { "Onboarding completed" }
                    },
                )
                showApiDemo -> Column {
                    ApiDemoBackButton { showApiDemo = false }
                    PostApiDemo()
                }
                else -> TodoHomeScreen(
                    useDarkTheme = useDarkTheme,
                    onDarkThemeChange = {
                        preferences.useDarkTheme = it
                        useDarkTheme = it
                    },
                    onReplayIntro = { showOnboarding = true },
                    onOpenApiDemo = { showApiDemo = true },
                )
            }
        }
    }
}
