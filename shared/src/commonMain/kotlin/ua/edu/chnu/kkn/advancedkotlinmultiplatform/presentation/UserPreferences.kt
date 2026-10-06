package ua.edu.chnu.kkn.advancedkotlinmultiplatform.presentation

import com.russhwolf.settings.Settings

internal class UserPreferences(private val settings: Settings) {
    var hasSeenOnboarding: Boolean
        get() = settings.getBoolean(ONBOARDING_KEY, false)
        set(value) = settings.putBoolean(ONBOARDING_KEY, value)

    var useDarkTheme: Boolean
        get() = settings.getBoolean(DARK_THEME_KEY, false)
        set(value) = settings.putBoolean(DARK_THEME_KEY, value)

    private companion object {
        const val ONBOARDING_KEY = "has_seen_onboarding"
        const val DARK_THEME_KEY = "use_dark_theme"
    }
}
