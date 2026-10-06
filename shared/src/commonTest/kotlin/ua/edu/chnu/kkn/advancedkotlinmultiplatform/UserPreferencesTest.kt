package ua.edu.chnu.kkn.advancedkotlinmultiplatform

import com.russhwolf.settings.MapSettings
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import ua.edu.chnu.kkn.advancedkotlinmultiplatform.presentation.UserPreferences

class UserPreferencesTest {
    @Test
    fun onboardingAndAppearanceSurviveRecreatingPreferences() {
        val settings = MapSettings()
        val first = UserPreferences(settings)
        assertFalse(first.hasSeenOnboarding)
        assertFalse(first.useDarkTheme)

        first.hasSeenOnboarding = true
        first.useDarkTheme = true

        val second = UserPreferences(settings)
        assertTrue(second.hasSeenOnboarding)
        assertTrue(second.useDarkTheme)
    }
}
